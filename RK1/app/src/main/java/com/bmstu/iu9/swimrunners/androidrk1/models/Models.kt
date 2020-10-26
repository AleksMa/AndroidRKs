package com.bmstu.iu9.swimrunners.androidrk1.models

import com.squareup.moshi.Json

data class RawDayTrades(
    @Json(name = "time_period_start") val day: String,
    @Json(name = "price_open") val priceOpen: Double,
    @Json(name = "price_high") val priceHigh: Double,
    @Json(name = "price_low") val priceLow: Double,
    @Json(name = "price_close") val priceClose: Double,
    @Json(name = "volume_traded") val volumeTraded: Double,
    @Json(name = "trades_count") val tradesCount: Int,
)

data class DayTrades(
    val date: String,
    val priceOpen: Int,
    val priceHigh: Int,
    val priceLow: Int,
    val priceClose: Int,
    val volumeTraded: Int,
    val tradesCount: Int,
)
