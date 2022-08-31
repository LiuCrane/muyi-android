package com.muyi.main.viewmodel

import android.location.Location
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.route.RouteCenter
import com.czl.lib_base.util.DialogHelper
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

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val successLiveEvent: SingleLiveEvent<BaseBean<UserBean>> = SingleLiveEvent()
    }


    val onUserNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userName.set(it)
    })

    val onPwdChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        pwd.set(it)
    })

    var btnLoginClick: BindingCommand<Any> = BindingCommand(BindingAction {
        hasGetLocation = false
        getLocation()
    })

    val registerClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.Login.F_REGISTER)
    })

    private fun getLocation() {
        showLoading()
        GPSUtils.getInstance(Utils.getApp())
            ?.getLngAndLat(object : GPSUtils.OnLocationResultListener {
                override fun onLocationResult(location: Location?) {
                    LogUtils.e("onLocationChange location latitude=" + location?.latitude + " longitude=" + location?.longitude)
//                    loginByPwd(location?.latitude, location?.longitude)
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

        hasGetLocation = true
        GPSUtils.getInstance(Utils.getApp())?.removeListener()

        if (latitude == null || longitude == null) {
            showNormalToast("位置信息未获取，请打开手机定位服务重新登录")
            dismissLoading()
            return
        }

        model.apply {
            if (userName.get().isNullOrBlank() || pwd.get().isNullOrBlank()) {
                showNormalToast("账号或密码不能为空")
                dismissLoading()
                return
            }
            userLogin(
                userName.get()!!,
                pwd.get()!!,
                latitude.toString(),
                longitude.toString()
            ).compose(RxThreadHelper.rxSchedulerHelper(this@LoginViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<UserBean>>() {
                    override fun onResult(result: BaseBean<UserBean>) {
                        dismissLoading()
                        uc.successLiveEvent.value=result
                    }

                    override fun onFailed(msg: String?) {
                        dismissLoading()
                        showErrorToast(msg)
                    }
                })
        }
    }

}