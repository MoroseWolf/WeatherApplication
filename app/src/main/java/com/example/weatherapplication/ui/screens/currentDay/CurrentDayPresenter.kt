package com.example.weatherapplication.ui.screens.currentDay

import com.example.weatherapplication.api.WeatherService
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CurrentDayPresenter : CurrentDayContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: WeatherService = WeatherService.create()
    private lateinit var view: CurrentDayContract.View

    override fun loadWeather() {
        var subscription = api
            .getCurrentWeather()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { weather ->
                    view.loadCurrentWeather(weather)
                },
                { error ->
                    view.showError(error.toString())
                }
            )

        subscriptions.add(subscription)
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: CurrentDayContract.View) {
        this.view = view
    }

}
