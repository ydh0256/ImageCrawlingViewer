package com.ydh.example.kakaointerview.data.source.local

import com.ydh.example.kakaointerview.data.ImageData
import com.ydh.example.kakaointerview.data.source.ImageDataSource
import com.ydh.example.kakaointerview.data.source.ImageResponse
import io.reactivex.Observable

class ImageLocalRepository: ImageDataSource {
    override fun getImage(): Observable<ImageResponse> {
        return Observable.fromCallable {
            val items: MutableList<ImageData> = mutableListOf()
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3360485-1024x802.jpg", "Astaires And Hanson"))
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3347677-1024x732.jpg", "Midnight Follies"))
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-2664892.jpg", "Sultry Cigarette"))
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3318099-769x1024.jpg", "Midnight Vanities"))
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3360485-1024x802.jpg", "Astaires And Hanson"))
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3347677-1024x732.jpg", "Midnight Follies"))
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-2664892.jpg", "Sultry Cigarette"))
            items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3318099-769x1024.jpg", "Midnight Vanities"))

            ImageResponse(items)
        }
    }
}