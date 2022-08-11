package com.muyi.main.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
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
}
