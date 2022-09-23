package com.muyi.main.detail.ui

import android.content.res.Configuration
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ResourceUtils
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.extension.loadUrl
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.ActivityDetailBinding
import com.muyi.main.detail.adapter.DetailMediaAdapter
import com.muyi.main.detail.viewmodel.DetailViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.model.VideoOptionModel
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Detail.A_DETAIL)
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    var classId: String? = null
    var courseId: String? = null
    var mediaId: String? = null
    var mediaType: String? = null

    lateinit var mAdapter: DetailMediaAdapter
    private var orientationUtils: OrientationUtils? = null
    private var gsyVideoOption: GSYVideoOptionBuilder = GSYVideoOptionBuilder()
    private var isPlay = false
    private var isPause = false

    //seek touch
    private var mHadSeekTouch = false
    private var currentMediaIndex = 0

    private var timer: Timer? = null
    private var timerTask = object : TimerTask() {
        override fun run() {
            viewModel.getCourseStatus()
        }
    }


    override fun initContentView(): Int {
        return R.layout.activity_detail
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
        timer= Timer()
        timer?.schedule(timerTask, 1000, 5000)
    }

    private fun initAdapter() {
        mAdapter = DetailMediaAdapter(this)
        mAdapter.setDiffCallback(mAdapter.diffConfig)
        binding.ryCommon.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            if (currentMediaIndex != position) {
                currentMediaIndex = position
                chooseToPlay(currentMediaIndex, true)
            }
        }
    }

    override fun initViewObservable() {
        viewModel.uc.getMediaListCompleteEvent.observe(this) {
            binding.smartCommon.finishRefresh(500)
            if (it.list.isNullOrEmpty()) {
                //
            } else {
                mAdapter.setDiffNewData(it.list)
                currentMediaIndex = 0
                chooseToPlay(currentMediaIndex, false)
            }
        }
    }

    private fun initVideoPlayer() {

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, binding.detailPlayer)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(com.czl.lib_base.R.drawable.ic_video_holder)
        gsyVideoOption.setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setRotateWithSystem(false)
            .setLockLand(true)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setCacheWithPlay(false)
            .setStartAfterPrepared(true)
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    LogUtils.e("onPrepared")
                    //开始播放了才能旋转和全屏
                    orientationUtils!!.isEnable = binding.detailPlayer.isRotateWithSystem
                    isPlay = true
                    binding.ivPause.setImageResource(R.drawable.ic_pause)
                    viewModel.recordMediaPlayer(mAdapter.data[currentMediaIndex].id, "START")
                }

                override fun onQuitFullscreen(url: String, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    orientationUtils?.backToProtVideo()
                }

                //点击了开始按键播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onClickStartIcon(url: String?, vararg objects: Any?) {
                    LogUtils.e("onClickStartIcon")
                    binding.ivPause.setImageResource(R.drawable.ic_pause)
                }

                //点击了错误状态下的开始按键，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onClickStartError(url: String?, vararg objects: Any?) {
                    LogUtils.e("onClickStartError")
                    binding.ivPause.setImageResource(R.drawable.ic_pause)
                }

                //点击了播放状态下的开始按键--->停止，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onClickStop(url: String?, vararg objects: Any?) {
                    LogUtils.e("onClickStop")
                    binding.ivPause.setImageResource(R.drawable.ic_play)
                    viewModel.recordMediaPlayer(mAdapter.data[currentMediaIndex].id, "PAUSE")
                }

                //点击了全屏播放状态下的开始按键--->停止，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {
                    LogUtils.e("onClickStopFullscreen")
                    binding.ivPause.setImageResource(R.drawable.ic_play)
                    viewModel.recordMediaPlayer(mAdapter.data[currentMediaIndex].id, "PAUSE")
                }

                //点击了暂停状态下的开始按键--->播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onClickResume(url: String?, vararg objects: Any?) {
                    LogUtils.e("onClickResume")
                    binding.ivPause.setImageResource(R.drawable.ic_pause)
                }

                //点击了全屏暂停状态下的开始按键--->播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {
                    LogUtils.e("onClickResumeFullscreen")
                    binding.ivPause.setImageResource(R.drawable.ic_pause)
                }

                //播放完了，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onAutoComplete(url: String?, vararg objects: Any?) {
                    LogUtils.e("onAutoComplete")
                    resetControlView()
                    viewModel.recordMediaPlayer(mAdapter.data[currentMediaIndex].id, "END")
                }

                //非正常播放完了，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onComplete(url: String?, vararg objects: Any?) {
                    LogUtils.e("onComplete")
                    resetControlView()
                }

                //播放错误，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                override fun onPlayError(url: String?, vararg objects: Any?) {
                    LogUtils.e("onPlayError")
                    resetControlView()
                }

            }).setLockClickListener { _, lock ->
                orientationUtils?.isEnable = !lock
            }
            .build(binding.detailPlayer)

        val videoOptionModel =
            VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1)
        val list: MutableList<VideoOptionModel> = ArrayList()
        list.add(videoOptionModel)
        GSYVideoManager.instance().optionModelList = list

        binding.detailPlayer.fullscreenButton.setOnClickListener { //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionBar，第二个true是否需要隐藏statusBar
            binding.detailPlayer.startWindowFullscreen(this, true, true)
        }
        binding.detailPlayer.setGSYVideoProgressListener { progress, _, currentPosition, duration ->
            if (currentPosition > 0)
                binding.tvTime.text = CommonUtil.stringForTime(currentPosition)
            binding.tvDuration.text = CommonUtil.stringForTime(duration)
            if (progress > 0) binding.sbProgress.progress = progress.toInt()
        }
        resetControlView()
        handleForwardAndBackward()
        binding.ivPause.setOnClickListener {
            binding.detailPlayer.clickStartButton()
        }
