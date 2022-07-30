package com.czl.lib_base.data

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseModel
import com.czl.lib_base.data.bean.BrowseRecordsBean
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
        return mHttpDataSource.userLogin(username,password)
    }

    override fun getBrowseRecords(limit: Int): Observable<BaseBean<BrowseRecordsBean>> {
        return mHttpDataSource.getBrowseRecords(limit)
    }


}