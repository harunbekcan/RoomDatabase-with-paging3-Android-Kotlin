package com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.harunbekcan.roomdatabasewithpaging3project.data.api.service.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.data.remote.PopularTvRemoteMediator
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serviceInterface: ServiceInterface,
    private val popularTvDatabase: PopularTvDatabase
) : ViewModel() {
    @ExperimentalPagingApi
    fun getAllPopularTv(): Flow<PagingData<PopularTvDatabaseModel>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = true),
        pagingSourceFactory = { popularTvDatabase.getPopularTvDao().getPopularTvAll() },
        remoteMediator = PopularTvRemoteMediator(serviceInterface, popularTvDatabase, Constant.API_KEY)
    ).flow.cachedIn(viewModelScope)
}