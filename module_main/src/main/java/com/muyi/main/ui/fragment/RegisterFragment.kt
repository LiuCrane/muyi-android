package com.muyi.main.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.DialogHelper
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentLoginBinding
import com.muyi.main.viewmodel.RegisterViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Login.F_REGISTER)
class RegisterFragment : BaseFragment<FragmentLoginBinding, RegisterViewModel>() {
    override fun initContentView(): Int {
        return R.layout.fragment_register
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initViewObservable() {
        viewModel.uc.successLiveEvent.observe(this) {
            DialogHelper.showNoCancelDialog(requireContext(), "注册成功", "您的注册信息正在审核中，请稍后在登录页登录。") {
                this@RegisterFragment.back()
            }

        }

    }

    override fun initData() {

    }
}
