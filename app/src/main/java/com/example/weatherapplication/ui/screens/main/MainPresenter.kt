package com.example.weatherapplication.ui.screens.main

import io.reactivex.disposables.CompositeDisposable

class MainPresenter: MainContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: MainContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun openFiveDays(){
        view.showFiveDaysFragment()
    }

    override fun openCurrentDay() {
        view.showCurrentDayFragment()
    }

}