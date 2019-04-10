package com.ydh.example.kakaointerview.di

import com.ydh.example.kakaointerview.data.source.ImageDataSource
import com.ydh.example.kakaointerview.data.source.local.ImageLocalRepository
import com.ydh.example.kakaointerview.data.source.remote.ImageApi
import com.ydh.example.kakaointerview.data.source.remote.ImageRetrofitRepository
import com.ydh.example.kakaointerview.data.source.remote.ImageSiteCrawlingRepository
import com.ydh.example.kakaointerview.ui.main.ImageAdapter
import com.ydh.example.kakaointerview.ui.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Koin modules for Data Inject
 */
val apiRepositoryModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.myjson.com/")
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApi::class.java)
    }
    single<ImageDataSource> { ImageRetrofitRepository(get()) }
}

val localRepositoryModule = module {
    single<ImageDataSource> { ImageLocalRepository() }
}

val crawlingRepositoryModule = module {
    single<ImageDataSource> { ImageSiteCrawlingRepository() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val adapterModule = module {
    factory { ImageAdapter() }
}

// 로컬
//val appModules = listOf(localRepositoryModule, viewModelModule, adapterModule)
// 크롤링
val appModules = listOf(crawlingRepositoryModule, viewModelModule, adapterModule)
// Http
//val appModules = listOf(apiRepositoryModule, viewModelModule, adapterModule)

private fun provideOkHttpClient(
    interceptor: HttpLoggingInterceptor
): OkHttpClient
        = OkHttpClient.Builder()
    .run {
        addInterceptor(interceptor)
        build()
    }

private fun provideLoggingInterceptor(): HttpLoggingInterceptor
        = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }