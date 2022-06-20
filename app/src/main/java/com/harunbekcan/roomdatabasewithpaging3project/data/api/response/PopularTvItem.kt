package com.harunbekcan.roomdatabasewithpaging3project.data.api.response

import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel

data class PopularTvItem(
    val page: Int,
    val popularTvList: List<PopularTvDatabaseModel>
)