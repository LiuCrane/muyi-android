package com.czl.lib_base.data.bean

import com.google.gson.annotations.SerializedName
import com.lljjcoder.bean.CustomCityData

data class UserBean(
    @SerializedName("token")
    val token: String?,
)

data class ListDataBean<T>(
    @SerializedName("list")
    val list: MutableList<T>?,
)

data class MediaBean(
    @SerializedName("id")
    val id: String?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,//(AUDIO:音频, VIDEO:视频)
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
    val id: String?,
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
    val student_num: String?
)

data class CourseBean(
    @SerializedName("id")
    val id: String?,
    @SerializedName("cover_url")
    val cover_url: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("media_num")
    val media_num: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("status")
    var status: String?
    //状态(APPLICABLE:可申请, UN_APPLICABLE:不可申请, UNDER_APPLICATION:申请中, ACCESSIBLE:可进入课程, COMPLETED:已完成)
)

data class StudentBean(
    @SerializedName("id")
    val id: String?,
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
    val improved: String?,
    @SerializedName("first_left_vision")
    val first_left_vision: String?,
    @SerializedName("first_right_vision")
    val first_right_vision: String?,
    @SerializedName("left_vision")
    val left_vision: String?,
    @SerializedName("right_vision")
    val right_vision: String?,
    @SerializedName("first_binocular_vision")
    val first_binocular_vision: String?,
    @SerializedName("binocular_vision")
    val binocular_vision: String?,
    @SerializedName("eyesight_list")
    val eyesight_list: MutableList<EyesightBean>?,

    )

data class EyesightBean(
    @SerializedName("created_at")
    val created_at: Long?,
    @SerializedName("updated_at")
    val updated_at: Long?,
    @SerializedName("improved")
    val improved: String?,
    @SerializedName("binocular_vision")
    val binocular_vision: String?,
    @SerializedName("left_vision")
    val left_vision: String?,
    @SerializedName("right_vision")
    val right_vision: String?,
    @SerializedName("title")
    val title: String?,
)

data class StoreBean(
    @SerializedName("id")
    val id: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("lat")
    val lat: String?,
    @SerializedName("lng")
    val lng: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("number")
    val number: String?,
    @SerializedName("status")
    val status: String?
    //APPROVED, REJECTED, SUBMITTED
)

data class ProvinceBean(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("children")
    val children: MutableList<CityBean>?
)

data class CityBean(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("children")
    val children: MutableList<CountyBean>?
)

data class CountyBean(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("children")
    val children: MutableList<CountyBean>?
)

data class StatusBean(
    @SerializedName("status")
    val status: String?,
)
