package com.bmstu.iu9.swimrunners.androidrk1.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmstu.iu9.swimrunners.androidrk1.models.DayTrades
import com.bmstu.iu9.swimrunners.androidrk1.services.RestCoinApi
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.roundToInt

class RestCoinViewModel : ViewModel() {
    private val _timeseries = MutableLiveData<List<DayTrades>>()
    val timeseries: LiveData<List<DayTrades>>
        get() = _timeseries

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadTimeseries(from: String) {
        viewModelScope.launch {
            try {
                val rawTimeseries = RestCoinApi.service.getTimeseries(from)
                _timeseries.postValue(rawTimeseries.map { rawDayTrades ->
                    DayTrades(
                        date = mapDate(rawDayTrades.day),
                        priceOpen = rawDayTrades.priceOpen.roundToInt(),
                        priceHigh = rawDayTrades.priceHigh.roundToInt(),
                        priceLow = rawDayTrades.priceLow.roundToInt(),
                        priceClose = rawDayTrades.priceClose.roundToInt(),
                        volumeTraded = rawDayTrades.volumeTraded.roundToInt(),
                        tradesCount = rawDayTrades.tradesCount,
                    )
                })
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }
        }
    }

    private fun mapDate(rawDate: String): String {
        return rawDate
            .split("T")
            .first()
            .split("-")
            .reversed()
            .slice(0..1)
            .joinToString(".")
    }
}
