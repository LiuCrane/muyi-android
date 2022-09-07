package com.muyi.main.classes.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.StudentBean
import com.muyi.main.R
import com.muyi.main.classes.ui.SignInFragment
import com.muyi.main.databinding.ItemSignInBinding

/**
 * Created by hq on 2022/8/12.
 **/
class SignInAdapter(val mFragment: SignInFragment) :
    BaseQuickAdapter<StudentBean, BaseDataBindingHolder<ItemSignInBinding>>(R.layout.item_sign_in) {

    override fun convert(holder: BaseDataBindingHolder<ItemSignInBinding>, item: StudentBean) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@SignInAdapter
            executePendingBindings()

            if (item.signIn == "true") {
                tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_apha20_70)
                tvStatus.text = "已签到"
            } else {
                tvStatus.setBackgroundResource(com.czl.lib_base.R.drawable.bg_blue_70)
                tvStatus.text = "去签到"
            }
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
            return oldItem.current_course == newItem.current_course
        }
    }
}