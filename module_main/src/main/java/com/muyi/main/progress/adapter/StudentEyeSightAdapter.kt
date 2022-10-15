package com.muyi.main.progress.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.data.bean.EyesightBean
import com.czl.lib_base.util.DateUtils
import com.muyi.main.R
import com.muyi.main.databinding.ItemStudentEyeSightBinding
import com.muyi.main.progress.ui.StudentDetailFragment

/**
 * Created by hq on 2022/8/12.
 **/
class StudentEyeSightAdapter(private val mFragment: StudentDetailFragment) :
    BaseQuickAdapter<EyesightBean, BaseDataBindingHolder<ItemStudentEyeSightBinding>>(R.layout.item_student_eye_sight) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemStudentEyeSightBinding>,
        item: EyesightBean
    ) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@StudentEyeSightAdapter
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

            tvVision.text = "双眼视力" + item.binocular_vision
            tvLeftVision.text = "L " + item.left_vision
            tvRightVision.text = "R " + item.right_vision
            item.created_at?.let {
                tvTime.text = DateUtils.millis2String(it, DateUtils.YYYY_MM_DD)
            }
        }
    }

    val diffConfig = object : DiffUtil.ItemCallback<EyesightBean>() {
        override fun areItemsTheSame(
            oldItem: EyesightBean,
            newItem: EyesightBean
        ): Boolean {
            return oldItem.created_at == newItem.created_at && oldItem.binocular_vision == newItem.binocular_vision
        }

        override fun areContentsTheSame(
            oldItem: EyesightBean,
            newItem: EyesightBean
        ): Boolean {
            return oldItem.created_at == newItem.created_at && oldItem.binocular_vision == newItem.binocular_vision
        }
    }
}