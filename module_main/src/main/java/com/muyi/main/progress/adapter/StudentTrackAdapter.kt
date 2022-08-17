package com.muyi.main.progress.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.data.bean.StudentBean
import com.czl.lib_base.extension.loadImageRes
import com.czl.lib_base.extension.loadUrl
import com.muyi.main.R
import com.muyi.main.databinding.ItemStudentTrackBinding
import com.muyi.main.progress.ui.StudentTrackFragment

/**
 * Created by hq on 2022/8/12.
 **/
class StudentTrackAdapter(val mFragment: StudentTrackFragment) :
    BaseQuickAdapter<StudentBean, BaseDataBindingHolder<ItemStudentTrackBinding>>(R.layout.item_student_track) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemStudentTrackBinding>,
        item: StudentBean
    ) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@StudentTrackAdapter
            executePendingBindings()

            if (item.avatar_url.isNullOrEmpty()) {
                ivAvatar.loadImageRes(com.czl.lib_base.R.drawable.ic_placeholder)
            } else {
                ivAvatar.loadUrl(item.avatar_url)
            }

            if (item.improved == true) {
                tvImprove.setBackgroundResource(R.drawable.bg_sight_improved)
                tvImprove.text = "有提升"
            } else {
                tvImprove.setBackgroundResource(R.drawable.bg_sight_not_improved)
                tvImprove.text = "无提升"
            }

            if (item.name.isNullOrEmpty())
                tvName.text = "学员"
            else
                tvName.text = item.name

            tvLeftVision.text = "L " + item.left_vision
            tvRightVision.text = "R " + item.right_vision


        }
    }

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
            return oldItem.current_course == newItem.current_course
        }
    }
}