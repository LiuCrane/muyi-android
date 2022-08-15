package com.czl.lib_base.data.source

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.*
import io.reactivex.Observable

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
        offset: Int,
        limit: Int,
        type: String,
        course_id: Int,
        public: Boolean
    ): Observable<BaseBean<List<MediaBean>>>

    fun getClassList(
        offset: Int,
        limit: Int
    ): Observable<BaseBean<List<ClassesBean>>>

    fun createClass(
        name: String,
    ): Observable<BaseBean<String>>

    fun getStudentList(
        offset: Int,
        limit: Int,
        rehab: String?
    ): Observable<BaseBean<List<StudentBean>>>

    fun getStoreInfo(): Observable<BaseBean<StoreBean>>

}