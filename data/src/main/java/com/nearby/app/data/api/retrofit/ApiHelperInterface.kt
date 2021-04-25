package com.nearby.app.data.api.retrofit

import com.nearby.app.data.ui_models.PlacesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelperInterface {
    /**
     *  Put your retrofit calls here
     */

    @GET("venues/explore")
    fun getNearByPlaces(
        @Query("ll") latLang: String,
        @Query("radius") radius: Int = 1000,
        @Query("venuePhotos") venuePhotos: Int = 1

    ): Observable<PlacesResponse>

}