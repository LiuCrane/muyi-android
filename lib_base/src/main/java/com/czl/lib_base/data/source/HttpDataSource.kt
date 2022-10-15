package com.czl.lib_base.data.source

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Path

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
interface HttpDataSource {
    fun userLogin(
        username: String, password: String, lat: String, lng: String
    ): Observable<BaseBean<UserBean>>

    fun userRegister(
        name: String,
        password: String,
        phone: String,
        id_card_num: String,
        store_name: String,
        store_area_id: String,
        store_address_detail: String,
        store_lat: String,
        store_lng: String
    ): Observable<BaseBean<String>>

    fun studentRegister(
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
    ): Observable<BaseBean<String>>

    fun getMediaList(
        page_num: Int,
        page_size: Int,
        type: String,
    ): Observable<BaseBean<ListDataBean<MediaBean>>>

    fun getClassList(
        page_num: Int,
        page_size: Int,
    ): Observable<BaseBean<ListDataBean<ClassesBean>>>

    fun createClass(
        name: String,
        teacher: String
    ): Observable<BaseBean<String>>

    fun getStudentList(
        page_num: Int,
        page_size: Int,
        rehab: String?
    ): Observable<BaseBean<ListDataBean<StudentBean>>>

    fun getStudentDetail(
        student_id: String
    ): Observable<BaseBean<StudentBean>>

    fun getStoreInfo(): Observable<BaseBean<StoreBean>>

    fun getClassStudents(
        class_id: String
    ): Observable<BaseBean<ListDataBean<StudentBean>>>

    fun getClassDetail(
        class_id: String
    ): Observable<BaseBean<ClassesBean>>

    fun getClassCourses(
        class_id: String
    ): Observable<BaseBean<ListDataBean<CourseBean>>>

    fun applyCourse(
        class_id: String,
        course_id: String
    ): Observable<BaseBean<String>>

    fun getCourseMediaList(
        class_id: String,
        course_id: String
    ): Observable<BaseBean<ListDataBean<MediaBean>>>

    fun mediaPlay(
        id: String,
        class_id: String?,
        course_id: String?,
        event: String
    ): Observable<BaseBean<String>>

    fun updateVision(
        id: String,
        binocular_vision: String,
        left_vision: String,
        right_vision: String,
        course_id: Int
    ): Observable<BaseBean<String>>

    fun getAllAddress(): Observable<BaseBean<ListDataBean<ProvinceBean>>>

    fun getStudentCourse(
        id: String
    ): Observable<BaseBean<ListDataBean<CourseBean>>>

    fun getCourseStatus(
        class_id: String,
        course_id: String
    ): Observable<BaseBean<StatusBean>>
}