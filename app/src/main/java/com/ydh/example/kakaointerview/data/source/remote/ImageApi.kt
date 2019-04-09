package com.ydh.example.kakaointerview.data.source.remote

import com.ydh.example.kakaointerview.data.source.ImageResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ImageApi {
    @GET("bins/142l04")
    fun getImage(): Observable<ImageResponse>
}