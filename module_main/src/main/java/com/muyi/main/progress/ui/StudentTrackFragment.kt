package com.muyi.main.progress.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.MediaBean
import com.czl.lib_base.data.bean.StudentBean
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentStudentTrackBinding
import com.muyi.main.progress.adapter.StudentTrackAdapter
import com.muyi.main.progress.viewmodel.StudentTrackViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Progress.F_STUDENT_TRACK)
class StudentTrackFragment : BaseFragment<FragmentStudentTrackBinding, StudentTrackViewModel>() {
    private var firstLoad = true
    lateinit var mAdapter: StudentTrackAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_student_track
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
        mAdapter = StudentTrackAdapter(this)
        mAdapter.setDiffCallback(mAdapter.diffConfig)
        binding.ryCommon.apply {
            layoutManager = GridLayoutManager(context, 2)
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
                binding.smartCommon.finishLoadMore(true)
//            }
            if (viewModel.currentPage > 1) {
                mAdapter.addData(it)
                return@Observer
            }
            mAdapter.setDiffNewData(it as MutableList<StudentBean>)
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