package com.muyi.main.my.viewmodel

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
class StudentRegisterViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var studentName = ObservableField("")
    var parentName = ObservableField("")
    var parentPhone = ObservableField("")
    var userDegree = ObservableField("")
    var leftSight = ObservableField("")
    var rightSight = ObservableField("")
    var studentClass = ObservableField("")

    val onStudentNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        studentName.set(it)
    })
    val onParentNameChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        parentName.set(it)
    })
    val onParentPhoneChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        parentPhone.set(it)
    })
    val onDegreeChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        userDegree.set(it)
    })
    val onLeftSightChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        leftSight.set(it)
    })
    val onRightSightChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        rightSight.set(it)
    })
    val onClassChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        studentClass.set(it)
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
            if (studentName.get().isNullOrBlank() || parentName.get()
                    .isNullOrBlank() || parentPhone.get().isNullOrBlank() ||
                userDegree.get().isNullOrBlank() || leftSight.get()
                    .isNullOrBlank() || rightSight.get().isNullOrBlank() ||
                studentClass.get().isNullOrBlank()
            ) {
                showNormalToast("学员注册各项不能为空")
                return
            }
            studentRegister(
                studentName.get()!!,
                parentName.get()!!,
                parentPhone.get()!!,
                userDegree.get()!!,
                leftSight.get()!!,
                rightSight.get()!!,
                studentClass.get()!!
            ).compose(RxThreadHelper.rxSchedulerHelper(this@StudentRegisterViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<UserBean>>() {
                    override fun onResult(result: BaseBean<UserBean>) {
                        dismissLoading()
                        if (result.code == 200) {
                            showNormalToast("学员注册成功")
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