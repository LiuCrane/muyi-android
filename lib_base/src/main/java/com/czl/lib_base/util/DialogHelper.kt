package com.czl.lib_base.util

import android.content.Context
import com.czl.lib_base.R
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnConfirmListener

/**
 * @author Alwyn
 * @Date 2020/12/1
 * @Description
 */
object DialogHelper {

    fun showLoadingDialog(context: Context, title: String? = "加载中"): BasePopupView {
        return XPopup.Builder(context).asLoading(title, R.layout.common_loading_dialog).show()
    }

    fun showBaseDialog(
        context: Context,
        title: String,
        content: String,
        func: () -> Unit
    ): BasePopupView {
        return XPopup.Builder(context).asConfirm(title, content, OnConfirmListener(func)).show()
    }

    fun showVersionDialog(
        context: Context,
        title: String,
        content: String,
        func: () -> Unit
    ): BasePopupView {
        return XPopup.Builder(context)
            .asConfirm(title, content, "取消", "下载", OnConfirmListener(func), null, false).show()
    }

//    fun showLoadingDialog(context: Context, title: String? = "加载中"): BasePopupView {
//        return XPopup.Builder(context).asLoading(title, R.layout.common_loading_dialog).show()
//    }

    fun showNoCancelDialog(
        context: Context,
        title: String,
        content: String,
        func: () -> Unit
    ): BasePopupView {
        return XPopup.Builder(context)
            .asConfirm(title, content, "取消", "确定", OnConfirmListener(func), null, true, R.layout.dialog_one_button).show()
    }


}