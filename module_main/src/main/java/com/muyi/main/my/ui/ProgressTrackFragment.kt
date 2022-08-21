package com.muyi.main.my.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.ClassesBean
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentProgressTrackBinding
import com.muyi.main.my.adapter.ProgressTrackAdapter
import com.muyi.main.my.viewmodel.ProgressTrackViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.My.F_PROGRESS_TRACK)
class ProgressTrackFragment : BaseFragment<FragmentProgressTrackBinding, ProgressTrackViewModel>() {
    private var firstLoad = true
    lateinit var mAdapter: ProgressTrackAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_progress_track
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        viewModel.tvTitle.set("进度跟踪")
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = ProgressTrackAdapter(this)
        mAdapter.setDiffCallback(mAdapter.diffConfig)
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
                binding.smartCommon.finishLoadMore(false)
                return@Observer
            }
            // 成功加载数据后关闭懒加载开关
            firstLoad = false

            if (it.size < AppConstants.Common.PAGE_SIZE) {
                binding.smartCommon.finishLoadMoreWithNoMoreData()
            } else {
                binding.smartCommon.finishLoadMore(true)
            }
            if (viewModel.currentPage > 2) {
                mAdapter.addData(it)
                return@Observer
            }
            mAdapter.setDiffNewData(it as MutableList<ClassesBean>)
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

    override fun reload() {
        super.reload()
        binding.smartCommon.autoRefresh()
    }
}