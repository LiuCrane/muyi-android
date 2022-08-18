package com.czl.lib_base.extension

import android.net.ParseException
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.util.ToastHelper.showErrorToast
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.kingja.loadsir.core.LoadService
import io.reactivex.observers.DisposableObserver
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Alwyn
 * @Date 2020/10/10
 * @Description RxJava 处理Api异常
 * 不自动处理状态页的不传构造即可
 */
abstract class ApiSubscriberHelper<T : Any>(private val loadService: LoadService<BaseBean<*>?>? = null) :
    DisposableObserver<T>() {

    override fun onNext(t: T) {
        if (t is BaseBean<*>) {
            loadService?.showWithConvertor(t)
        }
        if (t is BaseBean<*> && t.code == 400101) {
            LiveBusCenter.postTokenExpiredEvent(t.msg)
        } else if (t is BaseBean<*> && t.code != 200 && t.code != 400103 && t.code != 400104) {
            showErrorToast(t.msg)
        }
        onResult(t)
    }

    override fun onComplete() {}

    override fun onError(throwable: Throwable) {

        when (throwable) {
            is ConnectException, is ConnectTimeoutException, is UnknownHostException -> {
                onFailed("连接失败，请检查网络后再试")
            }
            is RuntimeException -> {
//                var failMessage = throwable.message
//                if (failMessage != null && failMessage.contains("HTTP")) {
//                    failMessage = throwable.message!!.substring(9)
//                }
                onFailed(throwable.message)
            }
            is SocketTimeoutException -> {
                onFailed("连接超时，请重试")
            }
            is IllegalStateException -> {
                onFailed(throwable.message)
            }
            is HttpException -> {
                onFailed("网络异常，请重试")
            }
            is JsonParseException, is JSONException, is JsonSyntaxException, is ParseException -> {
                onFailed("数据解析异常，请稍候再试")
            }
            else -> {
                onFailed(throwable.message)
            }
        }
    }

    protected abstract fun onResult(result: T)
    protected abstract fun onFailed(msg: String?)
}