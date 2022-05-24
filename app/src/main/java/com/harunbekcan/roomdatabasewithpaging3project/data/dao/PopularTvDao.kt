package com.harunbekcan.roomdatabasewithpaging3project.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harunbekcan.roomdatabasewithpaging3project.data.response.PopularTvItem

@Dao
interface PopularTvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(popularTvList: List<PopularTvItem>)

    @Query("SELECT * FROM popularTv")
    fun getPopularTvAll(): PagingSource<Int, PopularTvItem>

    @Query("DELETE FROM popularTv")
    fun clearAll()
}