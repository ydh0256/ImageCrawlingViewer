package com.ydh.example.kakaointerview.data.source.remote

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ImageSiteCrawlingRepositoryTest {

    lateinit var imageSiteCrawlingRepository: ImageSiteCrawlingRepository

    @Before
    fun setUp() {
        imageSiteCrawlingRepository = ImageSiteCrawlingRepository()
    }

    @Test
    fun getImage() {
        // when
        val response = imageSiteCrawlingRepository.getImage().blockingFirst()
        // then
        assertNotEquals(response.items.size, 0)
        response.items.forEach {
            val isEmpty = it.imageName.isEmpty() || it.imageUrl.isEmpty()
            assertFalse(isEmpty)
        }
    }
}