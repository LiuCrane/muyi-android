package com.czl.lib_base.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hq on 2022/8/21.
 **/
object DateUtils {
    private val DEFAULT_FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    val YYYY_MM_DD: DateFormat = SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA)

    /**
     * Milliseconds to the formatted time string.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param millis The milliseconds.
     * @return the formatted time string
     */
    fun millis2String(millis: Long): String? {
        return millis2String(millis, DEFAULT_FORMAT)
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis The milliseconds.
     * @param format The format.
     * @return the formatted time string
     */
    fun millis2String(millis: Long, format: DateFormat): String? {
        return format.format(Date(millis))
    }

    /**
     * Formatted time string to the date.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param time The formatted time string.
     * @return the date
     */
    fun string2Date(time: String?): Date? {
        return string2Date(time, DEFAULT_FORMAT)
    }

    /**
     * Formatted time string to the date.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the date
     */
    fun string2Date(time: String?, format: DateFormat): Date? {
        if (time == null)
            return null
        try {
            return format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }
}