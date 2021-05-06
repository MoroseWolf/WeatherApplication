package com.example.weatherapplication.model.fiveDays

data class FiveDaysObject(
    val cod: Int,
    val message: Int,
    val cnt: Int,
    val list: List<TimeIntervalInfo>,
    val city: City
)