package com.ydh.example.kakaointerview.data.source.local

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ImageLocalRepositoryTest {

    lateinit var imageLocalRepository: ImageLocalRepository

    @Before
    fun setUp() {
        imageLocalRepository = ImageLocalRepository()
    }

    @Test
    fun getImage() {
        //when
        val response = imageLocalRepository.getImage().blockingFirst()
        //then
        assertNotEquals(response.items.size, 0)
    }
}