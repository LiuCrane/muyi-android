package com.muyi.main.progress.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentStudentDetailBinding
import com.muyi.main.progress.adapter.StudentEyeSightAdapter
import com.muyi.main.progress.viewmodel.StudentDetailViewModel

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Progress.F_STUDENT_DETAIL)
class StudentDetailFragment : BaseFragment<FragmentStudentDetailBinding, StudentDetailViewModel>() {

    @JvmField
    @Autowired
    var classId: String? = null

    lateinit var mAdapter: StudentEyeSightAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_student_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        viewModel.tvTitle.set("学员信息")
        viewModel.studentId = classId
        viewModel.getClassDetailInfo()
        binding.smartCommon.setEnableLoadMore(false)
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = StudentEyeSightAdapter(this)
        mAdapter.setDiffCallback(mAdapter.diffConfig)
        binding.ryCommon.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }
    override fun initViewObservable() {
        viewModel.uc.getStoreInfoCompleteEvent.observe(this) {
            binding.smartCommon.finishRefresh(500)
            binding.data = it
            mAdapter.setDiffNewData(it.eyesight_list)
        }
    }
}