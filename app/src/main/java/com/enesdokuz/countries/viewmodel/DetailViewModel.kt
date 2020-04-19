package com.enesdokuz.countries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesdokuz.countries.model.Country
import com.enesdokuz.countries.service.CountryDatabase
import kotlinx.coroutines.launch

/***
 * Countries
 * enesdokuz enesdokuz 12.04.2020
 ***/
class DetailViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromDatabase(uuid : Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}