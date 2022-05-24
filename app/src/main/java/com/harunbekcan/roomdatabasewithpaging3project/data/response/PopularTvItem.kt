package com.harunbekcan.roomdatabasewithpaging3project.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popularTv")
data class PopularTvItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val original_language: String?,
    val poster_path: String?,
)