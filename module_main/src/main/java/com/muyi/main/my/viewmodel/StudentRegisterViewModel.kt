package com.muyi.main.my.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableField
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
import com.czl.lib_base.data.bean.ProvinceBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/30.
 **/
class StudentRegisterViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var studentName = ObservableField("")
    var studentGender = ObservableField("")
    var studentAge = ObservableField("")
    var parentName = ObservableField("")
    var parentPhone = ObservableField("")
    var diopter = ObservableField("")
    var leftSight = ObservableField("")
    var rightSight = ObservableField("")
    var classId = ObservableField("")
    var className = ObservableField("")

    var storeRegion = ObservableField("")
    var storeRegionId = ObservableField("")
    var storeLocation = ObservableField("")


    val onStudentNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        studentName.set(it)
    })
    val onStudentAgeChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        studentAge.set(it)
    })
    val onParentNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        parentName.set(it)
    })
    val onParentPhoneChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        parentPhone.set(it)
    })
    val onLeftSightChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        leftSight.set(it)
    })
    val onRightSightChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        rightSight.set(it)
    })
    val onDiopterChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        diopter.set(it)
    })

    val onClassChooseCommand: BindingCommand<Any> = BindingCommand(BindingAction {
        val bundle = Bundle()
        bundle.putString("classId", "1")
        startContainerActivity(AppConstants.Router.My.F_CHOOSE_CLASS, bundle, 100001)
    })

    val onStoreLocationChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        storeLocation.set(it)
    })

    val onGenderChooseCommand: BindingCommand<Any> = BindingCommand(BindingAction {
        uc.chooseGenderEvent.call()
    })

    val onRegionChooseCommand: BindingCommand<Any> = BindingCommand(BindingAction {
        uc.chooseRegionEvent.call()
    })
    var btnRegisterClick: BindingCommand<Any> = BindingCommand(BindingAction {
        register()
    })

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val getAllAddressEvent: SingleLiveEvent<ListDataBean<ProvinceBean>> = SingleLiveEvent()
        val chooseRegionEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val chooseGenderEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    }

    fun getAllAddress() {
        model.apply {
            getAllAddress().compose(RxThreadHelper.rxSchedulerHelper(this@StudentRegisterViewModel))
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

    private fun register() {
        model.apply {
            if (studentName.get().isNullOrBlank() || studentAge.get()
                    .isNullOrBlank() || studentGender.get().isNullOrBlank() || parentName.get()
                    .isNullOrBlank() || parentPhone.get().isNullOrBlank() || leftSight.get()
                    .isNullOrBlank() || rightSight.get().isNullOrBlank() || diopter.get()
                    .isNullOrBlank() || classId.get().isNullOrBlank() || storeRegionId.get()
                    .isNullOrBlank() || storeLocation.get().isNullOrBlank()
            ) {
                showNormalToast("学员注册各项不能为空")
                return
            }
            studentRegister(
                studentName.get()!!,
                studentAge.get()!!,
                studentGender.get()!!,
                parentName.get()!!,
                parentPhone.get()!!,
                leftSight.get()!!,
                rightSight.get()!!,
                diopter.get()!!,
                classId.get()!!,
                storeRegionId.get()!!,
                storeLocation.get()!!,
            ).compose(RxThreadHelper.rxSchedulerHelper(this@StudentRegisterViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<*>>() {
                    override fun onResult(result: BaseBean<*>) {
                        dismissLoading()
                        if (result.code == 200) {
                            showNormalToast("学员注册成功")
                            finish()
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