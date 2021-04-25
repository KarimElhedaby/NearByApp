package com.example.nearbyapp.ui

import com.nearby.app.base.MvpPresenter
import com.nearby.app.base.MvpView
import com.nearby.app.data.ui_models.Item

interface NearByPlacesContract {

    interface Presenter : MvpPresenter<View> {
        fun getNearByPlaces(lat: Double, long: Double)

    }

    interface View : MvpView {
        fun onGetNearByPlacesSuccess(places: MutableList<Item>)
        fun onGetNearByPlacesFail()
    }

}