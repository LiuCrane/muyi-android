package com.muyi.main.progress.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.StudentBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class StudentTrackViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var rehabType: String? = null
    var currentPage = 0

    val uc = UiChangeEvent()

    class UiChangeEvent {
        val refreshCompleteEvent: SingleLiveEvent<List<StudentBean>> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        currentPage = 0
        getDataList()
    })

    val onLoadMoreCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getDataList()
    })

    private fun getDataList() {
        model.apply {
            getStudentList(
                currentPage,
                20,
                rehabType
            ).compose(RxThreadHelper.rxSchedulerHelper(this@StudentTrackViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<List<StudentBean>>>() {
                    override fun onResult(result: BaseBean<List<StudentBean>>) {
                        if (result.code == 200) {
                            currentPage++
                            uc.refreshCompleteEvent.postValue(result.data)
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