package com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.harunbekcan.roomdatabasewithpaging3project.data.api.service.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.data.remote.PopularTvRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serviceInterface: ServiceInterface,
    private val popularTvDatabase: PopularTvDatabase
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllPopularTv(): Flow<PagingData<PopularTvDatabaseModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = PopularTvRemoteMediator(serviceInterface, popularTvDatabase),
            pagingSourceFactory = { popularTvDatabase.getPopularTvDao().getPopularTvAll() }
        ).flow
    }


    fun changeStatus(id: Int, status: Int) {
        viewModelScope.launch {
            popularTvDatabase.getPopularTvDao().changeFavoriteStatus(id, status)
        }
    }
}