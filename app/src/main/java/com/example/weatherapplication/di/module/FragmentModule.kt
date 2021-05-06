package com.example.weatherapplication.di.module

import com.example.weatherapplication.api.WeatherService
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysContract
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideFiveDaysPresenter(): FiveDaysContract.Presenter {
        return FiveDaysPresenter()
    }

    @Provides
    fun provideWeatherService(): WeatherService {
        return WeatherService.create()
    }
}