package com.muyi.main.classes.ui

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.classes.viewmodel.ClassDetailViewModel
import com.muyi.main.databinding.FragmentClassDetailBinding

/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.ClassManage.F_CLASS_DETAIL)
class ClassDetailFragment : BaseFragment<FragmentClassDetailBinding, ClassDetailViewModel>() {

    @JvmField
    @Autowired
    var classId: String? = null

    override fun initContentView(): Int {
        return R.layout.fragment_class_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
        viewModel.classId = classId
        viewModel.getClassDetailInfo()
        binding.smartCommon.setEnableLoadMore(false)
    }

    override fun initViewObservable() {
        viewModel.uc.getStoreInfoCompleteEvent.observe(this) {
            binding.smartCommon.finishRefresh(500)
            binding.data = it
            binding.tvCurrent.text = when (it.study_progress) {
                "NOT_STARTED" -> "未开始"
                "IN_PROGRESS" -> it.current_course
                "REHAB_TRAINING" -> "复训中"
                "ENDED" -> "已完结"
                else -> "未开始"
            }
        }
    }
}