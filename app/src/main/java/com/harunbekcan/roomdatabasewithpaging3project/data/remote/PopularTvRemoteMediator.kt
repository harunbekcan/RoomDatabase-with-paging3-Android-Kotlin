package com.harunbekcan.roomdatabasewithpaging3project.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.harunbekcan.roomdatabasewithpaging3project.data.api.service.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant.PAGE_NUMBER
import com.harunbekcan.roomdatabasewithpaging3project.utils.mapDataToPopularTvItem

@OptIn(ExperimentalPagingApi::class)
class PopularTvRemoteMediator(
    private val serviceInterface: ServiceInterface,
    private val popularTvDatabase: PopularTvDatabase,
    private val pageSize: Int
) : RemoteMediator<Int, PopularTvDatabaseModel>() {

    private var page = PAGE_NUMBER
    private var endOfPaginationReached = false

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularTvDatabaseModel>,
    ): MediatorResult {

        page = when (loadType) {
            LoadType.REFRESH -> {
                PAGE_NUMBER
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                state.lastItemOrNull() ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                page + 1
            }
        }

        try {
            val response = serviceInterface.getPopularTv(Constant.API_KEY, page)
            val data = response.mapDataToPopularTvItem()

            if (data.popularTvList.size < pageSize) {
                endOfPaginationReached = true
            }

            popularTvDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    popularTvDatabase.getPopularTvDao().clearAll()
                }
                popularTvDatabase.getPopularTvDao().insertAll(data.popularTvList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}