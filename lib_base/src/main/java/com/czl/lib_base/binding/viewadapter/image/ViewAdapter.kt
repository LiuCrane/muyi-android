package com.czl.lib_base.binding.viewadapter.image

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableInt
import com.czl.lib_base.extension.loadCircleImage
import com.czl.lib_base.extension.loadRoundImage
import com.czl.lib_base.extension.loadUrl

object ViewAdapter {
    @JvmStatic
    @BindingAdapter(value = ["url", "placeholderRes", "errorRes"], requireAll = false)
    fun setImageUrl(
        imageView: ImageView,
        url: String?,
        placeholderRes: Drawable? = null,
        errorRes: Drawable? = null
    ) {
        if (!TextUtils.isEmpty(url)) {
            imageView.loadUrl(url, placeholderRes, errorRes)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["radiusUrl", "radius", "placeholderRes"],
        requireAll = false
    )
    fun setImageUrRadius(
        imageView: ImageView,
        radiusUrl: String?,
        radius: Double,
        placeholderRes: Drawable? = null,
    ) {
        if (!TextUtils.isEmpty(radiusUrl)) {
            imageView.loadRoundImage(radiusUrl, radius, placeholderRes)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["circleUrl", "placeholderRes"],
        requireAll = false
    )
    fun setImageUrCircle(
        imageView: ImageView,
        circleUrl: String?,
        placeholderRes: Drawable? = null,
    ) {
        if (!TextUtils.isEmpty(circleUrl)) {
            imageView.loadCircleImage(circleUrl, placeholderRes)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["src"], requireAll = false)
    fun setImageRes(imageView: ImageView, resId: ObservableInt?) {
        if (resId == null) {
            return
        }
        if (resId.get() != 0) {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(resId.get())
        } else {
            imageView.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["imgRes", "imgBgRes"], requireAll = false)
    fun setImage(imageView: ImageView, resDrawable: Drawable?, bg: Drawable?) {
        if (resDrawable != null)
            imageView.setImageDrawable(resDrawable)
        if (bg != null) imageView.background = bg
    }
}