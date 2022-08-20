package com.muyi.main.classes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.data.bean.CourseBean
import com.czl.lib_base.extension.loadImageRes
import com.czl.lib_base.extension.loadUrl
import com.muyi.main.R
import com.muyi.main.classes.ui.CourseListFragment
import com.muyi.main.databinding.ItemCourseBinding

/**
 * Created by hq on 2022/8/12.
 **/
class CourseListAdapter(private val mFragment: CourseListFragment) :
    BaseQuickAdapter<CourseBean, BaseDataBindingHolder<ItemCourseBinding>>(R.layout.item_course) {
    val tvDuration = "00:00:00"

    override fun convert(holder: BaseDataBindingHolder<ItemCourseBinding>, item: CourseBean) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@CourseListAdapter
            executePendingBindings()

            when (item.status) {
                "APPLICABLE" -> {
                    tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_70)
                    tvStatus.text = "申请课程"
                }
                "UN_APPLICABLE" -> {
                    tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_apha20_70)
                    tvStatus.text = "不可申请"
                }
                "UNDER_APPLICATION" -> {
                    tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_apha70_70)
                    tvStatus.text = "申请中"
                }
                "ACCESSIBLE" -> {
                    tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_70)
                    tvStatus.text = "可进入"
                }
                "COMPLETED" -> {
                    tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_70)
                    tvStatus.text = "已完成"
                }
                else -> {
                    tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_apha20_70)
                    tvStatus.text = "不可申请"
                }
            }
        }
    }

    val onItemClickCommand: BindingCommand<Any?> = BindingCommand(BindingConsumer {
        if (it is CourseBean) {
            if (it.status == "APPLICABLE") {
                mFragment.viewModel.applyCourse(it.id)
            }
        }
    })


    val diffConfig = object : DiffUtil.ItemCallback<CourseBean>() {
        override fun areItemsTheSame(
            oldItem: CourseBean,
            newItem: CourseBean
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CourseBean,
            newItem: CourseBean
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.status == newItem.status
        }
    }
}