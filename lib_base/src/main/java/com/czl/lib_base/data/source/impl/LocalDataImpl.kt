package com.czl.lib_base.data.source.impl

import com.blankj.utilcode.util.GsonUtils
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.data.source.LocalDataSource
import com.czl.lib_base.util.SpHelper
import com.google.gson.reflect.TypeToken


class LocalDataImpl : LocalDataSource {

    override fun saveUserData(userBean: UserBean) {
        SpHelper.encode(AppConstants.SpKey.USER_TOKEN, userBean.token)
        SpHelper.encode(
            AppConstants.SpKey.USER_JSON_DATA,
            GsonUtils.toJson(userBean, object : TypeToken<UserBean>() {}.type)
        )
    }

    override fun getUserToken(): String {
        return SpHelper.decodeString(AppConstants.SpKey.USER_TOKEN)
    }

}