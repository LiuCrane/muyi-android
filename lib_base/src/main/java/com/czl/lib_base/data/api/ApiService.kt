package com.czl.lib_base.data.api

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {


    @POST("app/auth/login")
    fun login(@Body body: Any): Observable<BaseBean<UserBean>>

    @POST("/app/user/register")
    fun register(@Body body: Any): Observable<BaseBean<UserBean>>

    @GET("/app/media")
    fun appMedia(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("type") type: String,
        @Query("course_id") course_id: Int,
        @Query("public") public: Boolean
    ): Observable<BaseBean<List<MediaBean>>>
}