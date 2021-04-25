package com.example.nearbyapp.ui;

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nearbyapp.R
import com.example.nearbyapp.ui.adapter.PlacesAdapter
import com.nearby.app.base.BaseFragment
import com.nearby.app.data.ui_models.Item
import com.nearby.app.location.LocationUtils
import com.nearby.app.utils.loadImage
import com.nearby.app.utils.secretB
import com.nearby.app.utils.showB
import kotlinx.android.synthetic.main.fragment_near_by_places.*
import kotlinx.android.synthetic.main.layout_empty.view.*
import mumayank.com.airlocationlibrary.AirLocation
import org.koin.androidx.viewmodel.ext.android.viewModel


class NearByPlacesFragment : BaseFragment<NearByPlacesContract.Presenter>(),
    NearByPlacesContract.View, PlacesAdapter.PlaceListener {

    override val presenter by viewModel<NearByPlacesPresenter>()

    var layoutManager: GridLayoutManager? = null

    var adapter: PlacesAdapter? = null

    var realTimesLocations = mutableListOf<Location>()

    var isFirstRequestCall = true

    override fun getLayoutResource(): Int = R.layout.fragment_near_by_places

    /*The aim object that get location from all handled with AirLocation lib but
     I import it as module and make some changes on it .
    */
    private var currentLocation: AirLocation? = null


    override fun initLayout(savedInstanceState: Bundle?, view: View) {
        presenter.attachView(this, lifecycle)

        setHasOptionsMenu(true)

        setUpRecyclerView()

        // initialize location for first time
        setUpGetLocationController(false)

    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.mode_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            //select app mode that realTimeUpdate
            R.id.realTimeUpdate -> {
                setUpGetLocationController(false)
                true
            }

            //select app mode that singleUpdate
            R.id.singleUpdate -> {
                setUpGetLocationController(true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerView() {
        layoutManager = GridLayoutManager(context, 1)
        placesRV?.layoutManager = layoutManager
        adapter = PlacesAdapter(mutableListOf(), this)
        placesRV.adapter = adapter
    }

    override fun onClickPlace(place: Item) {
        //handle any needed action
    }


    // location logic handling there
    private fun setUpGetLocationController(singleTimeUpdate: Boolean) {

        currentLocation = activity?.let {
            AirLocation(
                it,
                callback = object : AirLocation.Callback {

                    override fun onSuccess(locations: ArrayList<Location>) {
                        val firstLocation = locations.first()

                        if (!singleTimeUpdate) {
                            realTimesLocations.addAll(locations)

                            handleRealTimeDifferenceDistance(realTimesLocations)

                        } else {
                            presenter.getNearByPlaces(
                                firstLocation.latitude,
                                firstLocation.longitude
                            )

                        }
                    }

                    override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                        Toast.makeText(context, locationFailedEnum.name, Toast.LENGTH_LONG)
                            .show()
                    }

                }, isLocationRequiredOnlyOneTime = singleTimeUpdate
            )
        }
    }

    // ensure that the difference between places 500m or more
    fun handleRealTimeDifferenceDistance(locations: MutableList<Location>) {

        //where the request launch for first time so no metres difference
        if (isFirstRequestCall)
            presenter.getNearByPlaces(locations.first().latitude, locations.first().longitude)
        else {
            if (LocationUtils.getDifferenceDistanceFromLatLong(
                    locations.first(),
                    locations.last()
                ) >= 500
            ) {
                presenter.getNearByPlaces(locations.last().latitude, locations.last().longitude)
                realTimesLocations.clear()
            }
        }
    }

    // receive data there
    override fun onGetNearByPlacesSuccess(places: MutableList<Item>) {
        if (places.isNullOrEmpty())
            emptyLayout.showB()
        else {
            emptyLayout.secretB()
            adapter?.swapData(places)
        }

        isFirstRequestCall = false
    }

    // receive data failed
    override fun onGetNearByPlacesFail() {
        emptyLayout.showB()
        emptyLayout.emptyIV.loadImage(R.drawable.ic_error)
        emptyLayout.emptyTV.text = getString(R.string.some_error)
    }

    // result for handling permissions
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        currentLocation?.onActivityResult(
            requestCode,
            resultCode,
            data
        )
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        currentLocation?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onResume() {
        super.onResume()
        if (!presenter.isViewAttached()) {
            presenter.attachView(this, lifecycle)
        }
    }


    companion object {
        fun newInstance(): NearByPlacesFragment {
            val fragment = NearByPlacesFragment()
            return fragment
        }
    }

}
