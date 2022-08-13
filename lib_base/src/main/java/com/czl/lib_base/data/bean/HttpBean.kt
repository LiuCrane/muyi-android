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

data class ClassesBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("current_course")
    val current_course: String,
    @SerializedName("store_name")
    val store_name: String,
    @SerializedName("store_number")
    val store_number: String,
    @SerializedName("study_progress")
    val study_progress: String,
)

data class StudentBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("class_name")
    val class_name: String,
    @SerializedName("currentCourse")
    val currentCourse: String,
    @SerializedName("parent_name")
    val parent_name: String,
    @SerializedName("parent_phone")
    val parent_phone: String,
    @SerializedName("stageStatus")
    val stageStatus: String,
    @SerializedName("improved")
    val improved: Boolean,
    @SerializedName("diopters")
    val diopters: List<String>
)