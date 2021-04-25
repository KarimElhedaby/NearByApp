package com.nearby.app.data.ui_models

data class Response(
    val groups: MutableList<Group>,
    val headerFullLocation: String,
    val headerLocation: String,
    val headerLocationGranularity: String,
    val totalResults: Int
)