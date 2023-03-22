package com.harunbekcan.roomdatabasewithpaging3project.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.harunbekcan.roomdatabasewithpaging3project.data.api.response.RemoteKeys
import com.harunbekcan.roomdatabasewithpaging3project.data.api.service.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant
import com.harunbekcan.roomdatabasewithpaging3project.utils.mapDataToPopularTvItem

@OptIn(ExperimentalPagingApi::class)
class PopularTvRemoteMediator(
    private val serviceInterface: ServiceInterface,
    private val db: PopularTvDatabase
) : RemoteMediator<Int, PopularTvDatabaseModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularTvDatabaseModel>
    ): MediatorResult {

        return try {
            val key = when (loadType) {
                LoadType.REFRESH -> {
                    if (db.getPopularTvDao().getCount() > 0) return MediatorResult.Success(false)
                    null
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    getKeyFromDb()
                }
            }

            if (key != null) if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)

            val page: Int = key?.nextKey ?: 1
            val response = serviceInterface.getPopularTv(Constant.API_KEY, page)
            val data = response.mapDataToPopularTvItem()
            val endOfPaginationReached = response.page == response.totalPages

            data.popularTvList.let {
                db.withTransaction {
                    db.remoteKeysDao().insertKeys(
                        RemoteKeys(
                            0,
                            nextKey = page.plus(1),
                            isEndReached = endOfPaginationReached
                        )
                    )
                    db.getPopularTvDao().insertAll(data.popularTvList)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyFromDb(): RemoteKeys? {
        return db.remoteKeysDao().getKeys().firstOrNull()
    }
}