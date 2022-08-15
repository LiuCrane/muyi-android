package com.muyi.main.viewmodel

import android.location.Location
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.GPSUtils
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/30.
 **/
class RegisterViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var userName = ObservableField("")
    var userPhone = ObservableField("")
    var userIdCard = ObservableField("")
    var storeName = ObservableField("")
    var storeLocation = ObservableField("")

    val onUserNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userName.set(it)
    })
    val onUserPhoneChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userPhone.set(it)
    })
    val onUserIdCardChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userIdCard.set(it)
    })
    val onStoreNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        storeName.set(it)
    })
    val onStoreLocationChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        storeLocation.set(it)
    })
    var btnRegisterClick: BindingCommand<Any> = BindingCommand(BindingAction {
        getLocation()
    })

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val successLiveEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    }

    private fun getLocation() {
        GPSUtils.getInstance(Utils.getApp())
            ?.getLngAndLat(object : GPSUtils.OnLocationResultListener {
                override fun onLocationResult(location: Location?) {
                    LogUtils.e("onLocationChange location latitude=" + location?.latitude + " longitude=" + location?.longitude)
                    register(location?.latitude, location?.longitude)
                }

                override fun onLocationChange(location: Location?) {
                    LogUtils.e("onLocationChange location latitude=" + location?.latitude + " longitude=" + location?.longitude)
                    register(location?.latitude, location?.longitude)
                }
            })
    }

    private fun register(latitude: Double?, longitude: Double?) {
        if (latitude == null || longitude == null) {
            showNormalToast("位置信息未获取，请打开手机定位服务")
            return
        }
        GPSUtils.getInstance(Utils.getApp())?.removeListener()

        if (userName.get().isNullOrBlank() || userPhone.get().isNullOrBlank() ||
            userIdCard.get().isNullOrBlank() || storeName.get().isNullOrBlank() ||
            storeLocation.get().isNullOrBlank()
        ) {
            showNormalToast("注册各项不能为空")
            return
        }

        model.apply {
            userRegister(
                userName.get()!!,
                "123456",
                userPhone.get()!!,
                userIdCard.get()!!,
                storeName.get()!!,
                storeLocation.get()!!,
                latitude.toString(),
                longitude.toString()
            ).compose(RxThreadHelper.rxSchedulerHelper(this@RegisterViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<UserBean>>() {
                    override fun onResult(result: BaseBean<UserBean>) {
                        dismissLoading()
                        if (result.code == 200) {
//                            result.data?.let {
//                                saveUserData(it)
//                            }
//                            RouteCenter.navigate(AppConstants.Router.Main.A_MAIN)
//                            AppManager.instance.finishAllActivity()
                            uc.successLiveEvent.call()
                        }
                    }

                    override fun onFailed(msg: String?) {
                        dismissLoading()
                        showNormalToast(msg)
                    }
                })
        }
    }

}