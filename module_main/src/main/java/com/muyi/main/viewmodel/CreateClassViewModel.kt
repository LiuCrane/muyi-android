package com.muyi.main.viewmodel

import androidx.databinding.ObservableField
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/30.
 **/
class CreateClassViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    var name = ObservableField("")

    val onAccountChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        name.set(it)
    })


    var btnLoginClick: BindingCommand<Any> = BindingCommand(BindingAction {
        loginByPwd()
    })


    private fun loginByPwd() {
        model.apply {
            if (name.get().isNullOrBlank()) {
                showNormalToast("班级名称不能为空")
                return
            }
            createClass(
                name.get()!!
            ).compose(RxThreadHelper.rxSchedulerHelper(this@CreateClassViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<*>>() {
                    override fun onResult(result: BaseBean<*>) {
                        dismissLoading()
                        if (result.code == 200) {
                            showNormalToast("班级创建成功")
                            finish()
                        }
                    }

                    override fun onFailed(msg: String?) {
                        dismissLoading()
                        showNormalToast(msg)
                    }
                })
        }
    }

}