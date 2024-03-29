package com.sukumar.tetrasoft.module.mostPopular.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukumar.tetrasoft.base.ApiBaseConfig.Companion.KEY
import com.sukumar.tetrasoft.base.ApiConfig
import com.sukumar.tetrasoft.base.KotlinEvent
import com.sukumar.tetrasoft.module.common.AppConstants.Companion.ERROR_LOADING
import com.sukumar.tetrasoft.module.mostPopular.dto.PopularDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection

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
                if(popularResponse.code()== HttpURLConnection.HTTP_OK) {
                    mostPopularLiveData.value = popularResponse.body()
                }else{
                    eventLiveData.value= KotlinEvent.Companion.FailedEvent(ERROR_LOADING)
                }
            }, {
                eventLiveData.value = KotlinEvent.Companion.CompletedEvent
                eventLiveData.value= KotlinEvent.Companion.FailedEvent(ERROR_LOADING)
            }).addTo(compositeDisposable)
    }

}