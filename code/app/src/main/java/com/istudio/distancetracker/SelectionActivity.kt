package com.istudio.distancetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.istudio.distancetracker.databinding.ActivityMapsBinding
import com.istudio.distancetracker.databinding.ActivitySelectionBinding
import com.istudio.distancetracker.mapStyle.MapStyleActivity
import com.istudio.distancetracker.mapType.MapTypeActivity
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
    }
}