package com.muyi.main.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.czl.lib_base.adapter.ViewPagerFmAdapter
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.route.RouteCenter
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.ActivityMainBinding
import com.muyi.main.viewmodel.MainViewModel
import me.yokeyword.fragmentation.SupportFragment
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

/**
 * Created by hq on 2022/7/28.
 **/
@Route(path = AppConstants.Router.Main.A_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), KoinComponent {
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
        LiveBusCenter.observeTokenExpiredEvent(this) {
            val dataRepository: DataRepository by inject()
            dataRepository.deleteUserData()
            startContainerActivity(AppConstants.Router.Login.F_LOGIN)
            AppManager.instance.finishAllActivity()

        }
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
                ).setActiveColorResource(R.color.theme_blue)
                    .setInactiveIconResource(R.drawable.ic_learn_off)
                    .setInActiveColorResource(R.color.theme_blue_20)
            )
            addItem(
                BottomNavigationItem(
                    R.drawable.ic_progress_on,
                    getString(R.string.main_tab_progress)
                ).setActiveColorResource(R.color.theme_blue)
                    .setInactiveIconResource(R.drawable.ic_progress_off)
                    .setInActiveColorResource(R.color.theme_blue_20)
            )
            addItem(
                BottomNavigationItem(
                    R.drawable.ic_my_on,
                    getString(R.string.main_tab_my)
                ).setActiveColorResource(R.color.theme_blue)
                    .setInactiveIconResource(R.drawable.ic_my_off)
                    .setInActiveColorResource(R.color.theme_blue_20)
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