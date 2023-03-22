package com.harunbekcan.roomdatabasewithpaging3project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harunbekcan.roomdatabasewithpaging3project.data.api.response.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(remoteKey: RemoteKeys)

    @Query("SELECT * FROM remoteKeys")
    suspend fun getKeys(): List<RemoteKeys>
}