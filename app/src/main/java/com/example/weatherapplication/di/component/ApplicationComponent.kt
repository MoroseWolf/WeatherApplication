package com.example.weatherapplication.di.component

import com.example.weatherapplication.ApplicationClass
import com.example.weatherapplication.di.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: ApplicationClass)

}