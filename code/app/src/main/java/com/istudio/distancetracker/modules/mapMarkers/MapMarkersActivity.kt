package com.istudio.distancetracker.modules.mapMarkers

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.utils.Constants
import kotlinx.coroutines.launch

class MapMarkersActivity : AppCompatActivity(), OnMapReadyCallback {

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
        showDialog()
        return true
    }

    // Here the map loading is initiated
    private fun initiateMapLoading() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private lateinit var markerPosition : Marker

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        lifecycleScope.launch {
            map.apply {
                // Add a marker in Sydney and move the camera
                val location = Constants.disneyLandLocation
                val bounds = Constants.disneyBoundsLocation
                val title = Constants.disneyLandMarkerStr
                // Set the marker in the map
                map.addMarker(MarkerOptions().position(location).title(title))?.apply { markerPosition = this }
                // Set up the boundary
                animateCamera(CameraUpdateFactory.newLatLngZoom(bounds.center,15f), 4000,
                    object : GoogleMap.CancelableCallback {
                        override fun onCancel() { Toast.makeText(this@MapMarkersActivity,"Cancelled",Toast.LENGTH_LONG).show() }
                        override fun onFinish() { Toast.makeText(this@MapMarkersActivity,"Map Loaded",Toast.LENGTH_LONG).show() }
                    })
                // Set the bounds for camera such that it can't move outside the bounds
                setLatLngBoundsForCameraTarget(bounds)
            }
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.bottom_sheet_map_type)
        }
        val deleteMarker: LinearLayout = dialog.findViewById(R.id.layoutOne)
        val mapHybrid: LinearLayout = dialog.findViewById(R.id.layoutTwo)
        val mapSatellite: LinearLayout = dialog.findViewById(R.id.layoutThree)
        val mapTerrain: LinearLayout = dialog.findViewById(R.id.layoutFour)
        val mapNone: LinearLayout = dialog.findViewById(R.id.layoutFive)

        deleteMarker.setOnClickListener { initDeleteMarker(dialog) }
        /*mapHybrid.setOnClickListener { setType(GoogleMap.MAP_TYPE_HYBRID,dialog) }
        mapSatellite.setOnClickListener { setType(GoogleMap.MAP_TYPE_SATELLITE,dialog) }
        mapTerrain.setOnClickListener { setType(GoogleMap.MAP_TYPE_TERRAIN,dialog) }
        mapNone.setOnClickListener { setType(GoogleMap.MAP_TYPE_NONE,dialog) }*/

        dialog.show()
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.DialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun initDeleteMarker(dialog: Dialog) {
        if(::markerPosition.isInitialized){
            dialog.dismiss()
            markerPosition.remove();
        }
    }
}