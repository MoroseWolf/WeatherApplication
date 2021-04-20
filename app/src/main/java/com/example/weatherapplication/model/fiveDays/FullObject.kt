package com.example.weatherapplication.model.fiveDays

import com.google.gson.annotations.SerializedName

data class FullObject(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Int,
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtTxt: String
)