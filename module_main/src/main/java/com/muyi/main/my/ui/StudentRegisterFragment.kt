package com.muyi.main.my.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentStudentRegisterBinding
import com.muyi.main.my.viewmodel.StudentRegisterViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.My.F_REGISTRATION)
class StudentRegisterFragment :
    BaseFragment<FragmentStudentRegisterBinding, StudentRegisterViewModel>() {

    override fun initContentView(): Int {
        return R.layout.fragment_student_register
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }


    override fun initData() {

    }
}
