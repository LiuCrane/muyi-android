package com.muyi.main.progress.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.data.bean.StudentBean
import com.muyi.main.R
import com.muyi.main.databinding.ItemStudentTrackBinding
import com.muyi.main.progress.ui.StudentTrackFragment

/**
 * Created by hq on 2022/8/12.
 **/
class StudentTrackAdapter(val mFragment: StudentTrackFragment) :
    BaseQuickAdapter<StudentBean, BaseDataBindingHolder<ItemStudentTrackBinding>>(R.layout.item_student_track) {

    override fun convert(holder: BaseDataBindingHolder<ItemStudentTrackBinding>, item: StudentBean) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@StudentTrackAdapter
            executePendingBindings()
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
            return oldItem.currentCourse == newItem.currentCourse && oldItem.stageStatus == newItem.stageStatus
        }
    }
}