package com.harunbekcan.roomdatabasewithpaging3project.data.api.response

data class PopularTvItem(
    val total_pages: Int = 0,
    val page: Int = 0,
    val popularTvList: List<PopularTvType>
) {

    val isEndOfListReached = total_pages == page
}