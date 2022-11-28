package com.istudio.distancetracker.modules.mapTapCoOrdinates

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.utils.Constants
import java.lang.Exception

class MapTapCoOrdinatesActivity : AppCompatActivity(), OnMapReadyCallback {

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

        map.apply {
            // Add a marker in Sydney and move the camera
            val location = Constants.disneyLandLocation
            val title = Constants.disneyLandMarkerStr
            addMarker(MarkerOptions().position(location).title(title))
            moveCamera(CameraUpdateFactory.newLatLng(location))
        }

        map.setOnMapClickListener {
            val latLong : String = it.latitude.toString().plus("<-->").plus(it.longitude)
            Toast.makeText(this@MapTapCoOrdinatesActivity,"Clicked:".plus(latLong),Toast.LENGTH_LONG).show()
        }

        map.setOnMapLongClickListener {
            val latLong : String = it.latitude.toString().plus("<-->").plus(it.longitude)
            Toast.makeText(this@MapTapCoOrdinatesActivity,"Clicked:".plus(latLong),Toast.LENGTH_LONG).show()
        }
    }

}