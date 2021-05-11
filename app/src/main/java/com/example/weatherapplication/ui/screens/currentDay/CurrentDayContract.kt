package com.example.weatherapplication.ui.screens.currentDay

import android.content.Context
import android.location.Location
import com.example.weatherapplication.model.currentDay.CurrentDayObject
import com.example.weatherapplication.ui.base.BaseContract

class CurrentDayContract {

    interface View: BaseContract.View {
        fun loadCurrentWeather(weather: CurrentDayObject)
        fun showError(error: String)
        fun showLoading(show: Boolean)
    }

    interface Presenter: BaseContract.Presenter<CurrentDayContract.View> {
        fun getGeolocationInfo(applicationContext: Context)
        fun loadWeather(currentLocation: Location)
    }
}