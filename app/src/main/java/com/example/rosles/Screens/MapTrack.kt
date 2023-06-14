package com.example.rosles.Screens

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.rosles.BaseActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.databinding.MapLayoutBinding
import com.example.rosles.utils.gps.simpleframework.ParseGps
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import java.lang.StringBuilder


class MapTrack: BaseActivity("Трек на карте") {
    val viewModel by viewModels<ViewModels>()
    lateinit var binding: MapLayoutBinding
    var listOfTrack: ArrayList<ParseGps.Waypoint> = arrayListOf<ParseGps.Waypoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfInitMapKitFactory()
        binding = MapLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listOfTrack = intent.getSerializableExtra("listOfTrack")
                as ArrayList<ParseGps.Waypoint>

        initMap()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.yandexMap.onStart()
    }

    override fun onStop() {
        binding.yandexMap.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop()
    }

    private fun initMapKitFactory() {
        MapKitFactory.setApiKey("67672189-f64b-4cfc-b019-3f14d2e65e70")
        MapKitFactory.initialize(this)
    }

    private fun selfInitMapKitFactory() {
        try {
            initMapKitFactory()
        } catch (E: AssertionError){
            Log.e("MAP EXCEPTIONS", "MapKitFactory.initialize as if failed")
        }
    }

    private fun initMap() {
        if (!listOfTrack.isEmpty()) {
            binding.yandexMap.map.move(
                CameraPosition(Point(listOfTrack[0].lat, listOfTrack[0].lon),
                20.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 2f), null
            )

            val placeMark = binding.yandexMap.map.mapObjects.addCollection().addPlacemark(
                Point(listOfTrack[0].lat, listOfTrack[0].lon)
            )
            placeMark.opacity = 1.5f
        }
    }
}