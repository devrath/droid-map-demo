package com.istudio.distancetracker.utils

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object Constants {
    val disneyLandLocation = LatLng(33.81213278476581, -117.91896178782112)
    val draggableMarkerLocation = LatLng(33.81196172266233, -117.9192556460658)
    val customMarkerLocation = LatLng(33.812302725281405, -117.91956550307864)
    val layoutMarkerLocation = LatLng(33.81148154041286, -117.92105482994259)
    const val disneyLandMarkerStr = "Disney land marker"

    private val disneyLandLocationBound1 = LatLng(33.8061483029195, -117.93071633478273)  //SW
    private val disneyLandLocationBound2 = LatLng(33.81231042413435, -117.91898157364918) //NE

    val disneyBoundsLocation = LatLngBounds(disneyLandLocationBound1, disneyLandLocationBound2)


    val polyPointsOfDisney = arrayListOf<LatLng>(
        disneyLandLocation,
        LatLng(33.812182619161156, -117.91890451874627),
        LatLng(33.81210461964178, -117.91882807578864),
        LatLng(33.81200544872161, -117.91889311935783),
        LatLng(33.812040548554805, -117.91924247708515)
    )


}