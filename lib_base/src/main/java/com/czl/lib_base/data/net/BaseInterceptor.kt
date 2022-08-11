package com.czl.lib_base.data.net

import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.SpHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseInterceptor(private val headers: Map<String, String>?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
        builder.addHeader("Authorization", SpHelper.decodeString(AppConstants.SpKey.USER_TOKEN))
        if (headers != null && headers.isNotEmpty()) {
            val keys = headers.keys
            for (headerKey in keys) {
                headers[headerKey]?.let { builder.addHeader(headerKey, it).build() }
            }
        }
        //请求信息
        return chain.proceed(builder.build())
    }
}