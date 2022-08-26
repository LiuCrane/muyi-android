package com.muyi.main.my.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.ClassesBean
import com.czl.lib_base.data.bean.MediaBean
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentChooseClassBinding
import com.muyi.main.learn.adapter.LearnClassAdapter
import com.muyi.main.my.adapter.ChooseClassAdapter
import com.muyi.main.my.viewmodel.ChooseClassViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.My.F_CHOOSE_CLASS)
class ChooseClassFragment : BaseFragment<FragmentChooseClassBinding, ChooseClassViewModel>() {
    private var firstLoad = true
    lateinit var mAdapter: ChooseClassAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_choose_class
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        viewModel.tvTitle.set("选择班级")

        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = ChooseClassAdapter(this)
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