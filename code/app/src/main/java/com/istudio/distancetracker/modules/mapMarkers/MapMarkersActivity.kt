package com.istudio.distancetracker.modules.mapMarkers

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.misc.CustomInfoAdapter
import com.istudio.distancetracker.utils.Constants
import com.istudio.distancetracker.utils.Constants.disneyLandLocation
import kotlinx.coroutines.launch

class MapMarkersActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener
{

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var markerPosition : Marker

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

    // <---------------------------------> Map Ready Listener <------------------------------------>
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        lifecycleScope.launch {
            map.apply {
                // Add a marker in Sydney and move the camera
                val location = Constants.disneyLandLocation
                val bounds = Constants.disneyBoundsLocation
                val title = Constants.disneyLandMarkerStr
                // Set the marker in the map
                map.addMarker(MarkerOptions().position(location).title(title))?.apply {
                    // Set the position of marker in a variable so that we can delete it later
                    markerPosition = this
                    // We can set this tag and get them on click of map marker
                    tag = "Marker is Clicked !!"
                }
                // Set up the boundary
                animateCamera(CameraUpdateFactory.newLatLngZoom(disneyLandLocation,17f), null)
                // Set the bounds for camera such that it can't move outside the bounds
                setLatLngBoundsForCameraTarget(bounds)
            }
        }
    }
    // <---------------------------------> Map Ready Listener <------------------------------------>

    // <---------------------------------> Marker Click Listener <--------------------------------->
    override fun onMarkerClick(marker: Marker): Boolean {
        if(marker!=null){
            Toast.makeText(this@MapMarkersActivity,marker.tag.toString(),Toast.LENGTH_LONG).show()
        }
        return true
    }
    // <---------------------------------> Marker Click Listener <--------------------------------->

    // <---------------------------------> Marker Drag Listeners <--------------------------------->
    override fun onMarkerDrag(marker: Marker) {
        val message = marker.position.toString().plus("<-->").plus("Dragging in progress")
        Log.d("MarkerMovement",message)
    }

    override fun onMarkerDragEnd(marker: Marker) {
        val message = marker.position.toString().plus("<-->").plus("Dragging has ended")
        Log.d("MarkerMovement",message)
    }

    override fun onMarkerDragStart(marker: Marker) {
        val message = marker.position.toString().plus("<-->").plus("Dragging has started")
        Log.d("MarkerMovement",message)
    }
    // <---------------------------------> Marker Drag Listeners <--------------------------------->

    private fun showDialog() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.bottom_sheet_map_type)
        }
        val deleteMarker: LinearLayout = dialog.findViewById(R.id.layoutOne)
        val markerClick: LinearLayout = dialog.findViewById(R.id.layoutTwo)
        val markerDraggable: LinearLayout = dialog.findViewById(R.id.layoutThree)
        val customMarker: LinearLayout = dialog.findViewById(R.id.layoutFour)
        val customLayoutMarker: LinearLayout = dialog.findViewById(R.id.layoutFive)

        deleteMarker.setOnClickListener { initDeleteMarker(dialog) }
        markerClick.setOnClickListener { initMarkerClickInfo(dialog) }
        markerDraggable.setOnClickListener { initDraggableMarker(dialog) }
        customMarker.setOnClickListener { initIconForMarker(dialog) }
        customLayoutMarker.setOnClickListener { initCustomLayoutMarker(dialog) }

        dialog.show()
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.DialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun initCustomLayoutMarker(dialog: Dialog) {
        dialog.dismiss()
        val layoutMarker = Constants.layoutMarkerLocation
        map.addMarker(
            MarkerOptions()
                .position(layoutMarker)
                .title("Title")
                .snippet("Description")
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(layoutMarker,17f))
        map.setInfoWindowAdapter(CustomInfoAdapter(this))
    }

    private fun initIconForMarker(dialog: Dialog) {
        dialog.dismiss()
        val customMarker = Constants.customMarkerLocation
        map.addMarker(
            MarkerOptions()
                .position(customMarker)
                .title("Custom Marker")
                .icon(fromVectorToBitmap(R.drawable.ic_seletion,Color.parseColor("#000000")))
        )
    }

    private fun initDraggableMarker(dialog: Dialog) {
        dialog.dismiss()
        val draggableMarker = Constants.draggableMarkerLocation

        map.addMarker(MarkerOptions().position(draggableMarker).title("Draggable marker"))?.apply {
            isDraggable = true
            // Drag listener for marker
            map.setOnMarkerDragListener(this@MapMarkersActivity)
        }
    }

    private fun initMarkerClickInfo(dialog: Dialog) {
        dialog.dismiss()
        // Set marker listener
        map.setOnMarkerClickListener(this@MapMarkersActivity)
        Snackbar.make(binding.root, "Click the marker for demo", Snackbar.LENGTH_SHORT).show();
    }

    private fun initDeleteMarker(dialog: Dialog) {
        if(::markerPosition.isInitialized){
            dialog.dismiss()
            markerPosition.remove();
        }
    }

    /**
     * Convert a vector drawable into a bitmap descriptor
     */
    private fun fromVectorToBitmap(id: Int, color: Int): BitmapDescriptor {
        val vectorDrawable: Drawable? = ResourcesCompat.getDrawable(resources, id, null)
        if(vectorDrawable == null){
            Log.d("MapsActivity", "Resource not found.")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}