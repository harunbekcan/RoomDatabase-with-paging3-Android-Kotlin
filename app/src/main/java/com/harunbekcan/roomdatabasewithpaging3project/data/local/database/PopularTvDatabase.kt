package com.harunbekcan.roomdatabasewithpaging3project.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.dao.PopularTvDao
import com.harunbekcan.roomdatabasewithpaging3project.data.local.dao.PopularTvRemoteDao
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvRemoteKeys

@Database(entities = [PopularTvDatabaseModel::class,PopularTvRemoteKeys::class], version = 1, exportSchema = false)
abstract class PopularTvDatabase : RoomDatabase() {

    abstract fun getPopularTvDao(): PopularTvDao
    abstract fun getPopularTvRemoteDao(): PopularTvRemoteDao
}