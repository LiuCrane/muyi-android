package com.czl.lib_base.data.bean

import com.google.gson.annotations.SerializedName

data class UserBean(
    @SerializedName("token")
    val token: String,
)

data class ListDataBean<T>(
    @SerializedName("list")
    val list: List<T>?,
)

data class MediaBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("img")
    val img: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("duration")
    val duration: String?
)

data class ClassesBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("teacher")
    val teacher: String?,
    @SerializedName("current_course")
    val current_course: String?,
    @SerializedName("store_name")
    val store_name: String?,
    @SerializedName("store_number")
    val store_number: String?,
    @SerializedName("study_progress")
    val study_progress: String?,//学习进度(NOT_STARTED:未开始, IN_PROGRESS:进行中, REHAB_TRAINING:复训中, ENDED:已结束)
    @SerializedName("student_num")
    val student_num: Int?
)

data class StudentBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("avatar_url")
    val avatar_url: String?,
    @SerializedName("class_name")
    val class_name: String?,
    @SerializedName("current_course")
    val current_course: String?,
    @SerializedName("parent_name")
    val parent_name: String?,
    @SerializedName("parent_phone")
    val parent_phone: String?,
    @SerializedName("improved")
    val improved: Boolean?,
    @SerializedName("left_vision")
    val left_vision: String?,
    @SerializedName("right_vision")
    val right_vision: String?,
    @SerializedName("eyesightList")
    val eyesightList: List<EyesightBean>?
)

data class EyesightBean(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("improved")
    val improved: Boolean?,
    @SerializedName("left_vision")
    val left_vision: String?,
    @SerializedName("right_vision")
    val right_vision: String?
)

data class StoreBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("status")
    val status: Boolean
    //APPROVED, REJECTED, SUBMITTED
)