package com.muyi.main.learn.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.data.bean.ClassesBean
import com.muyi.main.R
import com.muyi.main.databinding.ItemLearnClassBinding
import com.muyi.main.learn.ui.ClassFragment

/**
 * Created by hq on 2022/8/12.
 **/
class LearnClassAdapter(val mFragment: ClassFragment) :
    BaseQuickAdapter<ClassesBean, BaseDataBindingHolder<ItemLearnClassBinding>>(R.layout.item_learn_class) {
    val tvDuration = "00:00"

    override fun convert(holder: BaseDataBindingHolder<ItemLearnClassBinding>, item: ClassesBean) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@LearnClassAdapter
            executePendingBindings()
        }
    }

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