package com.muyi.main.detail.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.ListDataBean
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.data.bean.StatusBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class DetailViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var classId: String? = null
    var courseId: String? = null
    var mediaId: String? = null
    var mediaType: String? = null

    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getMediaListCompleteEvent: SingleLiveEvent<ListDataBean<MediaBean>> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getMediaList()
    })

    fun getMediaList() {
        if (mediaId != null && mediaType != null) {
            //首页的音视频获取
            model.apply {
                getMediaList(
                    1,
                    AppConstants.Common.PAGE_SIZE,
                    mediaType!!
                ).compose(RxThreadHelper.rxSchedulerHelper(this@DetailViewModel))
                    .subscribe(object :
                        ApiSubscriberHelper<BaseBean<ListDataBean<MediaBean>>>(loadService) {
                        override fun onResult(result: BaseBean<ListDataBean<MediaBean>>) {
                            if (result.code == 200) {
                                uc.getMediaListCompleteEvent.postValue(result.data)
                            } else {
                                uc.getMediaListCompleteEvent.postValue(null)
                            }
                        }

                        override fun onFailed(msg: String?) {
                            showErrorToast(msg)
                            uc.getMediaListCompleteEvent.postValue(null)
                        }
                    })
            }
            return
        }

        if (classId.isNullOrEmpty() || courseId.isNullOrEmpty()) {
            showErrorToast("班级或课程信息未获取")
            return
        }

        model.apply {
            getCourseMediaList(
                classId!!,
                courseId!!
            ).compose(RxThreadHelper.rxSchedulerHelper(this@DetailViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<ListDataBean<MediaBean>>>() {
                    override fun onResult(result: BaseBean<ListDataBean<MediaBean>>) {
                        if (result.code == 200) {
                            uc.getMediaListCompleteEvent.postValue(result.data)
                        } else {
                            uc.getMediaListCompleteEvent.postValue(null)
                        }
                    }

                    override fun onFailed(msg: String?) {
                        showErrorToast(msg)
                        uc.getMediaListCompleteEvent.value = null
                    }
                })
        }
    }

    fun recordMediaPlayer(id: String?, event: String) {
        if (id.isNullOrEmpty()) {
            showErrorToast("media id 获取失败")
            return
        }
        model.apply {
            mediaPlay(
                id,
                classId,
                courseId,
                event
            ).compose(RxThreadHelper.rxSchedulerHelper(this@DetailViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<String>>() {
                    override fun onResult(result: BaseBean<String>) {
                        if (result.code != 200) {
                            showErrorToast(result.msg)
                        }
                    }

                    override fun onFailed(msg: String?) {
                        showErrorToast(msg)
                    }
                })
        }
    }

    fun getCourseStatus(){
        if (classId.isNullOrEmpty() || courseId.isNullOrEmpty()) {
            return
        }
        model.apply {
            getCourseStatus(
                classId!!,
                courseId!!
            ).compose(RxThreadHelper.rxSchedulerHelper(this@DetailViewModel))
                .subscribe(object : ApiSubscriberHelper<BaseBean<StatusBean>>() {
                    override fun onResult(result: BaseBean<StatusBean>) {
                        if (result.code == 200 && result.data?.status != "ACCESSIBLE") {
                            showNormalToast("课程状态已被修改")
                            LiveBusCenter.postChangeCourseStatusEvent(result.data?.status)
                            finish()
                        }
                    }

                    override fun onFailed(msg: String?) {
                    }
                })
        }
    }
}