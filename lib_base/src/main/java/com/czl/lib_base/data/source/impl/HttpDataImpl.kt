package com.czl.lib_base.data.source.impl

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.api.ApiService
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.data.source.HttpDataSource
import com.google.gson.JsonObject
import io.reactivex.Observable

class HttpDataImpl(private val apiService: ApiService) : HttpDataSource {

    override fun userLogin(username: String, password: String): Observable<BaseBean<UserBean>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", username)
        jsonObject.addProperty("password", password)
        return apiService.login(jsonObject)
    }

    override fun userRegister(
        name: String,
        password: String,
        phone: String,
        id_card_num: String,
        store_name: String,
        store_address: String,
        store_lat: String,
        store_lng: String
    ): Observable<BaseBean<UserBean>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("phone", phone)
        jsonObject.addProperty("id_card_num", id_card_num)
        jsonObject.addProperty("store_name", store_name)
        jsonObject.addProperty("store_address", store_address)
        jsonObject.addProperty("store_lat", store_lat)
        jsonObject.addProperty("store_lng", store_lng)
        return apiService.register(jsonObject)
    }

    override fun getMediaList(
        offset: Int,
        limit: Int,
        type: String,
        course_id: Int,
        public: Boolean
    ): Observable<BaseBean<List<MediaBean>>> {
        return apiService.appMedia(offset, limit, type, course_id, public)
    }

}