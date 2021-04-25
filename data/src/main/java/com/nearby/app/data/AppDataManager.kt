package com.nearby.app.data

import com.nearby.app.data.api.retrofit.ApiHelperInterface
import com.nearby.app.data.ui_models.PlacesResponse
import io.reactivex.Observable


class AppDataManager(var service: ApiHelperInterface) : DataManager {

    override fun getNearByPlaces(latLang: String, radius: Int , venuePhotos:Int ): Observable<PlacesResponse> {
        return service.getNearByPlaces(latLang, radius , venuePhotos )
    }
}