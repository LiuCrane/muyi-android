package com.czl.lib_base.data

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseModel
import com.czl.lib_base.data.bean.*
import com.czl.lib_base.data.source.HttpDataSource
import com.czl.lib_base.data.source.LocalDataSource
import io.reactivex.Observable

/**
 * @author Alwyn
 * @Date 2020/7/20
 * @Description 数据中心（本地+在线） 外部通过Koin依赖注入调用
 * 对于缓存或者在线数据的增删查改统一通过该数据仓库调用
 */
class DataRepository constructor(
    private val mLocalDataSource: LocalDataSource,
    private val mHttpDataSource: HttpDataSource
) : BaseModel(), LocalDataSource, HttpDataSource {

    override fun saveUserData(userBean: UserBean) {
        mLocalDataSource.saveUserData(userBean)
    }

    override fun deleteUserData() {
        mLocalDataSource.deleteUserData()
    }

    override fun getUserToken(): String? {
        return mLocalDataSource.getUserToken()
    }

    override fun userLogin(
        username: String, password: String, lat: String, lng: String
    ): Observable<BaseBean<UserBean>> {
        return mHttpDataSource.userLogin(username, password, lat, lng)
    }

    override fun userRegister(
        name: String,
        password: String,
        phone: String,
        id_card_num: String,
        store_name: String,
        store_area_id: String,
        store_address_detail: String,
        store_lat: String,
        store_lng: String
    ): Observable<BaseBean<String>> {
        return mHttpDataSource.userRegister(
            name,
            password,
            phone,
            id_card_num,
            store_name,
            store_area_id,
            store_address_detail,
            store_lat,
            store_lng
        )
    }

    override fun studentRegister(
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
    ): Observable<BaseBean<String>> {
        return mHttpDataSource.studentRegister(
            name,
            age,
            gender,
            parent_name,
            parent_phone,
            left_vision,
            right_vision,
            binocular_vision,
            class_id,
            area_id,
            address_detail
        )
    }

    override fun getMediaList(
        page_num: Int,
        page_size: Int,
        type: String,
    ): Observable<BaseBean<ListDataBean<MediaBean>>> {
        return mHttpDataSource.getMediaList(page_num, page_size, type)
    }

    override fun getClassList(
        page_num: Int,
        page_size: Int
    ): Observable<BaseBean<ListDataBean<ClassesBean>>> {
        return mHttpDataSource.getClassList(page_num, page_size)
    }

    override fun createClass(name: String, teacher: String): Observable<BaseBean<String>> {
        return mHttpDataSource.createClass(name, teacher)
    }

    override fun getStudentList(
        page_num: Int,
        page_size: Int,
        rehab: String?
    ): Observable<BaseBean<ListDataBean<StudentBean>>> {
        return mHttpDataSource.getStudentList(page_num, page_size, rehab)
    }

    override fun getStudentDetail(student_id: String): Observable<BaseBean<StudentBean>> {
        return mHttpDataSource.getStudentDetail(student_id)
    }

    override fun getStoreInfo(): Observable<BaseBean<StoreBean>> {
        return mHttpDataSource.getStoreInfo()
    }

    override fun getClassStudents(class_id: String): Observable<BaseBean<ListDataBean<StudentBean>>> {
        return mHttpDataSource.getClassStudents(class_id)
    }

    override fun getClassDetail(class_id: String): Observable<BaseBean<ClassesBean>> {
        return mHttpDataSource.getClassDetail(class_id)
    }

    override fun getClassCourses(class_id: String): Observable<BaseBean<ListDataBean<CourseBean>>> {
        return mHttpDataSource.getClassCourses(class_id)
    }

    override fun applyCourse(class_id: String, course_id: String): Observable<BaseBean<String>> {
        return mHttpDataSource.applyCourse(class_id, course_id)
    }

    override fun getCourseMediaList(
        class_id: String,
        course_id: String
    ): Observable<BaseBean<ListDataBean<MediaBean>>> {
        return mHttpDataSource.getCourseMediaList(class_id, course_id)
    }

    override fun mediaPlay(
        id: String,
        class_id: String?,
        course_id: String?,
        event: String
    ): Observable<BaseBean<String>> {
        return mHttpDataSource.mediaPlay(id, class_id, course_id, event)
    }

    override fun updateVision(
        id: String,
        binocular_vision: String,
        left_vision: String,
        right_vision: String,
        course_id: Int
    ): Observable<BaseBean<String>> {
        return mHttpDataSource.updateVision(
            id,
            binocular_vision,
            left_vision,
            right_vision,
            course_id
        )
    }

    override fun getAllAddress(): Observable<BaseBean<ListDataBean<ProvinceBean>>> {
        return mHttpDataSource.getAllAddress()
    }

    override fun getStudentCourse(id: String): Observable<BaseBean<ListDataBean<CourseBean>>> {
        return mHttpDataSource.getStudentCourse(id)
    }

    override fun getCourseStatus(
        class_id: String,
        course_id: String,
    ): Observable<BaseBean<StatusBean>> {
        return mHttpDataSource.getCourseStatus(class_id, course_id)
    }

}