package com.bmstu.iu9.swimrunners.androidrk1.services

import com.bmstu.iu9.swimrunners.androidrk1.BuildConfig
import com.bmstu.iu9.swimrunners.androidrk1.models.RawDayTrades
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://rest.coinapi.io/v1/ohlcv/"

private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface RestCoinService {
    @GET("{from}/{to}/latest?period_id=1DAY&include_empty_items=false&apikey=${BuildConfig.API_KEY}")
    suspend fun getTimeseries(
        @Path("from") from: String,
        @Path("to") to: String,
        @Query("limit") days: String
    ): List<RawDayTrades>
}

object RestCoinApi {
    val service: RestCoinService by lazy {
        retrofit.create(RestCoinService::class.java)
    }
}
