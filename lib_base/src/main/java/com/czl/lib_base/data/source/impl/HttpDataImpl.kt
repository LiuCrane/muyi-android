package com.czl.lib_base.data.source.impl

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.api.ApiService
import com.czl.lib_base.data.bean.BrowseRecordsBean
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.data.source.HttpDataSource
import com.google.gson.JsonObject
import io.reactivex.Observable

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
class HttpDataImpl(private val apiService: ApiService) : HttpDataSource {

    override fun userLogin(username: String, password: String): Observable<BaseBean<UserBean>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", username)
        jsonObject.addProperty("password", password)
        return apiService.pwdLogin(jsonObject)
    }

    override fun getBrowseRecords(limit: Int): Observable<BaseBean<BrowseRecordsBean>>{
        return apiService.browseRecords(limit)
    }

}