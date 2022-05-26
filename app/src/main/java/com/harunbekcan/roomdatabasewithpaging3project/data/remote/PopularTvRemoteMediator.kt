package com.harunbekcan.roomdatabasewithpaging3project.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.harunbekcan.roomdatabasewithpaging3project.data.api.service.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvRemoteKeys
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant
import com.harunbekcan.roomdatabasewithpaging3project.utils.mapDataToPopularTvItem
import java.io.InvalidObjectException

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalPagingApi::class)
class PopularTvRemoteMediator constructor(
    private val serviceInterface: ServiceInterface,
    private val popularTvDatabase: PopularTvDatabase,
    private val apiKey:String
) : RemoteMediator<Int, PopularTvDatabaseModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularTvDatabaseModel>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                prevKey
            }
        }
        try {
            val response = serviceInterface.getPopularTv(apiKey, page)
            val data = response.mapDataToPopularTvItem()

            popularTvDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    popularTvDatabase.getPopularTvRemoteDao().clearRemoteKeys()
                    popularTvDatabase.getPopularTvDao().clearAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (data.isEndOfListReached) null else page + 1
                val keys = data.popularTvList.map {
                    it as PopularTvDatabaseModel
                    PopularTvRemoteKeys(
                        id = it.popularTvId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                popularTvDatabase.getPopularTvRemoteDao().insertAll(keys)
                popularTvDatabase.getPopularTvDao().insertAll(data.popularTvList as List<PopularTvDatabaseModel>)
            }
            return MediatorResult.Success(endOfPaginationReached = data.isEndOfListReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PopularTvDatabaseModel>): PopularTvRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            popularTvDatabase.getPopularTvRemoteDao().remoteKeysByPopularTvId(repo.popularTvId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PopularTvDatabaseModel>): PopularTvRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { popularTv ->
            popularTvDatabase.getPopularTvRemoteDao().remoteKeysByPopularTvId(popularTv.popularTvId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PopularTvDatabaseModel>): PopularTvRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.popularTvId?.let { id ->
                popularTvDatabase.getPopularTvRemoteDao().remoteKeysByPopularTvId(id)
            }
        }
    }
}