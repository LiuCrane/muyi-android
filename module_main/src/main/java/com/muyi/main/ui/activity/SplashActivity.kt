package com.muyi.main.ui.activity

import android.Manifest
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.ActivitySplashBinding
import com.muyi.main.viewmodel.SplashViewModel
import com.permissionx.guolindev.PermissionX
import es.dmoral.toasty.Toasty
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
//        goToMain()
        askPermission()
    }

    private fun askPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }.onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
            }
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Toasty.success(this, "All permissions are granted!", Toast.LENGTH_LONG, true)
                        .show()
                    goToMain()
                } else {
                    Toasty.error(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG,
                        true
                    ).show()


                }
            }
    }

    private fun goToMain() {
        viewModel.addSubscribe(
            Flowable.timer(200L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    LogUtils.e("userToken=" + viewModel.model.getUserToken())
                    if (viewModel.model.getUserToken().isNullOrEmpty()) {
                        startContainerActivity(AppConstants.Router.Login.F_LOGIN)
                        overridePendingTransition(
                            me.yokeyword.fragmentation.R.anim.h_fragment_enter,
                            0
                        )
                    } else {
                        RouteCenter.navigate(AppConstants.Router.Main.A_MAIN)
                    }
                    finish()
                })

    }


}