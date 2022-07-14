package com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val popularTvDatabase: PopularTvDatabase) : ViewModel() {

    fun getFavorites(){

    }

    fun removePopularTvList(id: Int) {
        viewModelScope.launch {
            popularTvDatabase.getPopularTvDao().removePopularTvList(id)
        }
    }
}