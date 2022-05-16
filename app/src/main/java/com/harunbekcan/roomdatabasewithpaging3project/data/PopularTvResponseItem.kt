package com.harunbekcan.roomdatabasewithpaging3project.data

data class PopularTvResponseItem(
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: ArrayList<Int>?,
    val id: Int,
    val name: String,
    val origin_country: ArrayList<String>,
    val original_language: String,
    val original_name: String?,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int,
)