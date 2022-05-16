package com.harunbekcan.roomdatabasewithpaging3project.base

data class BasePagingResponse<T>(
    val results: List<T> = arrayListOf()
)