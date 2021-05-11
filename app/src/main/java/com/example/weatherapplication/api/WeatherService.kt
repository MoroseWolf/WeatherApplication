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
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?units=metric$API_KEY")
    fun getCurrentWeather(@Query(value = "lat") latitude: Double,
                          @Query(value = "lon") longitude: Double
    ): Single<CurrentDayObject>

    @GET("data/2.5/forecast?units=metric$API_KEY")
    fun getFiveDaysWeather(@Query(value = "lat") latitude: Double,
                           @Query(value = "lon") longitude: Double)
            : Observable<FiveDaysObject>


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