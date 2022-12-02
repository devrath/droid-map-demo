package com.istudio.distancetracker.modules.mapPolyline

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.utils.Constants
import com.istudio.distancetracker.utils.Constants.polyPointsOfDisney
import kotlinx.coroutines.launch
import java.lang.Exception

class MapPolyLineActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateMapLoading()
    }

    // Here the map loading is initiated
    private fun initiateMapLoading() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        lifecycleScope.launch {
            map.apply {
                // Add a marker in Sydney and move the camera
                val location = Constants.disneyLandLocation
                val title = Constants.disneyLandMarkerStr
                addMarker(MarkerOptions().position(location).title(title))
                moveCamera(CameraUpdateFactory.newCameraPosition(setCameraPosition()))
            }
            setUpPolyLineSettings()
        }
    }

    private fun setUpPolyLineSettings() {
        val polylineOptions = PolylineOptions().apply {
            width(5f)
            color(Color.MAGENTA)
            geodesic(true)
            clickable(true)
        }
        val polyLine = map.addPolyline(polylineOptions)
        polyLine.points = polyPointsOfDisney
    }

    /**
     * Set the zoom level for the camera
     */
    private fun setCameraPosition(): CameraPosition {
        val zoomValue = 20f
        val baringValue = 100f
        val tiltValue = 45f
        return CameraPosition.Builder().apply {
            target(Constants.disneyLandLocation)
            zoom(zoomValue)
            bearing(baringValue)
        }.build()
    }

}