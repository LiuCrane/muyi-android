package com.czl.lib_base.data.source.impl

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.api.ApiService
import com.czl.lib_base.data.bean.*
import com.czl.lib_base.data.source.HttpDataSource
import com.google.gson.JsonObject
import io.reactivex.Observable

class HttpDataImpl(private val apiService: ApiService) : HttpDataSource {

    override fun userLogin(
        username: String,
        password: String,
        lat: String,
        lng: String
    ): Observable<BaseBean<UserBean>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", username)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("lat", lat)
        jsonObject.addProperty("lng", lng)
        return apiService.login(jsonObject)
    }

    override fun userRegister(
        name: String,
        password: String,
        phone: String,
        id_card_num: String,
        store_name: String,
        store_area_id: String,
        store_address_detail: String,
        store_lat: String,
        store_lng: String
    ): Observable<BaseBean<String>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("phone", phone)
        jsonObject.addProperty("id_card_num", id_card_num)
        jsonObject.addProperty("store_name", store_name)
        jsonObject.addProperty("store_area_id", store_area_id)
        jsonObject.addProperty("store_address_detail", store_address_detail)
        jsonObject.addProperty("store_lat", store_lat)
        jsonObject.addProperty("store_lng", store_lng)
        return apiService.register(jsonObject)
    }

    override fun studentRegister(
        name: String,
        age: String,
        gender: String,
        parent_name: String,
        parent_phone: String,
        left_vision: String,
        right_vision: String,
        binocular_vision: String,
        class_id: String,
        area_id: String,
        address_detail: String
    ): Observable<BaseBean<String>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("age", age)
        jsonObject.addProperty("gender", gender)
        jsonObject.addProperty("parent_name", parent_name)
        jsonObject.addProperty("parent_phone", parent_phone)
        jsonObject.addProperty("left_vision", left_vision)
        jsonObject.addProperty("right_vision", right_vision)
        jsonObject.addProperty("binocular_vision", binocular_vision)
        jsonObject.addProperty("class_id", class_id)
        jsonObject.addProperty("area_id", area_id)
        jsonObject.addProperty("address_detail", address_detail)
        return apiService.studentRegister(jsonObject)
    }


    override fun getMediaList(
        page_num: Int,
        page_size: Int,
        type: String,
    ): Observable<BaseBean<ListDataBean<MediaBean>>> {
        return apiService.appMedia(page_num, page_size, type)
    }

    override fun getClassList(
        page_num: Int,
        page_size: Int
    ): Observable<BaseBean<ListDataBean<ClassesBean>>> {
        return apiService.appClasses(page_num, page_size)
    }

    override fun createClass(name: String, teacher: String): Observable<BaseBean<String>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("teacher", teacher)
        return apiService.createAppClass(jsonObject)
    }

    override fun getStudentList(
        page_num: Int,
        page_size: Int,
        rehab: String?
    ): Observable<BaseBean<ListDataBean<StudentBean>>> {
        return apiService.appStudents(page_num, page_size, rehab)
    }

    override fun getStudentDetail(student_id: String): Observable<BaseBean<StudentBean>> {
        return apiService.getStudentDetail(student_id)
    }

    override fun getStoreInfo(): Observable<BaseBean<StoreBean>> {
        return apiService.appStore()
    }

    override fun getClassStudents(class_id: String): Observable<BaseBean<ListDataBean<StudentBean>>> {
        return apiService.getClassStudents(class_id)

    }

    override fun getClassDetail(class_id: String): Observable<BaseBean<ClassesBean>> {
        return apiService.getClassDetail(class_id)
    }

    override fun getClassCourses(class_id: String): Observable<BaseBean<ListDataBean<CourseBean>>> {
        return apiService.getClassCourses(class_id)
    }

    override fun applyCourse(class_id: String, course_id: String): Observable<BaseBean<String>> {
        return apiService.applyCourse(class_id, course_id)
    }

    override fun getCourseMediaList(
        class_id: String,
        course_id: String
    ): Observable<BaseBean<ListDataBean<MediaBean>>> {
        return apiService.getCourseMediaList(class_id, course_id)
    }

    override fun mediaPlay(
        id: String,
        class_id: String?,
        course_id: String?,
        event: String
    ): Observable<BaseBean<String>> {
        val jsonObject = JsonObject()
        if (class_id?.isNotEmpty() == true)
            jsonObject.addProperty("class_id", class_id)
        if (course_id?.isNotEmpty() == true)
            jsonObject.addProperty("course_id", course_id)
        jsonObject.addProperty("event", event)
        return apiService.mediaPlay(id, jsonObject)
    }

    override fun updateVision(
        id: String,
        binocular_vision: String,
        course_id: Int
    ): Observable<BaseBean<String>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("binocular_vision", binocular_vision)
        jsonObject.addProperty("course_id", course_id)
        return apiService.updateVision(id, jsonObject)
    }

    override fun getAllAddress(): Observable<BaseBean<ListDataBean<ProvinceBean>>> {
        return apiService.getAllAddress()
    }

    override fun getStudentCourse(id: String): Observable<BaseBean<ListDataBean<CourseBean>>> {
        return apiService.getStudentCourse(id)
    }

}