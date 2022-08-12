package com.muyi.main.ui.fragment.learn

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.MediaBean
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.adapter.LearnVideoAdapter
import com.muyi.main.databinding.FragmentVideoBinding
import com.muyi.main.viewmodel.VideoViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Learn.F_VIDEO)
class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>() {
    private var firstLoad = true
    lateinit var mAdapter: LearnVideoAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_video
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = LearnVideoAdapter(this)
        mAdapter.setDiffCallback(mAdapter.diffConfig)
        binding.ryCommon.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun initViewObservable() {
        // 接收加载完成的数据
        viewModel.uc.refreshCompleteEvent.observe(this, Observer {

            if (it == null) {
                binding.smartCommon.finishRefresh(500)
                binding.smartCommon.finishLoadMore(false)
                return@Observer
            }
            // 成功加载数据后关闭懒加载开关
            firstLoad = false
            binding.smartCommon.finishRefresh(500)

//            if (it.over) {
//                binding.smartCommon.finishLoadMoreWithNoMoreData()
//            } else {
//                binding.smartCommon.finishLoadMore(true)
//            }
            if (viewModel.currentPage > 1) {
                mAdapter.addData(it)
                return@Observer
            }
            mAdapter.setDiffNewData(it as MutableList<MediaBean>)
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