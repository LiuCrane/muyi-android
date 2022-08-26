package com.czl.lib_base.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat

/**
 * Created by hq on 2022/8/13.
 */
class GPSUtils private constructor(private val mContext: Context) {
    private var locationManager: LocationManager? = null

    /**
     * 获取经纬度
     *
     * @return
     */
    fun getLngAndLat(onLocationResultListener: OnLocationResultListener?): String? {
        mOnLocationListener = onLocationResultListener
        var locationProvider: String? = null
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //获取所有可用的位置提供器
        val providers = locationManager!!.getProviders(true)
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER
        } else {
            val i = Intent()
            i.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            mContext.startActivity(i)
            return null
        }

        //监视地理位置变化
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        //获取Location
        val location = locationManager!!.getLastKnownLocation(locationProvider)
        if (location != null) {
            //不为空,显示地理位置经纬度
            if (mOnLocationListener != null) {
                mOnLocationListener!!.onLocationResult(location)
            }
        }
        locationManager!!.requestLocationUpdates(locationProvider, 3000, 1f, locationListener)
        return null
    }

    var locationListener: LocationListener = object : LocationListener {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        override fun onProviderEnabled(provider: String) {}

        // Provider被disable时触发此函数，比如GPS被关闭
        override fun onProviderDisabled(provider: String) {}

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        override fun onLocationChanged(location: Location) {
            if (mOnLocationListener != null) {
                mOnLocationListener!!.onLocationChange(location)
            }
        }
    }

    fun removeListener() {
        mOnLocationListener = null
        locationManager!!.removeUpdates(locationListener)
    }

    private var mOnLocationListener: OnLocationResultListener? = null

    interface OnLocationResultListener {
        fun onLocationResult(location: Location?)
        fun onLocationChange(location: Location?)
    }

    companion object {
        private var instance: GPSUtils? = null
        fun getInstance(context: Context): GPSUtils? {
            if (instance == null) {
                instance = GPSUtils(context)
            }
            return instance
        }
    }
}