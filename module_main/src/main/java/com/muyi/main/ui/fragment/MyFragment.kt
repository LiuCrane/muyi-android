package com.muyi.main.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.StoreBean
import com.czl.lib_base.data.bean.StudentBean
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentLoginBinding
import com.muyi.main.databinding.FragmentMyBinding
import com.muyi.main.databinding.FragmentProgressBinding
import com.muyi.main.viewmodel.MyViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Main.F_MY)
class MyFragment : BaseFragment<FragmentMyBinding, MyViewModel>() {

    override fun initContentView(): Int {
        return R.layout.fragment_my
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
        viewModel.getStoreInfo()
        binding.smartCommon.setEnableLoadMore(false)

    }

    override fun initViewObservable() {
        viewModel.uc.getStoreInfoCompleteEvent.observe(this){
            binding.smartCommon.finishRefresh(500)

            binding.data=it
        }
    }
}