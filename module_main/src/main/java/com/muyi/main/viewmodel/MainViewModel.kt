package com.muyi.main.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.BrowseRecordsBean
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**
 * Created by hq on 2022/7/29.
 **/
class MainViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    val name = "huangqiang"
    val pwd = "123456"
    var btnLoginClick: BindingCommand<Any> = BindingCommand(BindingAction {
        loginByPwd()
    })

    private fun loginByPwd() {
        model.apply {
            userLogin(name, pwd).compose(RxThreadHelper.rxSchedulerHelper(this@MainViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<UserBean>>() {
                    override fun onResult(result: BaseBean<UserBean>) {
                        dismissLoading()
                        if (result.code == 200) {
                            result.data?.let {
                                saveUserData(it)
                            }
//                            RouteCenter.navigate(AppConstants.Router.Main.A_MAIN)
//                            AppManager.instance.finishAllActivity()
                        }
                    }

                    override fun onFailed(msg: String?) {
                        dismissLoading()
                        showNormalToast(msg)
                    }
                })
        }
    }


    override fun setToolbarRightClick() {
        showNormalToast("标题右侧点击事件")
        model.apply {
            getBrowseRecords(10).compose(RxThreadHelper.rxSchedulerHelper(this@MainViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<BrowseRecordsBean>>() {
                    override fun onResult(result: BaseBean<BrowseRecordsBean>) {
                        dismissLoading()
                        if (result.code == 200) {
                            result.data?.let {
//                                saveUserData(it)
                            }
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