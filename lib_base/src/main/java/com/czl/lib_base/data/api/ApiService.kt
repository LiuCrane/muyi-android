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
        @Query("page_num") page_num: Int? = 0,
        @Query("page_size") page_size: Int? = AppConstants.Common.PAGE_SIZE,
        @Query("type") type: String,
    ): Observable<BaseBean<ListDataBean<MediaBean>>>

    @GET("/app/classes")
    fun appClasses(
        @Query("page_num") page_num: Int? = 0,
        @Query("page_size") page_size: Int? = AppConstants.Common.PAGE_SIZE,
    ): Observable<BaseBean<ListDataBean<ClassesBean>>>

    @POST("/app/classes")
    fun createAppClass(
        @Body body: Any
    ): Observable<BaseBean<String>>


    @GET("/app/students")
    fun appStudents(
        @Query("page_num") page_num: Int? = 0,
        @Query("page_size") page_size: Int? = AppConstants.Common.PAGE_SIZE,
        @Query("rehab") rehab: String?,
    ): Observable<BaseBean<ListDataBean<StudentBean>>>


    @GET("/app/students/{id}")
    fun getStudentDetail(
        @Path("id") student_id: String
    ): Observable<BaseBean<StudentBean>>

    @GET("/app/store")
    fun appStore(): Observable<BaseBean<StoreBean>>


    @GET("/app/classes/{class_id}/students")
    fun getClassStudents(
        @Path("class_id") class_id: String
    ): Observable<BaseBean<ListDataBean<StudentBean>>>

    @GET("/app/classes/{id}")
    fun getClassDetail(
        @Path("id") class_id: String
    ): Observable<BaseBean<ClassesBean>>


    @GET("/app/classes/{class_id}/courses")
    fun getClassCourses(
        @Path("class_id") class_id: String
    ): Observable<BaseBean<ListDataBean<CourseBean>>>

    @POST("/app/classes/{class_id}/courses/{id}/apply")
    fun applyCourse(
        @Path("class_id") class_id: String,
        @Path("id") course_id: String
    ): Observable<BaseBean<String>>


}