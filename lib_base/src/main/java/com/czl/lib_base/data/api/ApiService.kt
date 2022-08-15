package com.czl.lib_base.data.api

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @POST("app/auth/login")
    fun login(@Body body: Any): Observable<BaseBean<UserBean>>

    @POST("/app/user/register")
    fun register(@Body body: Any): Observable<BaseBean<String>>

    @POST("/app/students")
    fun studentRegister(@Body body: Any): Observable<BaseBean<String>>

    @GET("/app/media")
    fun appMedia(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = AppConstants.Common.PAGE_SIZE,
        @Query("type") type: String,
        @Query("course_id") course_id: Int,
        @Query("public") public: Boolean
    ): Observable<BaseBean<List<MediaBean>>>

    @GET("/app/classes")
    fun appClasses(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = AppConstants.Common.PAGE_SIZE,
    ): Observable<BaseBean<List<ClassesBean>>>

    @POST("/app/classes")
    fun createAppClass(
        @Body body: Any
    ): Observable<BaseBean<String>>


    @GET("/app/students")
    fun appStudents(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = AppConstants.Common.PAGE_SIZE,
        @Query("rehab") rehab: String?,
    ): Observable<BaseBean<List<StudentBean>>>

    @GET("/app/store")
    fun appStore(): Observable<BaseBean<StoreBean>>


}