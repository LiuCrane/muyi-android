package com.muyi.main.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.StoreBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.route.RouteCenter
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class MyViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getStoreInfoCompleteEvent: SingleLiveEvent<StoreBean> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getStoreInfo()
    })

    var allianceClick: BindingCommand<Any> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.Main.F_CREATE_CLASS)
    })
    var progressClick: BindingCommand<Any> = BindingCommand(BindingAction {
//        startContainerActivity(AppConstants.Router.My.F_REGISTRATION)
    })
    var registrationClick: BindingCommand<Any> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.My.F_REGISTRATION)
    })


    fun getStoreInfo() {
        model.apply {
            getStoreInfo().compose(RxThreadHelper.rxSchedulerHelper(this@MyViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<StoreBean>>() {
                    override fun onResult(result: BaseBean<StoreBean>) {
                        dismissLoading()
                        if (result.code == 200) {
                            result.data?.let {
                                uc.getStoreInfoCompleteEvent.value = it
                            }
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