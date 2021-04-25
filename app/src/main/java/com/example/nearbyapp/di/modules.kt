package com.example.nearbyapp.di

import com.example.nearbyapp.ui.NearByPlacesPresenter
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel<NearByPlacesPresenter>()
}