package com.example.weatherapplication.ui.screens.fiveDays

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.weatherapplication.R
import com.example.weatherapplication.api.WeatherService
import com.example.weatherapplication.model.fiveDays.FiveDaysObject
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
//import com.example.weatherapplication.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class FiveDaysPresenter: FiveDaysContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: WeatherService = WeatherService.create()
    private lateinit var view: FiveDaysContract.View

    override fun loadWeather(currentLocation: Location) {
        var subscription = api
            .getFiveDaysWeather(currentLocation.latitude, currentLocation.longitude)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { weather ->
                    view.loadWeatherList(weather)
                    view.showLoading(false)
                },
                { error ->
                    view.showLoading(false)
                    view.showError(error.toString())
                }
            )

        subscriptions.add(subscription)
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
       subscriptions.clear()
    }

    override fun attach(view: FiveDaysContract.View)  {
        this.view = view
    }

    override fun getGeolocationInfo(applicationContext: Context) {
        var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        else {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener {
                        location: Location? ->
                    if (location != null) {
                        loadWeather(location)
                    }
                }
        }
    }

}