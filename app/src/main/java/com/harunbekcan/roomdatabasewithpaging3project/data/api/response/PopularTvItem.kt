package com.harunbekcan.roomdatabasewithpaging3project.data.api.response

import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel

data class PopularTvItem(
    val total_pages: Int = 0,
    val page: Int = 0,
    val popularTvList: List<PopularTvDatabaseModel>
) {
    val isEndOfListReached = total_pages == page
}