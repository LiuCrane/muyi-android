package com.muyi.main.viewmodel

import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.ListDataBean
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.data.bean.ProvinceBean
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
    var userPassword = ObservableField("")
    var userIdCard = ObservableField("")
    var storeName = ObservableField("")
    var storeRegion = ObservableField("")
    var storeRegionId = ObservableField("")
    var storeLocation = ObservableField("")
    var hasGetLocation = false

    val onUserNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userName.set(it)
    })
    val onUserPhoneChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userPhone.set(it)
    })
    val onUserPwdChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userPassword.set(it)
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
    val onRegionChooseCommand: BindingCommand<Any> = BindingCommand(BindingAction {
        uc.chooseRegionEvent.call()
    })
    var btnRegisterClick: BindingCommand<Any> = BindingCommand(BindingAction {
        hasGetLocation = false
        getLocation()
    })

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val getAllAddressEvent: SingleLiveEvent<ListDataBean<ProvinceBean>> = SingleLiveEvent()
        val chooseRegionEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val successLiveEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    }

    fun getAllAddress() {
        model.apply {
            getAllAddress().compose(RxThreadHelper.rxSchedulerHelper(this@RegisterViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<ListDataBean<ProvinceBean>>>() {
                    override fun onResult(result: BaseBean<ListDataBean<ProvinceBean>>) {
                        if (result.code == 200) {
                            uc.getAllAddressEvent.postValue(result.data)
                        } else {
                            uc.getAllAddressEvent.postValue(null)
                        }
                    }

                    override fun onFailed(msg: String?) {
                        showErrorToast(msg)
                    }
                })
        }
    }

    private fun getLocation() {
        showLoading()
        Handler(Looper.myLooper()!!).postDelayed({
            GPSUtils.getInstance(Utils.getApp())
                ?.getLngAndLat(object : GPSUtils.OnLocationResultListener {
                    override fun onLocationResult(location: Location?) {
                        LogUtils.e("onLocationChange location latitude=" + location?.latitude + " longitude=" + location?.longitude)
//                    register(location?.latitude, location?.longitude)
                    }

                    override fun onLocationChange(location: Location?) {
                        LogUtils.e("onLocationChange location latitude=" + location?.latitude + " longitude=" + location?.longitude)
                        register(location?.latitude, location?.longitude)
                    }

                    override fun onOpenSetting() {
                        dismissLoading()
                        showNormalToast("位置信息未获取，请打开手机定位服务再登录")
                    }
                })
        }, 500)
    }

    private fun register(latitude: Double?, longitude: Double?) {
        if (hasGetLocation)
            return

        hasGetLocation = true
        GPSUtils.getInstance(Utils.getApp())?.removeListener()

        if (latitude == null || longitude == null) {
            showNormalToast("位置信息未获取，请打开手机定位服务重新登录")
            dismissLoading()
            return
        }

        if (userName.get().isNullOrBlank() || userPhone.get().isNullOrBlank() ||
            userPassword.get().isNullOrBlank() || userIdCard.get().isNullOrBlank() ||
            storeName.get().isNullOrBlank() || storeRegionId.get().isNullOrBlank() ||
            storeLocation.get().isNullOrBlank()
        ) {
            showNormalToast("注册各项不能为空")
            dismissLoading()
            return
        }

        model.apply {
            userRegister(
                userName.get()!!,
                userPassword.get()!!,
                userPhone.get()!!,
                userIdCard.get()!!,
                storeName.get()!!,
                storeRegionId.get()!!,
                storeLocation.get()!!,
                latitude.toString(),
                longitude.toString()
            ).compose(RxThreadHelper.rxSchedulerHelper(this@RegisterViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<*>>() {
                    override fun onResult(result: BaseBean<*>) {
                        dismissLoading()
                        if (result.code == 200) {
                            uc.successLiveEvent.call()
                        }
                    }

                    override fun onFailed(msg: String?) {
                        dismissLoading()
                        showErrorToast(msg)
                    }
                })
        }
    }

}