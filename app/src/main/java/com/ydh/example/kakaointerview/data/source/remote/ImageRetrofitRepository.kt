package com.ydh.example.kakaointerview.data.source.remote

import com.ydh.example.kakaointerview.data.source.ImageDataSource
import com.ydh.example.kakaointerview.data.source.ImageResponse
import io.reactivex.Observable

class ImageRetrofitRepository(
    val imageApi: ImageApi
):ImageDataSource {
    override fun getImage(): Observable<ImageResponse> {
        return imageApi.getImage()
    }
}