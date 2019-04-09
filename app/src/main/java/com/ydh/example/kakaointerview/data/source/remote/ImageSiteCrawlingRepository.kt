package com.ydh.example.kakaointerview.data.source.remote

import com.google.gson.Gson
import com.ydh.example.kakaointerview.data.ImageData
import com.ydh.example.kakaointerview.data.source.ImageDataSource
import com.ydh.example.kakaointerview.data.source.ImageResponse
import io.reactivex.Observable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import timber.log.Timber

class ImageSiteCrawlingRepository: ImageDataSource {
    override fun getImage(): Observable<ImageResponse> {
        return Observable.fromCallable {
            val items: MutableList<ImageData> = mutableListOf()

            val doc = Jsoup.connect("https://www.gettyimagesgallery.com/collection/sasha/").get()
            val elements = doc.getElementsByAttributeValue("class", "grid-item image-item col-md-4")
            elements.forEach {
                Timber.d(it.toString())
                val img = it.getElementsByTag("img").first()
                val name = it.getElementsByTag("h5").first()
                items.add(ImageData(img.attr("data-src"), name.text()))
            }
            ImageResponse(items)
        }
    }
}