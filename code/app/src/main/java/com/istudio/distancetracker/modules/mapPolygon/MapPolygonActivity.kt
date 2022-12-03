package com.istudio.distancetracker.modules.mapPolygon

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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.utils.Constants
import com.istudio.distancetracker.utils.Constants.polyPointsOfDisney
import com.istudio.distancetracker.utils.Constants.polygonPointsOfDisney
import kotlinx.coroutines.launch

class MapPolygonActivity : AppCompatActivity(), OnMapReadyCallback, OnPolylineClickListener {

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
        val polylineOptions = PolygonOptions().apply {
            add(polygonPointsOfDisney[0],polygonPointsOfDisney[1],polygonPointsOfDisney[2],polygonPointsOfDisney[3])
            fillColor(Color.BLACK)
            strokeColor(Color.BLUE)
            clickable(true)
        }
        val polyLine = map.addPolygon(polylineOptions)
        polyLine.points = polygonPointsOfDisney
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

    override fun onPolylineClick(p0: Polyline) {
        Toast.makeText(this@MapPolygonActivity,"Click Action!",Toast.LENGTH_LONG).show()
    }
}