package com.harunbekcan.roomdatabasewithpaging3project.data.api.service

import com.harunbekcan.roomdatabasewithpaging3project.data.api.response.PopularTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceInterface {

    //SAMPLE_API_REQUEST = https://api.themoviedb.org/3/tv/popular?api_key=f83e3ab8ce2ddc41894a72e45dfe6a2e

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): PopularTvResponse
}