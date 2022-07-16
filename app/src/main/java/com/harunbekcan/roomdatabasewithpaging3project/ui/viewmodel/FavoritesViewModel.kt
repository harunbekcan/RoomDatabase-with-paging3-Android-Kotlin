package com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harunbekcan.roomdatabasewithpaging3project.data.local.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val popularTvDatabase: PopularTvDatabase) : ViewModel() {

    val adapterList = arrayListOf<PopularTvDatabaseModel>()
    var favoritesObserve: LiveData<List<PopularTvDatabaseModel>> = MutableLiveData()

    init {
        getFavorites()
    }

    private fun getFavorites(){
        favoritesObserve = popularTvDatabase.getPopularTvDao().getFavorites()
    }

    fun removePopularTvList(id: Int,status: Int) {
        viewModelScope.launch {
            popularTvDatabase.getPopularTvDao().changeFavoriteStatus(id,status)
        }
    }
}