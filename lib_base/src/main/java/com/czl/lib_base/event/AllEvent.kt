package com.czl.lib_base.event

import com.jeremyliao.liveeventbus.core.LiveEvent

/**
 * @author Alwyn
 * @Date 2020/10/15
 * @Description 页面通信事件
 */

data class TokenExpiredEvent(val msg: String?):LiveEvent
