package com.example.weatherapplication

import android.app.Application
import com.example.weatherapplication.di.component.ApplicationComponent
import com.example.weatherapplication.di.component.DaggerApplicationComponent
import com.example.weatherapplication.di.module.ApplicationModule


class ApplicationClass: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: ApplicationClass
        private set
    }
}