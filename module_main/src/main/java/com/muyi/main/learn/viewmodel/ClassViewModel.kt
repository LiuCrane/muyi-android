package com.muyi.main.learn.viewmodel

import com.blankj.utilcode.util.LogUtils
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.ClassesBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class ClassViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var currentPage = 0

    val uc = UiChangeEvent()

    class UiChangeEvent {
        val refreshCompleteEvent: SingleLiveEvent<List<ClassesBean>> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        LogUtils.e("ClassViewModel onRefreshCommand")
        currentPage = 0
        getMediaList()
    })

    val onLoadMoreCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getMediaList()
    })

    private fun getMediaList() {
        model.apply {
            getClassList(
                currentPage,
                AppConstants.Common.PAGE_SIZE
            ).compose(RxThreadHelper.rxSchedulerHelper(this@ClassViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<List<ClassesBean>>>() {
                    override fun onResult(result: BaseBean<List<ClassesBean>>) {
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