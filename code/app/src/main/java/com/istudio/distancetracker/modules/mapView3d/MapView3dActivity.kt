package com.istudio.distancetracker.modules.mapView3d

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.utils.Constants
import kotlinx.coroutines.launch

class MapView3dActivity : AppCompatActivity(), OnMapReadyCallback {

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
        }

    }

    /**
     * Set the camera position in such a way that building view is displayed.
     */
    private fun setCameraPosition(): CameraPosition {
        val zoomValue = 17f
        val baringValue = 100f
        val tiltValue = 45f
        return CameraPosition.Builder().apply {
            target(Constants.disneyLandLocation)
            zoom(zoomValue)
            bearing(baringValue)
            tilt(tiltValue)
        }.build()
    }
}