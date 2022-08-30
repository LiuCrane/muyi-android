package com.muyi.main.progress.viewmodel

import android.os.Bundle
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.ClassesBean
import com.czl.lib_base.data.bean.StudentBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class StudentDetailViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var studentId: String? = null
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getStoreInfoCompleteEvent: SingleLiveEvent<StudentBean> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getClassDetailInfo()
    })

    var addVisionClick: BindingCommand<Any> = BindingCommand(BindingAction {
        if (studentId.isNullOrEmpty()) {
            showErrorToast("学生Id为空")
        }else{
           startContainerActivity(
                AppConstants.Router.Progress.F_ADD_VISION,
                Bundle().apply { putString(AppConstants.BundleKey.KEY_STUDENT_ID, studentId) })
        }
    })

    fun getClassDetailInfo() {
        if (studentId.isNullOrEmpty()) {
            showErrorToast("班级Id为空")
            return
        }

        model.apply {
            getStudentDetail(studentId!!).compose(RxThreadHelper.rxSchedulerHelper(this@StudentDetailViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<StudentBean>>() {
                    override fun onResult(result: BaseBean<StudentBean>) {
                        uc.getStoreInfoCompleteEvent.value = result.data
                    }

                    override fun onFailed(msg: String?) {
                        showNormalToast(msg)
                        uc.getStoreInfoCompleteEvent.value = null
                    }
                })
        }
    }
}