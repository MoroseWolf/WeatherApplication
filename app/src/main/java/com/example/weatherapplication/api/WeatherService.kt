package com.example.weatherapplication.api

import com.example.weatherapplication.model.currentDay.CurrentDayObject
import com.example.weatherapplication.model.fiveDays.FiveDaysObject
import com.example.weatherapplication.util.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherService {

    @GET("data/2.5/weather?q=Vitebsk&units=metric$API_KEY")
    fun getCurrentWeather(): Single<CurrentDayObject>

    @GET("data/2.5/forecast?q=Vitebsk&units=metric$API_KEY")
    fun getFiveDaysWeather(): Observable<FiveDaysObject>


    companion object {
        fun create(): WeatherService {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WeatherService::class.java)
        }
    }

}