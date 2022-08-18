package com.muyi.main.classes.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.ClassesBean
import com.czl.lib_base.data.bean.StoreBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class ClassDetailViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var classId: String? = null
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getStoreInfoCompleteEvent: SingleLiveEvent<ClassesBean> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getClassDetailInfo()
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

    fun getClassDetailInfo() {
        if (classId.isNullOrEmpty()) {
            showErrorToast("班级Id为空")
            return
        }

        model.apply {
            getClassDetail(classId!!).compose(RxThreadHelper.rxSchedulerHelper(this@ClassDetailViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<ClassesBean>>() {
                    override fun onResult(result: BaseBean<ClassesBean>) {
                        if (result.code == 200) {
                            result.data?.let {
                                uc.getStoreInfoCompleteEvent.value = it
                            }
                        }
                    }

                    override fun onFailed(msg: String?) {
                        showNormalToast(msg)
                        uc.getStoreInfoCompleteEvent.value = null
                    }
                })
        }
    }
}