package com.harunbekcan.roomdatabasewithpaging3project.data.api.response

data class PopularTvResponse(
    val page: Int,
    val results: List<PopularTvResponseItem>?
)

data class PopularTvResponseItem(
    val id: Int?,
    val name: String?,
    val original_language: String?,
    val poster_path: String?
)
