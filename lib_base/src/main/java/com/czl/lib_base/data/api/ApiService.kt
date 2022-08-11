package com.czl.lib_base.data.api

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.*
import com.google.gson.Gson
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
interface ApiService {


    @POST("app/auth/login")
    fun pwdLogin(@Body body: Any): Observable<BaseBean<UserBean>>

    @GET("/app/media/browse_records")
    fun browseRecords(@Query("limit") limit: Int): Observable<BaseBean<BrowseRecordsBean>>
}