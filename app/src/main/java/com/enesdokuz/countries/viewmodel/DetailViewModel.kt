package com.enesdokuz.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesdokuz.countries.model.Country

/***
 * Countries
 * enesdokuz enesdokuz 12.04.2020
 ***/
class DetailViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country = Country("Turkey", "Asia", "Ankara", "TRY", "Turkish", "www.enesdokuz.com")
        countryLiveData.value = country
    }
}