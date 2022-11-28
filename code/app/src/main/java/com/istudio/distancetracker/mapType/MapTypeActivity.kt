package com.istudio.distancetracker.mapType

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.misc.TypeAndStyle


class MapTypeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val typeAndStyle by lazy { TypeAndStyle() }


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_types_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showDialog()
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun showDialog() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.bottom_sheet_map_type)
        }
        val mapNormal: LinearLayout = dialog.findViewById(R.id.layoutOne)
        val mapHybrid: LinearLayout = dialog.findViewById(R.id.layoutTwo)
        val mapSatellite: LinearLayout = dialog.findViewById(R.id.layoutThree)
        val mapTerrain: LinearLayout = dialog.findViewById(R.id.layoutFour)
        val mapNone: LinearLayout = dialog.findViewById(R.id.layoutFive)

        mapNormal.setOnClickListener { setType(GoogleMap.MAP_TYPE_NORMAL,dialog) }
        mapHybrid.setOnClickListener { setType(GoogleMap.MAP_TYPE_HYBRID,dialog) }
        mapSatellite.setOnClickListener { setType(GoogleMap.MAP_TYPE_SATELLITE,dialog) }
        mapTerrain.setOnClickListener { setType(GoogleMap.MAP_TYPE_TERRAIN,dialog) }
        mapNone.setOnClickListener { setType(GoogleMap.MAP_TYPE_NONE,dialog) }

        dialog.show()
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.DialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun setType(typeValue: Int, dialog: Dialog) {
        map.mapType = typeValue
        dialog.dismiss()
    }

}