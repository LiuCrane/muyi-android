package com.muyi.main.classes.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.ListDataBean
import com.czl.lib_base.data.bean.StudentBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class SignInViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var classId: String? = null
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val refreshCompleteEvent: SingleLiveEvent<List<StudentBean>> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getStudentList()
    })


    private fun getStudentList() {
        if (classId.isNullOrEmpty()) {
            showErrorToast("班级Id为空")
            return
        }

        model.apply {
            getClassStudents(
                classId!!,
            ).compose(RxThreadHelper.rxSchedulerHelper(this@SignInViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<ListDataBean<StudentBean>>>(loadService) {
                    override fun onResult(result: BaseBean<ListDataBean<StudentBean>>) {
                        if (result.code == 200) {
                            uc.refreshCompleteEvent.postValue(result.data?.list)
                        } else {
                            uc.refreshCompleteEvent.postValue(null)
                        }
                    }

                    override fun onFailed(msg: String?) {
                        showErrorToast(msg)
                        uc.refreshCompleteEvent.postValue(null)
                    }
                })
        }
    }
}