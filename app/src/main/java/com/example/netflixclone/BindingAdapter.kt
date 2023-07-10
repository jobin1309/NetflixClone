package com.example.netflixclone

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingAdapter {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        fun loadImage(view: ImageView, url: String) {

            Glide.with(view).load(url).into(view)
        }
    }
}