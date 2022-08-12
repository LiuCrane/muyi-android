package com.muyi.main.ui.fragment.learn

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentClassBinding
import com.muyi.main.viewmodel.ClassViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Learn.F_CLASS)
class ClassFragment : BaseFragment<FragmentClassBinding, ClassViewModel>() {
    override fun initContentView(): Int {
        return R.layout.fragment_class
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
    override fun useBaseLayout(): Boolean {
        return false
    }
}