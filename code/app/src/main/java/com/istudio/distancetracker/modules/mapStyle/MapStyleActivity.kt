package com.istudio.distancetracker.modules.mapStyle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.misc.TypeAndStyle
import com.istudio.distancetracker.utils.Constants
import java.lang.Exception

class MapStyleActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateMapLoading()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.choose_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMapStyle(map,this@MapStyleActivity)
        return true
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
    }

    private fun setMapStyle(googleMap: GoogleMap, context: Context) {
        try {
            // Get the map resource
            val mapOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.style)
            // Set the new style
            val success = googleMap.setMapStyle(mapOptions)
            if (googleMap.setMapStyle(mapOptions)) {
                Toast.makeText(context,"Map style loaded",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"Map style loading failed",Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.d("Maps", e.toString())
        }
    }
}