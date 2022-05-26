package com.harunbekcan.roomdatabasewithpaging3project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvRemoteKeys

@Dao
interface PopularTvRemoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PopularTvRemoteKeys>)

    @Query("SELECT * FROM popular_tv_remote_keys WHERE id = :popularTvId")
    suspend fun remoteKeysByPopularTvId(popularTvId: Long): PopularTvRemoteKeys?

    @Query("DELETE FROM popular_tv_remote_keys")
    suspend fun clearRemoteKeys()
}