package com.muyi.main.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.czl.lib_base.util.DialogHelper
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentLoginBinding
import com.muyi.main.viewmodel.LoginViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Login.F_LOGIN)
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override fun initContentView(): Int {
        return R.layout.fragment_login
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initViewObservable() {
        viewModel.uc.successLiveEvent.observe(this) {
            when (it.code) {
                200 -> {
                    it.data?.let { userBean ->
                        viewModel.model.saveUserData(userBean)
                        RouteCenter.navigate(AppConstants.Router.Main.A_MAIN)
                        AppManager.instance.finishAllActivity()
                    }
                }
                400103 -> {
                    DialogHelper.showNoCancelDialog(
                        requireContext(),
                        "⚠️ 注意",
                        it.msg ?: "您的位置与注册地址偏差较大，请在指定位置登录！"
                    ) {

                    }
                }
                400104 -> {
                    DialogHelper.showNoCancelDialog(
                        requireContext(),
                        "⚠️ 注意",
                        it.msg ?: "当前处于禁止登录时间，将退出应用!"
                    ) {
                        AppManager.instance.appExit()
                    }
                }
            }
        }
    }
}
