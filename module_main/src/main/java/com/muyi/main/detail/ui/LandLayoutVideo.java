package com.muyi.main.detail.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.Utils;
import com.muyi.main.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

/**
 * Created by hq on 2022/7/30.
 **/
public class LandLayoutVideo extends StandardGSYVideoPlayer {


    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public LandLayoutVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LandLayoutVideo(Context context) {
        super(context);
    }

    public LandLayoutVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void init(Context context) {
        super.init(context);

        post(new Runnable() {
            @Override
            public void run() {
                gestureDetector = new GestureDetector(Utils.getApp(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        touchDoubleUp(e);
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (!mChangePosition && !mChangeVolume && !mBrightness
                                && mCurrentState != CURRENT_STATE_ERROR
                        ) {
                            onClickUiToggle(e);
                        }
                        return super.onSingleTapConfirmed(e);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                    }
                });
            }
        });
    }

    //这个必须配置最上面的构造才能生效
    @Override
    public int getLayoutId() {
        if (mIfCurrentIsFullscreen) {
            return R.layout.layout_video_land;
        }
        return R.layout.layout_video_normal;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isIfCurrentIsFullscreen()) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    boolean getMHadPlay() {
        return mHadPlay;
    }

    void clickStartButton(){
        mStartButton.performClick();
    }

    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        LandLayoutVideo landLayoutVideo = (LandLayoutVideo) gsyVideoPlayer;
        landLayoutVideo.dismissProgressDialog();
        landLayoutVideo.dismissVolumeDialog();
        landLayoutVideo.dismissBrightnessDialog();
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
    }

    /**
     * 定义结束后的显示
     */
    @Override
    protected void changeUiToCompleteClear() {
        super.changeUiToCompleteClear();
        setViewShowState(mBottomContainer, INVISIBLE);
    }

    @Override
    protected void changeUiToCompleteShow() {
        super.changeUiToCompleteShow();
        setViewShowState(mBottomContainer, INVISIBLE);
    }

    @Override
    public int getEnlargeImageRes() {
        return com.czl.lib_base.R.mipmap.custom_enlarge;
    }

    @Override
    public int getShrinkImageRes() {
        return com.czl.lib_base.R.mipmap.custom_shrink;
    }

}
