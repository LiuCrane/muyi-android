package com.muyi.main.viewmodel

import com.alibaba.android.arouter.launcher.ARouter
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.BrowseRecordsBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.route.RouteCenter
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




//    override fun setToolbarRightClick() {
//        showNormalToast("标题右侧点击事件")
//        model.apply {
//            getBrowseRecords(10).compose(RxThreadHelper.rxSchedulerHelper(this@MainViewModel))
//                .doOnSubscribe { showLoading() }
//                .subscribe(object : ApiSubscriberHelper<BaseBean<BrowseRecordsBean>>() {
//                    override fun onResult(result: BaseBean<BrowseRecordsBean>) {
//                        dismissLoading()
//                        if (result.code == 200) {
//                            result.data?.let {
////                                saveUserData(it)
//                            }
//                        }
//                    }
//
//                    override fun onFailed(msg: String?) {
//                        dismissLoading()
//                        showNormalToast(msg)
//                    }
//                })
//
//        }
//    }


}