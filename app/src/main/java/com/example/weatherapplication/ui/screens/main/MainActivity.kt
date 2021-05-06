package com.example.weatherapplication.ui.screens.main

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.di.component.DaggerActivityComponent
import com.example.weatherapplication.di.module.ActivityModule
import com.example.weatherapplication.ui.screens.fiveDays.FiveDaysFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity: AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var  presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_currentDay,
            R.id.navigation_fiveDays
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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