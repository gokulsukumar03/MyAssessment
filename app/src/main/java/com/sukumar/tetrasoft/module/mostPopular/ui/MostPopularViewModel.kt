package com.sukumar.tetrasoft.module.mostPopular.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukumar.tetrasoft.base.ApiBaseConfig.Companion.KEY
import com.sukumar.tetrasoft.base.ApiConfig
import com.sukumar.tetrasoft.base.KotlinEvent
import com.sukumar.tetrasoft.module.mostPopular.dto.PopularDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MostPopularViewModel : ViewModel() {

    var mostPopularLiveData = MutableLiveData<PopularDto>()
    private var compositeDisposable = CompositeDisposable()
    var eventLiveData = MutableLiveData<KotlinEvent>()

    fun disposeRequest() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun fetchMostPopular() {
        eventLiveData.value = KotlinEvent.Companion.LoadingEvent
        ApiConfig.getApiInstance().getPopularData(KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ popularResponse ->
                eventLiveData.value = KotlinEvent.Companion.CompletedEvent
                mostPopularLiveData.value=popularResponse
            }, { error ->
                eventLiveData.value = KotlinEvent.Companion.CompletedEvent
                eventLiveData.value= KotlinEvent.Companion.FailedEvent(error.message?:"Error Occurred...")
            }).addTo(compositeDisposable)
    }

}