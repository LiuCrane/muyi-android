package com.muyi.main.detail.ui

import android.content.res.Configuration
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ResourceUtils
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.extension.loadUrl
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentDetailBinding
import com.muyi.main.detail.adapter.DetailMediaAdapter
import com.muyi.main.detail.viewmodel.DetailViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Detail.F_DETAIL)
class DetailActivity : BaseActivity<FragmentDetailBinding, DetailViewModel>() {

    var classId: String? = null
    var courseId: String? = null
    var mediaId: String? = null
    var mediaType: String? = null

    lateinit var mAdapter: DetailMediaAdapter
    private var orientationUtils: OrientationUtils? = null
    private var isPlay = false
    private var isPause = false

    override fun initContentView(): Int {
        return R.layout.fragment_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        classId = intent.getStringExtra(AppConstants.BundleKey.KEY_ClASS_ID)
        courseId = intent.getStringExtra(AppConstants.BundleKey.KEY_COURSE_ID)
        mediaId = intent.getStringExtra(AppConstants.BundleKey.KEY_MEDIA_ID)
        mediaType = intent.getStringExtra(AppConstants.BundleKey.KEY_MEDIA_TYPE)

        if (mediaType == "AUDIO")
            viewModel.tvTitle.set("试听音频")
        else if (mediaType == "VIDEO")
            viewModel.tvTitle.set("导学视频")

        viewModel.classId = classId
        viewModel.courseId = courseId
        viewModel.mediaId = mediaId
        viewModel.mediaType = mediaType

        viewModel.getMediaList()
        binding.smartCommon.setEnableLoadMore(false)
        initAdapter()
        initVideoPlayer()
    }

    private fun initAdapter() {
        mAdapter = DetailMediaAdapter(this)
        mAdapter.setDiffCallback(mAdapter.diffConfig)
        binding.ryCommon.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun initViewObservable() {
        viewModel.uc.getMediaListCompleteEvent.observe(this) {
            binding.smartCommon.finishRefresh(500)
            if (it.list.isNullOrEmpty()) {

            } else {
                binding.data = it.list?.get(0)
                mAdapter.setDiffNewData(it.list)

                //增加封面
                val imageView = ImageView(this)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.loadUrl(
                    it.list?.get(0)!!.img,
                    ResourceUtils.getDrawable(com.czl.lib_base.R.drawable.ic_video_holder)
                )
                val gsyVideoOption = GSYVideoOptionBuilder()
                gsyVideoOption.setThumbImageView(imageView)
                    .setIsTouchWiget(true)
                    .setRotateViewAuto(false)
                    .setLockLand(false)
                    .setAutoFullWithSize(true)
                    .setShowFullAnimation(false)
                    .setNeedLockFull(true)
                    .setUrl(it.list?.get(0)!!.url)
                    .setCacheWithPlay(false)
                    .setVideoTitle(it.list?.get(0)!!.title)
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
                    }.build(binding.detailPlayer)
            }
        }
    }

    private fun initVideoPlayer() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, binding.detailPlayer)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false




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
            binding.detailPlayer.onConfigurationChanged(
                this,
                newConfig,
                orientationUtils,
                true,
                true
            )
        }
    }
}