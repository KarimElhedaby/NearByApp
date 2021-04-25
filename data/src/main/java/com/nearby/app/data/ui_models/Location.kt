package com.nearby.app.data.ui_models

data class Location(
    val address: String,
    val cc: String,
    val city: String,
    val contextGeoId: Int,
    val contextLine: String,
    val country: String,
    val crossStreet: String,
    val distance: Int,
    val formattedAddress: MutableList<String>,
    val lat: Double,
    val lng: Double,
    val neighborhood: String,
    val postalCode: String,
    val state: String
)