//        binding.sbProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                showDragProgressTextOnSeekBar(fromUser, progress)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                mHadSeekTouch = true
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                if (binding.detailPlayer.mHadPlay)
//                    try {
//                        val time: Long = seekBar!!.progress * binding.detailPlayer.duration / 100
//                        binding.detailPlayer.seekTo(time)
//                    } catch (e: java.lang.Exception) {
//                        e.printStackTrace()
//                    }
//
//                mHadSeekTouch = false
//            }
//        })

    }

    private fun resetControlView() {
        binding.sbProgress.progress = 0
        binding.tvTime.text = CommonUtil.stringForTime(0)
        binding.ivPause.setImageResource(R.drawable.ic_play)
    }


    private fun play(mediaBean: MediaBean, isAutoPlay: Boolean) {
        binding.sbProgress.progress = 0
        binding.tvTime.text = CommonUtil.stringForTime(0)

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.loadUrl(
            mediaBean.img,
            ResourceUtils.getDrawable(com.czl.lib_base.R.drawable.ic_video_holder)
        )
        gsyVideoOption
            .setThumbImageView(imageView)
            .setUrl(mediaBean.url)
            .setVideoTitle(mediaBean.title)
            .build(binding.detailPlayer)

        if (isAutoPlay)
            binding.detailPlayer.clickStartButton()
    }

    private fun chooseToPlay(position: Int, isAutoPlay: Boolean) {
        binding.data = mAdapter.data[position]
        play(mAdapter.data[position], isAutoPlay)
        mAdapter.setPlayPosition(position)
        handleForwardAndBackward()
    }

    private fun handleForwardAndBackward() {
        if (currentMediaIndex == 0) {
            binding.ivBackward.setImageResource(R.drawable.ic_play_backward_unclicked)
            binding.ivBackward.setOnClickListener(null)
        } else {
            binding.ivBackward.setImageResource(R.drawable.ic_play_backward)
            binding.ivBackward.setOnClickListener {
                currentMediaIndex--
                chooseToPlay(currentMediaIndex, true)
            }
        }
        if (currentMediaIndex == mAdapter.data.size - 1) {
            binding.ivForward.setImageResource(R.drawable.ic_play_forward_unclicked)
            binding.ivForward.setOnClickListener(null)
        } else {
            binding.ivForward.setImageResource(R.drawable.ic_play_forward)
            binding.ivForward.setOnClickListener {
                currentMediaIndex++
                chooseToPlay(currentMediaIndex, true)
            }
        }

    }


    private fun getCurPlayer(): GSYVideoPlayer? {
        return if (binding.detailPlayer.fullWindowPlayer != null) {
            binding.detailPlayer.fullWindowPlayer
        } else binding.detailPlayer
    }

    private fun showDragProgressTextOnSeekBar(fromUser: Boolean, progress: Int) {
        if (fromUser) {
            val duration: Long = binding.detailPlayer.duration
            binding.tvTime.text = CommonUtil.stringForTime(progress * duration / 100)
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
        getCurPlayer()?.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        getCurPlayer()?.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            getCurPlayer()?.release()
        }
        orientationUtils?.releaseListener()
        timer?.cancel()
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