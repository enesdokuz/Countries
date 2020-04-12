package com.enesdokuz.countries.model

import com.google.gson.annotations.SerializedName

/***
 * Countries
 * enesdokuz enesdokuz 12.04.2020
 ***/
data class Country(
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("flag")
    val imageUrl: String?
)