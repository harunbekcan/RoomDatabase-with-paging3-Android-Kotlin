package com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.harunbekcan.roomdatabasewithpaging3project.data.api.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.repository.PopularTvRemoteMediator
import com.harunbekcan.roomdatabasewithpaging3project.data.response.PopularTvItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serviceInterface: ServiceInterface,
    private val popularTvDatabase: PopularTvDatabase
) : ViewModel() {
    @ExperimentalPagingApi
    fun getAllPopularTv(): Flow<PagingData<PopularTvItem>> = Pager(
        config = PagingConfig(20, enablePlaceholders = false),
        pagingSourceFactory = { popularTvDatabase.getPopularTvDao().getPopularTvAll() },
        remoteMediator = PopularTvRemoteMediator(serviceInterface, popularTvDatabase)
    ).flow.cachedIn(viewModelScope)
}