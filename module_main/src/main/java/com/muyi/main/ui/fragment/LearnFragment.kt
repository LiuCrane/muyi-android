package com.muyi.main.ui.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import androidx.core.view.marginLeft
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.adapter.ViewPagerFmAdapter
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentLearnBinding
import com.muyi.main.viewmodel.LearnViewModel
import me.yokeyword.fragmentation.SupportFragment
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Main.F_LEARN)
class LearnFragment : BaseFragment<FragmentLearnBinding, LearnViewModel>() {
    private val channels = arrayOf("试听音频", "导学视频", "班级管理")


    override fun initContentView(): Int {
        return R.layout.fragment_learn
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {

        initViewPager()
        initMagicIndicator()
    }

    private fun initViewPager() {
        val audioFragment =
            RouteCenter.navigate(AppConstants.Router.Learn.F_AUDIO) as SupportFragment
        val videoFragment =
            RouteCenter.navigate(AppConstants.Router.Learn.F_VIDEO) as SupportFragment
        val classFragment =
            RouteCenter.navigate(AppConstants.Router.Learn.F_CLASS) as SupportFragment
        val fragments = arrayListOf(audioFragment, videoFragment, classFragment)

        binding.viewPager.apply {
            adapter = ViewPagerFmAdapter(childFragmentManager, lifecycle, fragments)
            // 优化体验设置该属性后第一次将自动加载所有fragment 在子fragment内部添加懒加载机制
            offscreenPageLimit = fragments.size

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.magicIndicator.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    binding.magicIndicator.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    binding.magicIndicator.onPageScrolled(
                        position,
                        positionOffset,
                        positionOffsetPixels
                    )

                }
            })
        }
    }

    private fun initMagicIndicator() {
        binding.magicIndicator.setBackgroundResource(R.drawable.bg_indicator)
        val commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return channels.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = channels[index]
                clipPagerTitleView.textColor = Color.parseColor("#443461FD")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setPadding(
                    UIUtil.dip2px(context, 13.0),
                    0,
                    UIUtil.dip2px(context, 13.0),
                    0
                )
                clipPagerTitleView.textSize = UIUtil.dip2px(context, 16.0).toFloat()
                clipPagerTitleView.setOnClickListener { binding.viewPager.currentItem = index }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                val navigatorHeight =
                    context.resources.getDimension(com.czl.lib_base.R.dimen.dp_50)
                val borderWidth = UIUtil.dip2px(context, 2.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.lineWidth = UIUtil.dip2px(context, 90.0).toFloat()
                indicator.lineHeight = lineHeight
                indicator.roundRadius =  UIUtil.dip2px(context, 70.0).toFloat()
                indicator.yOffset = borderWidth
                indicator.setColors(Color.parseColor("#3461FD"))
                return indicator
            }
        }
        binding.magicIndicator.navigator = commonNavigator
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer.setPadding(
            UIUtil.dip2px(context, 4.0),
            UIUtil.dip2px(context, 0.0),
            UIUtil.dip2px(context, 4.0),
            UIUtil.dip2px(context, 0.0)
        )
    }
}