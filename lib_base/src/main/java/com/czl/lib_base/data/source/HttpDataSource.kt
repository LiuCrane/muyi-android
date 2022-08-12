package com.czl.lib_base.data.source

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.data.bean.UserBean
import io.reactivex.Observable

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
interface HttpDataSource {
    fun userLogin(username: String, password: String): Observable<BaseBean<UserBean>>
    fun userRegister(
        name: String,
        password: String,
        phone: String,
        id_card_num: String,
        store_name: String,
        store_address: String,
        store_lat: String,
        store_lng: String
    ): Observable<BaseBean<UserBean>>

    fun getMediaList(
        offset: Int,
        limit: Int,
        type: String,
        course_id: Int,
        public: Boolean
    ): Observable<BaseBean<List<MediaBean>>>
}