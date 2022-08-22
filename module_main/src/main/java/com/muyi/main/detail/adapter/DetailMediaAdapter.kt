package com.muyi.main.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.data.bean.MediaBean
import com.muyi.main.R
import com.muyi.main.databinding.ItemDetailMediaBinding
import com.muyi.main.detail.ui.DetailActivity

/**
 * Created by hq on 2022/8/12.
 **/
class DetailMediaAdapter(val mFragment: DetailActivity) :
    BaseQuickAdapter<MediaBean, BaseDataBindingHolder<ItemDetailMediaBinding>>(R.layout.item_detail_media) {
    val tvDuration = "00:00:00"

    override fun convert(holder: BaseDataBindingHolder<ItemDetailMediaBinding>, item: MediaBean) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@DetailMediaAdapter
            executePendingBindings()
        }
    }

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