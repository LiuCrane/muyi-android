package com.muyi.main.learn.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class AudioViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var currentPage = 0

    val uc = UiChangeEvent()

    class UiChangeEvent {
        val refreshCompleteEvent: SingleLiveEvent<List<MediaBean>> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        currentPage = 0
        getMediaList()
    })

    val onLoadMoreCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getMediaList()
    })

    private fun getMediaList() {
        model.apply {
            getMediaList(
                currentPage,
                20,
                "AUDIO",
                1111,
                true
            ).compose(RxThreadHelper.rxSchedulerHelper(this@AudioViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<List<MediaBean>>>() {
                    override fun onResult(result: BaseBean<List<MediaBean>>) {
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