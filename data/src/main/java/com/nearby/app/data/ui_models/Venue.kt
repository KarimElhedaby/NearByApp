package com.nearby.app.data.ui_models

data class Venue(
    val allowMenuUrlEdit: Boolean,
    val canonicalPath: String,
    val canonicalUrl: String,
    val hasMenu: Boolean,
    val id: String,
    val location: Location,
    val name: String,
    val popularityByGeo: Double,
    val rating: Double,
    val ratingColor: String,
    val storeId: String,
    val verified: Boolean
)