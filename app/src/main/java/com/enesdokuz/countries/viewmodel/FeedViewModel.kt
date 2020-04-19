package com.enesdokuz.countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesdokuz.countries.model.Country
import com.enesdokuz.countries.service.CountryAPIService
import com.enesdokuz.countries.service.CountryDatabase
import com.enesdokuz.countries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/***
 * Countries
 * enesdokuz enesdokuz 12.04.2020
 ***/
class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshFrequency = 10 * 60 * 1000 * 1000 * 1000L

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
        val updatedTime = customPreferences.getTime()
        if (updatedTime != null
            && updatedTime != 0L
            && System.nanoTime() - updatedTime < refreshFrequency){
            getDataFromSQLite()
        }
        else
            getDataFromAPI()
    }

    fun refreshDataFromAPI(){
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


                        Toast.makeText(getApplication(),"from api",Toast.LENGTH_SHORT).show()
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList : List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list : List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = listLong[i].toInt()
                i++
            }

            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())
    }

    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"from sqllite",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}