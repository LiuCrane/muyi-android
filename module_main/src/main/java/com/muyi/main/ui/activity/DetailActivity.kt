package com.muyi.main.ui.activity

import android.content.res.Configuration
import android.widget.ImageView
import com.czl.lib_base.base.BaseActivity
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.ActivityDetailBinding
import com.muyi.main.detail.viewmodel.DetailViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils


/**
 * Created by hq on 2022/8/3.
 **/
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    private var orientationUtils: OrientationUtils?=null
    private var isPlay = false
    private var isPause = false


    override fun initContentView(): Int {
        return R.layout.activity_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel

    }

    override fun initData() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, binding.detailPlayer)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.ic_launcher)

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption.setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl("http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4")
            .setCacheWithPlay(false)
            .setVideoTitle("测试视频")
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils?.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[0]) //title
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[1]) //当前非全屏player
                    orientationUtils?.backToProtVideo()
                }
            }).setLockClickListener { _, lock ->
                orientationUtils?.isEnable = !lock
            }.build( binding.detailPlayer)

        binding.detailPlayer.fullscreenButton.setOnClickListener { //直接横屏
            orientationUtils?.resolveByClick()

            //第一个true是否需要隐藏actionBar，第二个true是否需要隐藏statusBar
            binding.detailPlayer.startWindowFullscreen(this, true, true)
        }
    }

    override fun onBackPressedSupport() {
        if (orientationUtils != null) {
            orientationUtils?.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressedSupport()
    }


    override fun onPause() {
        binding.detailPlayer.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        binding.detailPlayer.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            binding.detailPlayer.currentPlayer.release()
        }
        orientationUtils?.releaseListener()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            binding.detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}