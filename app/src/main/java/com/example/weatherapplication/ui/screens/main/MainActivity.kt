package com.example.weatherapplication.ui.screens.main

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.di.component.DaggerActivityComponent
import com.example.weatherapplication.di.module.ActivityModule
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysFragment
import javax.inject.Inject

class MainActivity: AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var  presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()

        presenter.attach(this)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)
    }

    override fun showCurrentDayFragment() {
        //TODO("Not yet implemented")
    }

    override fun showFiveDaysFragment() {
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.frame, FiveDaysFragment().newInstance(), FiveDaysFragment.TAG)
            .commit()
    }
}