package com.ydh.example.kakaointerview.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ydh.example.kakaointerview.data.ImageData
import com.ydh.example.kakaointerview.ui.main.ImageAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import java.util.*


object CustomDataBind {
    @BindingAdapter("app:imageItems")
    @JvmStatic fun setImageItems(listView: RecyclerView, items: List<ImageData>) {
        with(listView.adapter as ImageAdapter) {
            setImages(items)
        }
    }


    @BindingAdapter(value = ["app:imageUrl", "app:isThumbnail"], requireAll = false)
    @JvmStatic fun loadImage(imageView: ImageView, imageUrl: String, isThumbnail: Boolean = false) {
        val glide = Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions().apply(){
                val rnd = Random()
                val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                placeholder(ColorDrawable(color))
            })
        if(isThumbnail) glide.thumbnail(0.1f)
        glide.into(imageView)

    }
}