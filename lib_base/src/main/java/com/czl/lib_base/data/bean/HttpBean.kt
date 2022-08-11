package com.czl.lib_base.data.bean

import com.google.gson.annotations.SerializedName

/**
 * @author Alwyn
 * @Date 2020/10/15
 * @Description
 */
data class UserBean(
    @SerializedName("token")
    val token: String,
)

data class BrowseRecordsBean(
    @SerializedName("token")
    val token: String,
)
