package com.harunbekcan.roomdatabasewithpaging3project.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.api.response.RemoteKeys
import com.harunbekcan.roomdatabasewithpaging3project.data.local.dao.PopularTvDao
import com.harunbekcan.roomdatabasewithpaging3project.data.local.dao.RemoteKeysDao
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel

@Database(
    entities = [PopularTvDatabaseModel::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class PopularTvDatabase : RoomDatabase() {

    abstract fun getPopularTvDao(): PopularTvDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}