package com.example.weatherapplication.di.module

import android.app.Activity
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysContract
import com.example.weatherapplication.ui.screens.main.MainContract
import com.example.weatherapplication.ui.screens.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter {
        return MainPresenter()
    }
}