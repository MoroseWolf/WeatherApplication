package com.example.weatherapplication.ui.screens.fiveDays

import android.content.Context
import android.location.Location
import com.example.weatherapplication.model.fiveDays.FiveDaysObject
import com.example.weatherapplication.ui.base.BaseContract

class FiveDaysContract {

    interface View: BaseContract.View {
        fun loadWeatherList(weather: FiveDaysObject)
        fun showError(error: String)
        fun showLoading(show: Boolean)
        fun hideLoading()
    }

    interface Presenter: BaseContract.Presenter<FiveDaysContract.View> {
        fun getGeolocationInfo(applicationContext: Context)
        fun loadWeather(currentLocation: Location)
    }
}