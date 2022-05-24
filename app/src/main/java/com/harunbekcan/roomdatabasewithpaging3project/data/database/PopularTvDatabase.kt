package com.harunbekcan.roomdatabasewithpaging3project.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harunbekcan.roomdatabasewithpaging3project.data.dao.PopularTvDao
import com.harunbekcan.roomdatabasewithpaging3project.data.response.PopularTvItem

@Database(entities = [PopularTvItem::class], version = 1, exportSchema = false)
abstract class PopularTvDatabase : RoomDatabase() {

    abstract fun getPopularTvDao(): PopularTvDao
}