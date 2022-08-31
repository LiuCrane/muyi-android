package com.muyi.main.progress.viewmodel

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
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.GPSUtils
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/30.
 **/
class AddVisionViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var studentId: String? = null

    var leftVision = ObservableField("")
    var rightVision = ObservableField("")

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val successLiveEvent: SingleLiveEvent<BaseBean<UserBean>> = SingleLiveEvent()
    }


    val onLeftChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        leftVision.set(it)
    })

    val onRightChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        rightVision.set(it)
    })

    var btnSureClick: BindingCommand<Any> = BindingCommand(BindingAction {
        addVision()
    })


    private fun addVision() {
        model.apply {
            if (studentId.isNullOrEmpty()) {
                showNormalToast("学生Id不能为空")
                return
            }
            if (leftVision.get().isNullOrEmpty()) {
                showNormalToast("左眼视力不能为空")
                return
            }
            if (rightVision.get().isNullOrEmpty()) {
                showNormalToast("右眼视力不能为空")
                return
            }
            updateVision(
                studentId!!,
                leftVision.get()!!,
                rightVision.get()!!
            ).compose(RxThreadHelper.rxSchedulerHelper(this@AddVisionViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<*>>() {
                    override fun onResult(result: BaseBean<*>) {
                        dismissLoading()
                        if (result.code == 200) {
                            showNormalToast("添加视力信息成功")
                            LiveBusCenter.postAddVisionEvent("success")
                            finish()
                        }else{
                            showErrorToast(result.msg)
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