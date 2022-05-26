package com.harunbekcan.roomdatabasewithpaging3project.utils

import com.harunbekcan.roomdatabasewithpaging3project.data.api.response.PopularTvItem
import com.harunbekcan.roomdatabasewithpaging3project.data.api.response.PopularTvResponse
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel

fun PopularTvResponse.mapDataToPopularTvItem(): PopularTvItem =
    with(this) {
        PopularTvItem(
            total_pages = total_pages,
            page = page,
            popularTvList = results?.map {
                PopularTvDatabaseModel(
                    it.id ?: -1,
                    it.name ?: "",
                    it.original_language?: "",
                    it.poster_path?: ""
                )
            } ?: arrayListOf()
        )
    }