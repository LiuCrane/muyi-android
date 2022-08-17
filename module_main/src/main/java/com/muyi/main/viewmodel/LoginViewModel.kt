package com.muyi.main.viewmodel

import android.location.Location
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.route.RouteCenter
import com.czl.lib_base.util.GPSUtils
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/30.
 **/
class LoginViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var userName = ObservableField("")
    var pwd = ObservableField("")
    var hasGetLocation = false

    val onUserNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userName.set(it)
    })

    val onPwdChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        pwd.set(it)
    })

    var btnLoginClick: BindingCommand<Any> = BindingCommand(BindingAction {
        getLocation()
    })

    val registerClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.Login.F_REGISTER)
    })

    private fun getLocation() {
        GPSUtils.getInstance(Utils.getApp())
            ?.getLngAndLat(object : GPSUtils.OnLocationResultListener {
                override fun onLocationResult(location: Location?) {
                    LogUtils.e("onLocationChange location latitude=" + location?.latitude + " longitude=" + location?.longitude)
                    loginByPwd(location?.latitude, location?.longitude)
                }

                override fun onLocationChange(location: Location?) {
                    LogUtils.e("onLocationChange location latitude=" + location?.latitude + " longitude=" + location?.longitude)
                    loginByPwd(location?.latitude, location?.longitude)
                }
            })
    }

    private fun loginByPwd(latitude: Double?, longitude: Double?) {
        if (hasGetLocation)
            return

        if (latitude == null || longitude == null) {
            showNormalToast("位置信息未获取，请打开手机定位服务重新登录")
            return
        }

        hasGetLocation = true
        GPSUtils.getInstance(Utils.getApp())?.removeListener()

        model.apply {
            if (userName.get().isNullOrBlank() || pwd.get().isNullOrBlank()) {
                showNormalToast("账号或密码不能为空")
                return
            }
            userLogin(
                userName.get()!!,
                pwd.get()!!,
                latitude.toString(),
                longitude.toString()
            ).compose(RxThreadHelper.rxSchedulerHelper(this@LoginViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<UserBean>>() {
                    override fun onResult(result: BaseBean<UserBean>) {
                        dismissLoading()
                        if (result.code == 200) {
                            result.data?.let {
                                saveUserData(it)
                                RouteCenter.navigate(AppConstants.Router.Main.A_MAIN)
                                AppManager.instance.finishAllActivity()
                            }
                        }
                        hasGetLocation = false
                    }

                    override fun onFailed(msg: String?) {
                        dismissLoading()
                        showErrorToast(msg)
                        hasGetLocation = false
                    }
                })
        }
    }

}