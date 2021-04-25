package com.nearby.app.location

import android.location.Location

object LocationUtils {

    fun getDifferenceDistanceFromLatLong(
        location1: Location,
        location2 :Location
    ): Float {

        val distanceInMeters: Float = location1.distanceTo(location2)
        return distanceInMeters
    }
}
