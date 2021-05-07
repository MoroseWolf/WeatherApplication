package com.example.weatherapplication.di.module

import com.example.weatherapplication.api.WeatherService
import com.example.weatherapplication.ui.screens.currentDay.CurrentDayContract
import com.example.weatherapplication.ui.screens.currentDay.CurrentDayPresenter
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysContract
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideCurrentDayPresenter(): CurrentDayContract.Presenter {
        return CurrentDayPresenter()
    }

    @Provides
    fun provideFiveDaysPresenter(): FiveDaysContract.Presenter {
        return FiveDaysPresenter()
    }

    @Provides
    fun provideWeatherService(): WeatherService {
        return WeatherService.create()
    }
}