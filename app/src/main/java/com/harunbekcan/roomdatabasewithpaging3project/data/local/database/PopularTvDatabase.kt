package com.harunbekcan.roomdatabasewithpaging3project.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.local.dao.PopularTvDao
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel

@Database(entities = [PopularTvDatabaseModel::class], version = 1, exportSchema = false)
abstract class PopularTvDatabase : RoomDatabase() {

    abstract fun getPopularTvDao(): PopularTvDao
}