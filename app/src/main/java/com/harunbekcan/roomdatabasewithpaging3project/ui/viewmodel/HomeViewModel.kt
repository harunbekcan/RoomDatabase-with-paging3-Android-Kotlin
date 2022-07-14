package com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.harunbekcan.roomdatabasewithpaging3project.data.api.service.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.data.remote.PopularTvRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serviceInterface: ServiceInterface,
    private val popularTvDatabase: PopularTvDatabase
) : ViewModel() {
    @ExperimentalPagingApi
    fun getAllPopularTv(): Flow<PagingData<PopularTvDatabaseModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            prefetchDistance = 5,
            initialLoadSize = 40
        ),
        pagingSourceFactory = { popularTvDatabase.getPopularTvDao().getPopularTvAll() },
        remoteMediator = PopularTvRemoteMediator(
            serviceInterface,
            popularTvDatabase,
            20
        )
    ).flow.cachedIn(viewModelScope).flowOn(Dispatchers.IO)

    @OptIn(DelicateCoroutinesApi::class)
    fun changeStatus(id: Int, status: Int) {
        GlobalScope.launch {
            popularTvDatabase.getPopularTvDao().changeFavoriteStatus(id, status)
        }
    }
}