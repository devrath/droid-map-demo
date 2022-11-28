package com.istudio.distancetracker.modules.animateCamera

import android.content.Context
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.utils.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class AnimateCameraActivity : AppCompatActivity(), OnMapReadyCallback {

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
                val bounds = Constants.disneyBoundsLocation
                val title = Constants.disneyLandMarkerStr
                // Set the marker in the map
                addMarker(MarkerOptions().position(location).title(title))
                // Set up the boundary
                animateCamera(CameraUpdateFactory.newLatLngZoom(bounds.center,15f), 4000,
                    object : GoogleMap.CancelableCallback {
                        override fun onCancel() { Toast.makeText(this@AnimateCameraActivity,"Cancelled",Toast.LENGTH_LONG).show() }
                        override fun onFinish() { Toast.makeText(this@AnimateCameraActivity,"Completed",Toast.LENGTH_LONG).show() }
                    })
                // Set the bounds for camera such that it can't move outside the bounds
                setLatLngBoundsForCameraTarget(bounds)
            }
        }
    }
}