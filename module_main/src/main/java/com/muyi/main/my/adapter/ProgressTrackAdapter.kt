package com.muyi.main.my.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.ClassesBean
import com.muyi.main.R
import com.muyi.main.databinding.ItemProgressTrackBinding
import com.muyi.main.my.ui.ProgressTrackFragment

/**
 * Created by hq on 2022/8/12.
 **/
class ProgressTrackAdapter(private val mFragment: ProgressTrackFragment) :
    BaseQuickAdapter<ClassesBean, BaseDataBindingHolder<ItemProgressTrackBinding>>(R.layout.item_progress_track) {


    override fun convert(
        holder: BaseDataBindingHolder<ItemProgressTrackBinding>,
        item: ClassesBean
    ) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@ProgressTrackAdapter
            executePendingBindings()
            tvCurrent.text = when (item.study_progress) {
                "NOT_STARTED" -> "未开始"
                "IN_PROGRESS" -> item.current_course
                "REHAB_TRAINING" -> "复训中"
                "ENDED" -> "已完结"
                else -> "未开始"
            }

        }
    }

    val onItemClickCommand: BindingCommand<Any?> = BindingCommand(BindingConsumer {
        if (it is ClassesBean) {
            mFragment.startContainerActivity(
                AppConstants.Router.ClassManage.F_CLASS_MANAGE,
                Bundle().apply { putString(AppConstants.BundleKey.KEY_ClASS_ID, it.id) })
        }
    })

    val diffConfig = object : DiffUtil.ItemCallback<ClassesBean>() {
        override fun areItemsTheSame(
            oldItem: ClassesBean,
            newItem: ClassesBean
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ClassesBean,
            newItem: ClassesBean
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.study_progress == newItem.study_progress
        }
    }
}