package com.czl.lib_base.data

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseModel
import com.czl.lib_base.data.bean.ClassesBean
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.data.bean.StudentBean
import com.czl.lib_base.data.bean.UserBean
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

    override fun getUserToken(): String? {
        return mLocalDataSource.getUserToken()
    }

    override fun userLogin(username: String, password: String): Observable<BaseBean<UserBean>> {
        return mHttpDataSource.userLogin(username, password)
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
        return mHttpDataSource.userRegister(
            name,
            password,
            phone,
            id_card_num,
            store_name,
            store_address,
            store_lat,
            store_lng
        )
    }

    override fun getMediaList(
        offset: Int,
        limit: Int,
        type: String,
        course_id: Int,
        public: Boolean
    ): Observable<BaseBean<List<MediaBean>>> {
        return mHttpDataSource.getMediaList(offset, limit, type, course_id, public)
    }

    override fun getClassList(offset: Int, limit: Int): Observable<BaseBean<List<ClassesBean>>> {
        return mHttpDataSource.getClassList(offset, limit)
    }

    override fun getStudentList(
        offset: Int,
        limit: Int,
        rehab: Boolean
    ): Observable<BaseBean<List<StudentBean>>> {
        return mHttpDataSource.getStudentList(offset, limit, rehab)
    }

}