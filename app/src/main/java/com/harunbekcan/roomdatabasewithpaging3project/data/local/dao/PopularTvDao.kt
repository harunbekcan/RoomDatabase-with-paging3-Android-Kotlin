package com.harunbekcan.roomdatabasewithpaging3project.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel

@Dao
interface PopularTvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(popularTvList: List<PopularTvDatabaseModel>)

    @Query("select * from popularTv")
    fun getPopularTvAll(): PagingSource<Int, PopularTvDatabaseModel>

    @Query("select * from popularTv where isFavorite = 1")
    fun getFavorites(): PagingSource<Int, PopularTvDatabaseModel>

    @Query("DELETE FROM popularTv")
    fun clearAll()

    @Query("UPDATE popularTv SET isFavorite = :status WHERE popularTvId=:id")
    suspend fun changeFavoriteStatus(id: Int, status: Int)

}