package com.istudio.distancetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.databinding.ActivitySelectionBinding
import com.istudio.distancetracker.modules.animateCamera.AnimateCameraActivity
import com.istudio.distancetracker.modules.mapBoundaries.MapBoundariesActivity
import com.istudio.distancetracker.modules.mapCircle.MapCircleActivity
import com.istudio.distancetracker.modules.mapCustomPolyline.MapCustomPolylineActivity
import com.istudio.distancetracker.modules.mapMarkers.MapMarkersActivity
import com.istudio.distancetracker.modules.mapPolygon.MapPolygonActivity
import com.istudio.distancetracker.modules.mapPolyline.MapPolyLineActivity
import com.istudio.distancetracker.modules.mapStyle.MapStyleActivity
import com.istudio.distancetracker.modules.mapTapCoOrdinates.MapTapCoOrdinatesActivity
import com.istudio.distancetracker.modules.mapType.MapTypeActivity
import com.istudio.distancetracker.modules.mapView3d.MapView3dActivity
import com.istudio.distancetracker.utils.openActivity

class SelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnMapTypeId.setOnClickListener { openActivity(MapTypeActivity::class.java) }
        binding.btnMapStyleId.setOnClickListener { openActivity(MapStyleActivity::class.java) }
        binding.btnMapView3dId.setOnClickListener { openActivity(MapView3dActivity::class.java) }
        binding.btnMapBoundariesId.setOnClickListener { openActivity(MapBoundariesActivity::class.java) }
        binding.btnMapAnimateCameraId.setOnClickListener { openActivity(AnimateCameraActivity::class.java) }
        binding.btnMapTapCoOrdinatesId.setOnClickListener { openActivity(MapTapCoOrdinatesActivity::class.java) }
        binding.btnMapMarkersId.setOnClickListener { openActivity(MapMarkersActivity::class.java) }
        binding.btnPolyLineDemoId.setOnClickListener { openActivity(MapPolyLineActivity::class.java) }
        binding.btnPolygonDemoId.setOnClickListener { openActivity(MapPolygonActivity::class.java) }
        binding.btnPolyCircleDemoId.setOnClickListener { openActivity(MapCircleActivity::class.java) }
        binding.btnCustomPolylineDemoId.setOnClickListener { openActivity(MapCustomPolylineActivity::class.java) }
    }
}