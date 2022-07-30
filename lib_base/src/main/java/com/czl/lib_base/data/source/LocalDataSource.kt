package com.czl.lib_base.data.source

import com.czl.lib_base.data.bean.UserBean

/**
 * @author Alwyn
 * @Date 2020/7/20
 * @Description
 */
interface LocalDataSource {
    fun saveUserData(userBean: UserBean)
    fun getUserToken(): String?

}