package com.muyi.main.classes.ui

import android.content.Context
import android.graphics.Color
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.czl.lib_base.adapter.ViewPagerFmAdapter
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.classes.viewmodel.ClassManageViewModel
import com.muyi.main.databinding.FragmentClassManageBinding
import com.muyi.main.databinding.FragmentLearnBinding
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
@Route(path = AppConstants.Router.ClassManage.F_CLASS_MANAGE)
class ClassManageFragment : BaseFragment<FragmentClassManageBinding, ClassManageViewModel>() {
    private var classId: String? = null
    private val channels = arrayOf("学员签到", "班级详情", "课程列表")


    override fun initContentView(): Int {
        return R.layout.fragment_class_manage
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        classId = arguments?.getString(AppConstants.BundleKey.KEY_STRING)
        LogUtils.e("classId=$classId")


        viewModel.tvTitle.set("班级管理")

        initViewPager()
        initMagicIndicator()
    }

    private fun initViewPager() {
        val signInFragment =
            RouteCenter.navigateWithKey(
                AppConstants.Router.ClassManage.F_SIGN_IN,
                classId
            ) as SupportFragment
        val classDetailFragment =
            RouteCenter.navigateWithKey(
                AppConstants.Router.ClassManage.F_CLASS_DETAIL,
                classId
            ) as SupportFragment
        val courseListFragment =
            RouteCenter.navigateWithKey(
                AppConstants.Router.ClassManage.F_COURSE_LIST,
                classId
            ) as SupportFragment
        val fragments = arrayListOf(signInFragment, classDetailFragment, courseListFragment)

        binding.viewPager.apply {
            adapter = ViewPagerFmAdapter(childFragmentManager, lifecycle, fragments)
            // 优化体验 设置该属性后第一次将自动加载所有fragment 在子fragment内部添加懒加载机制
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
                indicator.roundRadius = UIUtil.dip2px(context, 70.0).toFloat()
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