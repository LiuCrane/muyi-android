package com.muyi.main.progress.ui

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.czl.lib_base.util.DialogHelper
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentAddVisionBinding
import com.muyi.main.progress.viewmodel.AddVisionViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Progress.F_ADD_VISION)
class AddVisionFragment : BaseFragment<FragmentAddVisionBinding, AddVisionViewModel>() {
    @JvmField
    @Autowired
    var studentId: String? = null

    override fun initContentView(): Int {
        return R.layout.fragment_add_vision
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        viewModel.tvTitle.set("添加视力")
        viewModel.studentId = studentId
    }
}
