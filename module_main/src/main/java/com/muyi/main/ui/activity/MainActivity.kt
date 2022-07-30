package com.muyi.main.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.ActivityMainBinding
import com.muyi.main.viewmodel.MainViewModel

/**
 * Created by hq on 2022/7/28.
 **/
@Route(path = AppConstants.Router.Main.A_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.tvTitle.set("Login")
        viewModel.toolbarRightText.set("right")
    }


}