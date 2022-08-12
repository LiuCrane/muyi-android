package com.muyi.main.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/29.
 **/
class MainViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val tabChangeLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()
        val pageChangeLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    }

    val onTabSelectedListener: BindingCommand<Int> = BindingCommand(BindingConsumer {
        uc.tabChangeLiveEvent.postValue(it)
    })

    val onPageSelectedListener: BindingCommand<Int> = BindingCommand(BindingConsumer {
        uc.pageChangeLiveEvent.postValue(it)
    })

}