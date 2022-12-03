package com.istudio.distancetracker.modules.mapCircle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnPolylineClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.utils.Constants
import com.istudio.distancetracker.utils.Constants.polyPointsOfDisney
import kotlinx.coroutines.launch

class MapCircleActivity : AppCompatActivity(), OnMapReadyCallback, OnPolylineClickListener {

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

        // Set up click listener for polyline
        map.setOnPolylineClickListener(this)
    }

    private fun setUpPolyLineSettings() {
        val polylineOptions = CircleOptions().apply {
            fillColor(Color.TRANSPARENT)
            strokeColor(Color.BLUE)
            clickable(true)
            radius(5000.0)
            center(Constants.disneyLandLocation)
        }
        map.addCircle(polylineOptions)
    }

    /**
     * Set the zoom level for the camera
     */
    private fun setCameraPosition(): CameraPosition {
        val zoomValue = 12f
        val baringValue = 100f
        val tiltValue = 45f
        return CameraPosition.Builder().apply {
            target(Constants.disneyLandLocation)
            zoom(zoomValue)
            bearing(baringValue)
        }.build()
    }

    override fun onPolylineClick(p0: Polyline) {
        Toast.makeText(this@MapCircleActivity,"Click Action!",Toast.LENGTH_LONG).show()
    }
}