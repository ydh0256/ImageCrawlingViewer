package com.ydh.example.kakaointerview.ui.main

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ydh.example.kakaointerview.data.ImageData
import android.view.LayoutInflater
import com.ydh.example.kakaointerview.databinding.ImageItemGridBinding
import com.ydh.example.kakaointerview.databinding.ImageItemListBinding


class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    enum class ViewMode{
        GRID, LIST
    }

    private var images: MutableList<ImageData> = mutableListOf()
    var viewMode = ViewMode.LIST

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding =
            when(viewMode) {
                ViewMode.GRID -> ImageItemGridBinding.inflate(LayoutInflater.from(parent.context))
                ViewMode.LIST -> ImageItemListBinding.inflate(LayoutInflater.from(parent.context))
            }
        return ImageViewHolder(itemBinding)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun setImages(images: List<ImageData>) {
        this.images.clear()
        this.images.addAll(images)
        this.notifyDataSetChanged()
    }

    class ImageViewHolder(val bindView: ViewDataBinding): RecyclerView.ViewHolder(bindView.root){
        fun bind(imageData: ImageData) {
            if(bindView is ImageItemGridBinding) {
                bindView.imageData = imageData
            } else if(bindView is ImageItemListBinding) {
                bindView.imageData = imageData
            }
        }
    }
}