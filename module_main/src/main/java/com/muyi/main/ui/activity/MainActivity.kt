package com.muyi.main.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.czl.lib_base.adapter.ViewPagerFmAdapter
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.ActivityMainBinding
import com.muyi.main.viewmodel.MainViewModel
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by hq on 2022/7/28.
 **/
@Route(path = AppConstants.Router.Main.A_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private var touchTime: Long = 0L

    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initViewObservable() {
        viewModel.uc.tabChangeLiveEvent.observe(this) {
            binding.viewPager2.setCurrentItem(it, false)
        }
        viewModel.uc.pageChangeLiveEvent.observe(this) {
            binding.bottomBar.selectTab(it)
        }
    }

    override fun initData() {
        initBottomBar()
        initViewPager()
    }

    private fun initViewPager() {
        // 设置不可滑动
        binding.viewPager2.isUserInputEnabled = false
        val learnFragment =
            RouteCenter.navigate(AppConstants.Router.Main.F_LEARN) as SupportFragment
        val progressFragment =
            RouteCenter.navigate(AppConstants.Router.Main.F_PROGRESS) as SupportFragment
        val myFragment =
            RouteCenter.navigate(AppConstants.Router.Main.F_MY) as SupportFragment
        val fragments = arrayListOf(learnFragment, progressFragment, myFragment)
        binding.viewPager2.apply {
            adapter = ViewPagerFmAdapter(supportFragmentManager, lifecycle, fragments)
            offscreenPageLimit = fragments.size
        }
    }

    private fun initBottomBar() {
        binding.bottomBar.apply {
            setMode(BottomNavigationBar.MODE_FIXED)
            setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            addItem(
                BottomNavigationItem(
                    R.drawable.ic_learn_on,
                    getString(R.string.main_tab_learn)
                ).setActiveColorResource(R.color.md_theme_blue)
                    .setInactiveIconResource(R.drawable.ic_learn_off)
                    .setInActiveColorResource(R.color.md_theme_blue_20)
            )
            addItem(
                BottomNavigationItem(
                    R.drawable.ic_progress_on,
                    getString(R.string.main_tab_progress)
                ).setActiveColorResource(R.color.md_theme_blue)
                    .setInactiveIconResource(R.drawable.ic_progress_off)
                    .setInActiveColorResource(R.color.md_theme_blue_20)
            )
            addItem(
                BottomNavigationItem(
                    R.drawable.ic_my_on,
                    getString(R.string.main_tab_my)
                ).setActiveColorResource(R.color.md_theme_blue)
                    .setInactiveIconResource(R.drawable.ic_my_off)
                    .setInActiveColorResource(R.color.md_theme_blue_20)
            )

            setFirstSelectedPosition(0)
            initialise()
        }
    }


    override fun onBackPressedSupport() {
        if (System.currentTimeMillis() - touchTime < 2000L) {
            AppManager.instance.appExit()
        } else {
            touchTime = System.currentTimeMillis()
            showNormalToast(getString(R.string.main_press_again))
        }
    }

}