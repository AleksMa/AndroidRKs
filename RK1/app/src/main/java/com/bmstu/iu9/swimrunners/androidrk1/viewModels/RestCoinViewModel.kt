package com.bmstu.iu9.swimrunners.androidrk1.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.bmstu.iu9.swimrunners.androidrk1.R
import com.bmstu.iu9.swimrunners.androidrk1.models.DayTrades
import com.bmstu.iu9.swimrunners.androidrk1.services.RestCoinApi
import kotlinx.coroutines.launch

class RestCoinViewModel(app: Application) : AndroidViewModel(app) {
    private val _timeseries = MutableLiveData<List<DayTrades>>()
    val timeseries: LiveData<List<DayTrades>>
        get() = _timeseries

    private val _currency = MutableLiveData<String>()
    val currency: LiveData<String>
        get() = _currency

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadTimeseries(from: String) {
        val app = getApplication<Application>()
        val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())

        val defaultCurrencyValue = app.getString(R.string.currency_usd)
        val defaultDaysValue = app.getString(R.string.days_preference_default_value)

        val currency = prefs.getString(
            app.getString(R.string.currency_preference_key), defaultCurrencyValue
        )!!
        val daysCount = prefs.getString(
            app.getString(R.string.days_preference_key), defaultDaysValue
        )!!

        viewModelScope.launch {
            try {
                val rawTimeseries = RestCoinApi.service.getTimeseries(from, currency, daysCount)
                _currency.postValue(currency)
                _timeseries.postValue(rawTimeseries.map { rawDayTrades ->
                    DayTrades(
                        date = mapDate(rawDayTrades.day),
                        priceOpen = String.format("%.3f", rawDayTrades.priceOpen),
                        priceHigh = String.format("%.3f", rawDayTrades.priceHigh),
                        priceLow = String.format("%.3f", rawDayTrades.priceLow),
                        priceClose = String.format("%.3f", rawDayTrades.priceClose),
                        volumeTraded = String.format("%.3f", rawDayTrades.volumeTraded),
                        tradesCount = rawDayTrades.tradesCount
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
