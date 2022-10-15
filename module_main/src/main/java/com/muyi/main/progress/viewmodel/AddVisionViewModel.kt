package com.muyi.main.progress.viewmodel

import androidx.databinding.ObservableField
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.CourseBean
import com.czl.lib_base.data.bean.ListDataBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/30.
 **/
class AddVisionViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var studentId: String? = null
    var leftVision = ObservableField("")
    var rightVision = ObservableField("")
    var doubleVision = ObservableField("")
    private var courseId = 0


    val onLeftChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        leftVision.set(it)
    })

    val onRightChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        rightVision.set(it)
    })

    val onDoubleChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        doubleVision.set(it)
    })


    var btnSureClick: BindingCommand<Any> = BindingCommand(BindingAction {
        addVision()
    })

    fun getCourse() {
        model.apply {
            if (studentId.isNullOrEmpty()) {
                showNormalToast("学生Id不能为空")
                return
            }
            getStudentCourse(studentId!!).compose(RxThreadHelper.rxSchedulerHelper(this@AddVisionViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<ListDataBean<CourseBean>>>() {
                    override fun onResult(result: BaseBean<ListDataBean<CourseBean>>) {
                        if (result.code == 200) {
                            var largestID = 0
                            result.data?.list?.forEach {
                                if ((it.id?.toInt() ?: 0) > largestID) {
                                    largestID = it.id?.toInt() ?: 0
                                }
                            }
                            if (largestID > 0)
                                courseId = largestID
                        }
                    }

                    override fun onFailed(msg: String?) {
                        showErrorToast(msg)
                    }
                })
        }
    }

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
            if (doubleVision.get().isNullOrEmpty()) {
                showNormalToast("双眼视力不能为空")
                return
            }
            if (courseId <= 0) {
                showNormalToast("请先完成课程")
                return
            }

            updateVision(
                studentId!!,
                doubleVision.get()!!,
                leftVision.get()!!,
                rightVision.get()!!,
                courseId
            ).compose(RxThreadHelper.rxSchedulerHelper(this@AddVisionViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<*>>() {
                    override fun onResult(result: BaseBean<*>) {
                        dismissLoading()
                        if (result.code == 200) {
                            showNormalToast("添加视力信息成功")
                            LiveBusCenter.postAddVisionEvent("success")
                            finish()
                        } else {
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