package com.muyi.main.learn.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.MediaBean
import com.muyi.main.R
import com.muyi.main.databinding.ItemLearnAudioBinding
import com.muyi.main.detail.ui.DetailActivity
import com.muyi.main.learn.ui.AudioFragment

/**
 * Created by hq on 2022/8/12.
 **/
class LearnAudioAdapter(val mFragment: AudioFragment) :
    BaseQuickAdapter<MediaBean, BaseDataBindingHolder<ItemLearnAudioBinding>>(R.layout.item_learn_audio) {
    val tvDuration = "00:00:00"

    override fun convert(holder: BaseDataBindingHolder<ItemLearnAudioBinding>, item: MediaBean) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@LearnAudioAdapter
            executePendingBindings()
        }
    }

    val onItemClickCommand: BindingCommand<Any?> = BindingCommand(BindingConsumer {
        if (it is MediaBean) {
            mFragment.startActivity(
                DetailActivity::class.java,
                Bundle().apply {
                    putString(
                        AppConstants.BundleKey.KEY_MEDIA_ID,
                        it.id
                    )
                    putString(
                        AppConstants.BundleKey.KEY_MEDIA_TYPE,
                        it.type
                    )
                })
        }
    })

    val diffConfig = object : DiffUtil.ItemCallback<MediaBean>() {
        override fun areItemsTheSame(
            oldItem: MediaBean,
            newItem: MediaBean
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MediaBean,
            newItem: MediaBean
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.url == newItem.url && oldItem.duration == newItem.duration
        }
    }
}