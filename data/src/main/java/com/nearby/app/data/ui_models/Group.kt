package com.nearby.app.data.ui_models

data class Group(
    val items: MutableList<Item> = mutableListOf(),
    val name: String,
    val type: String
)