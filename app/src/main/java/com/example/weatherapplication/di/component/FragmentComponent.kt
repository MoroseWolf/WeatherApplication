package com.example.weatherapplication.di.component

import com.example.weatherapplication.di.module.FragmentModule
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysFragment
import dagger.Component

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fiveDaysFragment: FiveDaysFragment)
}