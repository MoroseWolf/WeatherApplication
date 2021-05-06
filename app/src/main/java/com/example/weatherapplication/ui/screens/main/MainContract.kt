package com.example.weatherapplication.ui.screens.main

import com.example.weatherapplication.ui.base.BaseContract

class MainContract {
    interface View: BaseContract.View {
        fun showCurrentDayFragment()
        fun showFiveDaysFragment()
    }

    interface Presenter: BaseContract.Presenter<MainContract.View> {
    }
}