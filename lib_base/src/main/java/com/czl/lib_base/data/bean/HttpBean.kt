package com.czl.lib_base.data.bean

import com.google.gson.annotations.SerializedName

data class UserBean(
    @SerializedName("token")
    val token: String,
)

data class MediaBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("duration")
    val duration: String
)
