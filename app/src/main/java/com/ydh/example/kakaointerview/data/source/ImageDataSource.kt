package com.ydh.example.kakaointerview.data.source

import com.ydh.example.kakaointerview.data.ImageData
import io.reactivex.Observable

/**
 * Main entry point for accessing image data.
 */
interface ImageDataSource {
    fun getImage(): Observable<ImageResponse>
}