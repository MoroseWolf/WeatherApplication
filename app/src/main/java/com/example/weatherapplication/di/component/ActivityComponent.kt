package com.example.weatherapplication.di.component

import com.example.weatherapplication.di.module.ActivityModule
import com.example.weatherapplication.ui.screens.main.MainActivity
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}