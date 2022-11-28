package com.istudio.distancetracker.misc

import android.content.Context
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import com.istudio.distancetracker.R
import java.lang.Exception

class TypeAndStyle {

    fun setMapStyle(googleMap: GoogleMap, context: Context) {
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.style
                )
            )
            if (!success) {
                Log.d("Maps", "Failed to add Style.")
            }
        } catch (e: Exception) {
            Log.d("Maps", e.toString())
        }
    }

}