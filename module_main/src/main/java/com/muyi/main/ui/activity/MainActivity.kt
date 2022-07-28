package com.muyi.main.ui.activity

import com.czl.lib_base.base.BaseActivity
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.MainActivityMainBinding
import com.muyi.main.viewmodel.MainViewModel


class MainActivity : BaseActivity<MainActivityMainBinding, MainViewModel>(){
    override fun initContentView(): Int {
        return R.layout.main_activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}