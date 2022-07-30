package com.muyi.main.ui.activity

import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.ActivitySplashBinding
import com.muyi.main.viewmodel.SplashViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by hq on 2022/7/30.
 **/
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun initContentView(): Int {
        return R.layout.activity_splash
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }
    override fun initData() {
        goToMain()
    }

    private fun goToMain(){
        viewModel.addSubscribe(
            Flowable.timer(200L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (viewModel.model.getUserToken().isNullOrBlank()) {
                        startContainerActivity(AppConstants.Router.Login.F_LOGIN)
                        overridePendingTransition(me.yokeyword.fragmentation.R.anim.h_fragment_enter, 0)
                    } else {
                        RouteCenter.navigate(AppConstants.Router.Main.A_MAIN)
                    }
                    finish()
                })

    }


}