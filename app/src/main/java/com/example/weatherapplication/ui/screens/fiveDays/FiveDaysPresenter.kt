package com.example.weatherapplication.ui.screens.fiveDays

import com.example.weatherapplication.R
import com.example.weatherapplication.api.WeatherService
import com.example.weatherapplication.model.fiveDays.FiveDaysObject
//import com.example.weatherapplication.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class FiveDaysPresenter: FiveDaysContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: WeatherService = WeatherService.create()
    private lateinit var view: FiveDaysContract.View

    override fun loadWeather() {
        var subscription = api
            .getFiveDaysWeather()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate {view.showLoading(false)}
            .subscribe(
                { weather ->
                    view.loadWeatherList(weather)
                    view.showLoading(false)
                },
                { error ->
                    view.showLoading(false)
                    view.showError(error.toString())
                }
            )
            //.doOnTerminate {view.hideLoading()}
            /*.subscribe(
                {weatherlist: List<FiveDaysObject>? ->
                    view.showLoading(false)
                }, {error ->
                    view.showLoading(false)
                    view.showError(error.LocalizedMessage)
                })

             */

        subscriptions.add(subscription)
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
       subscriptions.clear()
    }

    override fun attach(view: FiveDaysContract.View)  {
        this.view = view
    }

}


/*
private var subscription: Disposable? = null

override fun onViewCreated() {
    loadWeather();
}

fun loadWeather() {
    view.showLoading()
    subscription = weatherService
        .getFiveDaysWeather()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .doOnTerminate {view.hideLoading()}
        .subscribe(
            { weatherList -> view.updateWeatherList(listOf(weatherList))},
            { view.showError(R.string.unknown_error)}
        )
}

override fun onViewDestroyed() {
    subscription?.dispose()
}
}

*/