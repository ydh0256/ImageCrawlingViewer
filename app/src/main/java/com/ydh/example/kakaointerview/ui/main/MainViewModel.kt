package com.ydh.example.kakaointerview.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ydh.example.kakaointerview.data.ImageData
import com.ydh.example.kakaointerview.data.source.ImageDataSource
import com.ydh.example.kakaointerview.data.source.ImageResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    val imageDataSource: ImageDataSource
): ViewModel() {
    private val _imageItems = MutableLiveData<List<ImageData>>().apply { value = emptyList() }
    val imageItems: LiveData<List<ImageData>>
        get() = _imageItems
    private val _isShowProgress = MutableLiveData<Boolean>().apply { value = false }
    val isShowProgress: LiveData<Boolean>
        get() = _isShowProgress
    private val _errorMessage = MutableLiveData<String>().apply { value = "" }
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val empty: LiveData<Boolean> = Transformations.map(_imageItems) {
        it.isEmpty()
    }

    val compositeDisposable = CompositeDisposable()


    fun loadImage(): Observable<ImageResponse> {
        val request =
            imageDataSource.getImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showProgress()
                }
                .doOnTerminate {
                    hideProgress()
                }
        compositeDisposable.add(
            request
                .flatMap {
                    Observable.just(it.items)
                }
                .subscribe({ items ->
                    _imageItems.value = items
                }) {
                    showError(it.message)
                }
        )
        return request
    }

    fun showProgress() {
        _isShowProgress.value = true
    }
    fun hideProgress() {
        _isShowProgress.value = false
    }
    fun showError(errMessage: String?) {
        _errorMessage.value = errMessage
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}