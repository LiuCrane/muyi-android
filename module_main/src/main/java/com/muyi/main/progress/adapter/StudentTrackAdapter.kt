package com.muyi.main.progress.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.StudentBean
import com.muyi.main.R
import com.muyi.main.databinding.ItemStudentTrackBinding
import com.muyi.main.progress.ui.StudentTrackFragment

/**
 * Created by hq on 2022/8/12.
 **/
class StudentTrackAdapter(private val mFragment: StudentTrackFragment) :
    BaseQuickAdapter<StudentBean, BaseDataBindingHolder<ItemStudentTrackBinding>>(R.layout.item_student_track) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemStudentTrackBinding>,
        item: StudentBean
    ) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@StudentTrackAdapter
            executePendingBindings()

            if (item.improved == "true") {
                tvImprove.setBackgroundResource(R.drawable.bg_sight_improved)
                tvImprove.text = "有提升"
            } else if (item.improved == "false") {
                tvImprove.setBackgroundResource(R.drawable.bg_sight_not_improved)
                tvImprove.text = "无提升"
            } else {
                tvImprove.setBackgroundResource(R.drawable.bg_sight_unchange)
                tvImprove.text = "无变化"
            }

            tvLeftVision.text = "双眼视力 " + item.binocular_vision
//            tvLeftVision.text = "L " + item.left_vision
//            tvRightVision.text = "R " + item.right_vision

        }
    }

    val onItemClickCommand: BindingCommand<Any?> = BindingCommand(BindingConsumer {
        if (it is StudentBean) {
            mFragment.startContainerActivity(
                AppConstants.Router.Progress.F_STUDENT_DETAIL,
                Bundle().apply { putString(AppConstants.BundleKey.KEY_ClASS_ID, it.id) })
        }
    })

    val diffConfig = object : DiffUtil.ItemCallback<StudentBean>() {
        override fun areItemsTheSame(
            oldItem: StudentBean,
            newItem: StudentBean
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: StudentBean,
            newItem: StudentBean
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.current_course == newItem.current_course
        }
    }
}