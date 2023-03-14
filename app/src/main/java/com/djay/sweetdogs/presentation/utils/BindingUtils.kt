package com.djay.sweetdogs.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Utility for binding views with data
 */
class BindingUtils {

    companion object {

        /**
         * load image from url into ImageView
         */
        @JvmStatic
        @BindingAdapter("image")
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

}