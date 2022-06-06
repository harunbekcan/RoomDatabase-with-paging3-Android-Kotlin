package com.harunbekcan.roomdatabasewithpaging3project.data.api.response

import com.google.gson.annotations.SerializedName

data class PopularTvResponse(
    @SerializedName("total_pages") val total_pages: Int = 0,
    val page: Int = 0,
    val results: List<PopularTvResponseItem>?
) {
    data class PopularTvResponseItem(
        val id: Int?,
        val name: String?,
        val original_language: String?,
        val poster_path: String?
    )
}