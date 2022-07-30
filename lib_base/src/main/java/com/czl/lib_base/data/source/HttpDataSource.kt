package com.czl.lib_base.data.source

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.BrowseRecordsBean
import com.czl.lib_base.data.bean.UserBean
import io.reactivex.Observable

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
interface HttpDataSource {
    fun userLogin(username: String, password: String): Observable<BaseBean<UserBean>>
    fun getBrowseRecords(limit: Int): Observable<BaseBean<BrowseRecordsBean>>
}