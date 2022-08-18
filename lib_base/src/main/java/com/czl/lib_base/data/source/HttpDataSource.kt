package com.czl.lib_base.data.source

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.*
import io.reactivex.Observable
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
        store_address: String,
        store_lat: String,
        store_lng: String
    ): Observable<BaseBean<String>>

    fun studentRegister(
        name: String,
        parent_name: String,
        parent_phone: String,
        left_diopter: String,
        right_diopter: String,
        left_vision: String,
        right_vision: String,
        class_id: String
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
    ): Observable<BaseBean<String>>

    fun getStudentList(
        page_num: Int,
        page_size: Int,
        rehab: String?
    ): Observable<BaseBean<ListDataBean<StudentBean>>>

    fun getStoreInfo(): Observable<BaseBean<StoreBean>>

    fun getClassStudents(
        class_id: String
    ): Observable<BaseBean<List<StudentBean>>>

    fun getClassDetail(
        class_id: String
    ): Observable<BaseBean<ClassesBean>>

    fun getClassCourses(
        class_id: String
    ): Observable<BaseBean<List<CourseBean>>>
}