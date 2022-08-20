package com.muyi.main.my.adapter

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.ClassesBean
import com.czl.lib_base.data.bean.MediaBean
import com.muyi.main.R
import com.muyi.main.databinding.ItemChooseClassBinding
import com.muyi.main.databinding.ItemLearnAudioBinding
import com.muyi.main.databinding.ItemLearnClassBinding
import com.muyi.main.learn.ui.AudioFragment
import com.muyi.main.my.ui.ChooseClassFragment

/**
 * Created by hq on 2022/8/12.
 **/
class ChooseClassAdapter(private val mFragment: ChooseClassFragment) :
    BaseQuickAdapter<ClassesBean, BaseDataBindingHolder<ItemChooseClassBinding>>(R.layout.item_choose_class) {

    override fun convert(holder: BaseDataBindingHolder<ItemChooseClassBinding>, item: ClassesBean) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@ChooseClassAdapter
            executePendingBindings()
        }
    }

    val onItemClickCommand: BindingCommand<Any?> = BindingCommand(BindingConsumer {
        if (it is ClassesBean) {
            val intent = Intent()
            intent.putExtra("classId",it.id)
            intent.putExtra("className",it.name)
            mFragment.activity?.setResult(10001,intent)
            mFragment.back()
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