package com.harunbekcan.roomdatabasewithpaging3project.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.harunbekcan.roomdatabasewithpaging3project.data.api.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.response.PopularTvItem
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant.API_KEY
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PopularTvRemoteMediator constructor(
    private val serviceInterface: ServiceInterface,
    private val popularTvDatabase: PopularTvDatabase
) : RemoteMediator<Int, PopularTvItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularTvItem>,
    ): MediatorResult {

        STARTING_PAGE_INDEX = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                lastItem
                STARTING_PAGE_INDEX + 1
            }
        }

        try {
            val response = serviceInterface.getPopularTv(apikey = API_KEY, page = STARTING_PAGE_INDEX)
            val allPopularTv = response.results


            var endOfPaginationReached = false

            if (allPopularTv.size < STARTING_PAGE_INDEX) {
                endOfPaginationReached = true
            }


            popularTvDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    popularTvDatabase.getPopularTvDao().clearAll()

                }
                popularTvDatabase.getPopularTvDao().insertAll(response.results)
            }



            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)

        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }


    }

    companion object {
        private var STARTING_PAGE_INDEX = 1
    }
}