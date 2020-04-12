package com.enesdokuz.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesdokuz.countries.model.Country
import com.enesdokuz.countries.service.CountryAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/***
 * Countries
 * enesdokuz enesdokuz 12.04.2020
 ***/
class FeedViewModel : ViewModel() {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun getDummyData() {
        val country = Country("Turkey", "Asia", "Ankara", "TRY", "Turkish", "www.enesdokuz.com")
        val country2 = Country("Germany", "Europe", "Berlin", "EUR", "German", "www.enesdokuz.com")
        val country3 = Country("France", "Europe", "Paris", "EUR", "English", "www.enesdokuz.com")

        val countryList = arrayListOf(country, country2, country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        countries.value = t
                        countryError.value = false
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }
}