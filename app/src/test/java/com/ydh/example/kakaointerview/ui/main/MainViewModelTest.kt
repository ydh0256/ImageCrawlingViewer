package com.ydh.example.kakaointerview.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ydh.example.kakaointerview.LiveDataTestUtil
import com.ydh.example.kakaointerview.RxSchedulerRule
import com.ydh.example.kakaointerview.data.ImageData
import com.ydh.example.kakaointerview.data.source.ImageDataSource
import com.ydh.example.kakaointerview.data.source.ImageResponse
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 * Testing for [MainViewModel]
 */
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    @Mock private lateinit var imageDataSource: ImageDataSource
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(imageDataSource)
    }

    @Test
    fun `displays an error message if error is returned`() {
        // When 에러 발생시
        `when`(imageDataSource.getImage()).thenReturn(
            Observable.error(Exception("Error Test"))
        )
        mainViewModel.loadImage()

        // Then 에러 메시지 표시
        assertEquals(LiveDataTestUtil.getValue(mainViewModel.errorMessage), "Error Test")
    }

    @Test
    fun `displays an empty message if an empty list is returned`() {
        val response = mock(ImageResponse::class.java)
        // When 이미지를 표시할것이 없으면
        `when`(imageDataSource.getImage()).thenReturn(
            Observable.just(response)
        )
        mainViewModel.loadImage()

        // Then empty == true
        assertTrue(LiveDataTestUtil.getValue(mainViewModel.empty))
    }

    @Test
    fun `don't Displays an empty message if an non-empty list is returned`() {
        val items: MutableList<ImageData> = mutableListOf()
        items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3360485-1024x802.jpg", "Astaires And Hanson"))
        items.add(ImageData("https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3347677-1024x732.jpg", "Midnight Follies"))
        val response = ImageResponse(items)
        // When 이미지를 표시할것이 있으면
        `when`(imageDataSource.getImage()).thenReturn(
            Observable.just(response)
        )
        mainViewModel.loadImage()

        // Then empty == false
        assertFalse(LiveDataTestUtil.getValue(mainViewModel.empty))
    }

    @Test
    fun `displays progress bar while load data`() {
        val delayer = PublishSubject.create<Boolean>()
        val response = mock(ImageResponse::class.java)
        // When 로딩 중일때
        `when`(imageDataSource.getImage()).thenReturn(
            Observable.just(response).delaySubscription(delayer)
        )
        mainViewModel.loadImage().test()
        // 프로그래스바가 표시된다
        assertTrue(LiveDataTestUtil.getValue(mainViewModel.isShowProgress))
        // 로딩이 끝나면
        delayer.onComplete()
        // 프로그래스바가 표시되지 않는다.
        assertFalse(LiveDataTestUtil.getValue(mainViewModel.isShowProgress))
    }
}

