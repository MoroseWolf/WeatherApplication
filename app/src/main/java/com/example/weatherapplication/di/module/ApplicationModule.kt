package com.example.weatherapplication.di.module

import android.app.Application
import com.example.weatherapplication.ApplicationClass
import com.example.weatherapplication.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private var appClass: ApplicationClass) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return appClass
    }

}