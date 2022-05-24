package com.harunbekcan.roomdatabasewithpaging3project.data.api

import com.harunbekcan.roomdatabasewithpaging3project.base.BasePagingResponse
import com.harunbekcan.roomdatabasewithpaging3project.data.response.PopularTvItem
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceInterface {

    //SAMPLE_API_REQUEST = https://api.themoviedb.org/3/tv/popular?api_key=f83e3ab8ce2ddc41894a72e45dfe6a2e

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") apikey: String = API_KEY,
        @Query("page") page: Int
    ): BasePagingResponse<PopularTvItem>
}