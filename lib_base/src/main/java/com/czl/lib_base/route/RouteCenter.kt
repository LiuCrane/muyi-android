package com.czl.lib_base.route

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.czl.lib_base.config.AppConstants

/**
 * @author Alwyn
 * @Date 2020/10/23
 * @Description
 */
object RouteCenter {

    fun navigateWithKey(path: String, key: String? = null): Any? {
        val build = ARouter.getInstance().build(path)
        return if (key == null) build.navigation() else build.withString(
            AppConstants.IntentKey.KEY_STRING,
            key
        ).navigation()
    }

    fun navigate(path: String, bundle: Bundle? = null): Any? {
        val build = ARouter.getInstance().build(path)
        return if (bundle == null) build.navigation() else build.with(bundle).navigation()
    }
}