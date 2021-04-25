package com.example.nearbyapp.ui

import com.nearby.app.base.BasePresenter
import com.nearby.app.data.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class NearByPlacesPresenter(private val dataManager: DataManager) :
    BasePresenter<NearByPlacesContract.View>(), NearByPlacesContract.Presenter {

    //where the request launch for first time so show loading
    var isFirstRequestCall = true

    override fun getNearByPlaces(lat: Double, long: Double) {

        if (isFirstRequestCall)
            view?.showLoading()

        val latLang = "$lat,$long"

//            "40.74224,-73.99386"


        compositeDisposable.addAll(
            dataManager.getNearByPlaces(latLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        if (it.meta.code == 200) {
                            view?.onGetNearByPlacesSuccess(it.response.groups[0].items)
                            isFirstRequestCall = false
                        } else {
                            view?.onGetNearByPlacesFail()
                        }
                    },
                    onError = {
                        view?.hideLoading()
                        view?.onNetworkError()
                    })
        )
    }

}