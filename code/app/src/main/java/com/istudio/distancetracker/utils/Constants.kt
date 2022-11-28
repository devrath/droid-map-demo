package com.istudio.distancetracker.utils

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object Constants {
    val disneyLandLocation = LatLng(33.81213278476581, -117.91896178782112)
    const val disneyLandMarkerStr = "Disney land marker"

    private val disneyLandLocationBound1 = LatLng(33.8061483029195, -117.93071633478273)  //SW
    private val disneyLandLocationBound2 = LatLng(33.81231042413435, -117.91898157364918) //NE

    val disneyBoundsLocation = LatLngBounds(disneyLandLocationBound1, disneyLandLocationBound2)

}