package com.muyi.main.viewmodel

import androidx.databinding.ObservableField
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
        register()
    })

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val successLiveEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    }

    private fun register() {
        model.apply {
            if (userName.get().isNullOrBlank() || userPhone.get().isNullOrBlank() ||
                userIdCard.get().isNullOrBlank() || storeName.get().isNullOrBlank() ||
                storeLocation.get().isNullOrBlank()
            ) {
                showNormalToast("注册各项不能为空")
                return
            }
            userRegister(
                userName.get()!!,
                "123456",
                userPhone.get()!!,
                userIdCard.get()!!,
                storeName.get()!!,
                storeLocation.get()!!,
                "123",
                "234"
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