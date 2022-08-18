package com.muyi.main.classes.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.CourseBean
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.classes.adapter.CourseListAdapter
import com.muyi.main.classes.viewmodel.CourseListViewModel
import com.muyi.main.databinding.FragmentCourseListBinding

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.ClassManage.F_COURSE_LIST)
class CourseListFragment : BaseFragment<FragmentCourseListBinding, CourseListViewModel>() {

    @JvmField
    @Autowired
    var keyString: String? = null

    private var firstLoad = true
    lateinit var mAdapter: CourseListAdapter


    override fun initContentView(): Int {
        return R.layout.fragment_course_list
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
        viewModel.classId = keyString
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = CourseListAdapter(this)
        mAdapter.setDiffCallback(mAdapter.diffConfig)
        binding.smartCommon.setEnableLoadMore(false)
        binding.ryCommon.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun initViewObservable() {
        // 接收加载完成的数据
        viewModel.uc.refreshCompleteEvent.observe(this, Observer {

            binding.smartCommon.finishRefresh(500)

            if (it.isNullOrEmpty()) {
                return@Observer
            }
            // 成功加载数据后关闭懒加载开关
            firstLoad = false
            mAdapter.setDiffNewData(it as MutableList<CourseBean>)
        })

    }


    override fun onResume() {
        super.onResume()
        // 懒加载
        if (firstLoad) {
            refreshData()
        }
    }

    private fun refreshData() {
        binding.smartCommon.autoRefresh()
    }
}