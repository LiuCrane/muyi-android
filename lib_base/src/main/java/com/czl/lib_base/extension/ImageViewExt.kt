package com.czl.lib_base.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.ResourceUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.czl.lib_base.R
import com.makeramen.roundedimageview.RoundedImageView
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import net.lucode.hackware.magicindicator.buildins.UIUtil


/**
 * @author Alwyn
 * @Date 2020/10/30
 * @Description
 */
fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).apply(
        RequestOptions().diskCacheStrategy(
            DiskCacheStrategy.RESOURCE
        ).skipMemoryCache(true).dontAnimate().placeholder(R.drawable.ic_placeholder)
    ).thumbnail(0.6f)
        .into(this)
}

fun ImageView.loadUrl(
    url: String?,
    placeHolderRes: Drawable? = ResourceUtils.getDrawable(R.drawable.ic_placeholder),
    errorHolder: Drawable? = ResourceUtils.getDrawable(R.drawable.ic_placeholder)
) {
    if (url.isNullOrBlank() && placeHolderRes != null) {
        setImageDrawable(placeHolderRes)
    } else if (placeHolderRes == null) {
        Glide.with(this)
            .load(url)
            .into(this)
    } else {
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions().placeholder(placeHolderRes).error(errorHolder)
            )
            .into(this)
    }


}

fun ImageView.loadImageRes(
    @DrawableRes imgRes: Int,
    placeHolderRes: Drawable? = ResourceUtils.getDrawable(R.drawable.ic_placeholder),
    errorHolder: Drawable? = ResourceUtils.getDrawable(R.drawable.ic_placeholder)
) {
    Glide.with(this)
        .load(imgRes)
        .apply(
            RequestOptions().placeholder(placeHolderRes).error(errorHolder)
        )
        .into(this)
}

fun ImageView.loadBlurImageRes(@DrawableRes imgRes: Int, radius: Int = 25, scale: Int = 1) {
    Glide.with(this).load(imgRes).dontAnimate()
        .apply(
            RequestOptions.bitmapTransform(BlurTransformation(radius, scale))
                .placeholder(R.drawable.ic_placeholder)
        ).into(this)
}

fun ImageView.loadBlurImage(url: String?) {
    Glide.with(this).load(url).dontAnimate()
        .apply(RequestOptions.bitmapTransform(BlurTransformation())).into(this)
}

fun ImageView.loadImage(uri: String?, requestListener: RequestListener<Drawable?>) {
    Glide.with(this).load(uri).listener(requestListener).into(this)
}

fun ImageView.loadImage(uri: String?, width: Int, height: Int) {
    val multi = MultiTransformation(CropTransformation(width, height))
    Glide.with(this).load(uri).apply(RequestOptions.bitmapTransform(multi).dontAnimate()).into(this)
}

fun ImageView.loadGif(
    uri: String?,
    requestListener: RequestListener<GifDrawable?>? = null,
    centerCrop: Boolean? = null,
    @DrawableRes holder: Int? = null
) {
    var requestOptions = RequestOptions().dontTransform()
    if (centerCrop != null) {
        requestOptions = requestOptions.centerCrop()
    }
    if (holder != null) {
        requestOptions = requestOptions.placeholder(holder)
    }
    if (requestListener != null) {
        Glide.with(this).asGif().load(uri).apply(requestOptions).listener(requestListener)
            .into(this)
    } else {
        Glide.with(this).asGif().load(uri).apply(requestOptions).into(this)
    }
}

fun ImageView.loadCircleImage(url: String?, @DrawableRes holder: Int? = null) {
    if (url.isNullOrBlank() && holder != null) {
        setImageResource(holder)
        Glide.with(this).load(holder).apply(RequestOptions().circleCrop())
            .into(this)
    } else if (holder == null) {
        Glide.with(this).load(url).apply(RequestOptions().circleCrop()).into(this)
    } else {
        Glide.with(this).load(url).apply(RequestOptions().placeholder(holder).circleCrop())
            .into(this)
    }
}

fun ImageView.loadCircleImage(
    url: String?, holder: Drawable? = ResourceUtils.getDrawable(R.drawable.ic_placeholder)
) {
    if (url.isNullOrBlank() && holder != null) {
        setImageDrawable(holder)
    } else if (holder == null) {
        Glide.with(this).load(url).apply(RequestOptions().circleCrop()).into(this)
    } else {
        Glide.with(this).asBitmap().load(url)
            .apply(RequestOptions().placeholder(holder).circleCrop())
            .into(this)
    }
}

fun ImageView.loadCircleImageRes(resId: Int, @DrawableRes holder: Int? = null) {
    if (resId == 0) {
        if (holder != null) {
            setImageResource(holder)
        }
    } else if (holder == null) {
        Glide.with(this).load(resId).apply(RequestOptions().circleCrop()).into(this)
    } else {
        Glide.with(this).load(resId).placeholder(holder).apply(RequestOptions().circleCrop())
            .into(this)
    }
}


fun ImageView.loadRoundImage(
    url: String?,
    radius: Double,
    holder: Drawable? = ResourceUtils.getDrawable(R.drawable.ic_placeholder)
) {
    if (url.isNullOrBlank() && holder != null) {
        setImageDrawable(holder)
    } else if (holder == null) {
        Glide.with(this).load(url)
            .apply(
                RequestOptions().transform(
                    CenterCrop(),
                    RoundedCornersTransformation(UIUtil.dip2px(context, radius), 0)
                )
            )
            .into(this)
    } else {

        Glide.with(this).load(url).placeholder(holder)
            .apply(
                RequestOptions().transform(
                    CenterCrop(),
                    RoundedCornersTransformation(UIUtil.dip2px(context, radius), 0)
                )
            )
            .into(this)
    }
}
