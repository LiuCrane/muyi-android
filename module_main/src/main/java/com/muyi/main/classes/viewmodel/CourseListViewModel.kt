package com.muyi.main.classes.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.CourseBean
import com.czl.lib_base.data.bean.ListDataBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/8/3.
 **/
class CourseListViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var classId: String? = null
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val refreshCompleteEvent: SingleLiveEvent<List<CourseBean>> = SingleLiveEvent()
        val applyCourseSuccessEvent: SingleLiveEvent<String> = SingleLiveEvent()
    }

    val onRefreshCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        getCourseList()
    })


    fun getCourseList() {
        if (classId.isNullOrEmpty()) {
            showErrorToast("班级Id为空")
            return
        }

        model.apply {
            getClassCourses(
                classId!!,
            ).compose(RxThreadHelper.rxSchedulerHelper(this@CourseListViewModel))
                .subscribe(object :
                    ApiSubscriberHelper<BaseBean<ListDataBean<CourseBean>>>(loadService) {
                    override fun onResult(result: BaseBean<ListDataBean<CourseBean>>) {
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

    fun applyCourse(courseId: String?) {
        if (classId.isNullOrEmpty() || courseId.isNullOrEmpty()) {
            showErrorToast("班级Id为空或者课程Id为空")
            return
        }

        model.apply {
            applyCourse(
                classId!!,
                courseId
            ).compose(RxThreadHelper.rxSchedulerHelper(this@CourseListViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<String>>() {
                    override fun onResult(result: BaseBean<String>) {
                        dismissLoading()
                        if (result.code == 200) {
                            uc.applyCourseSuccessEvent.value = courseId
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