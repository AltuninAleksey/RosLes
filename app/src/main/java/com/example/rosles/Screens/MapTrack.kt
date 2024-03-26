package com.example.rosles.Screens

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.rosles.BaseActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.MapLayoutBinding
import com.example.rosles.utils.gps.simpleframework.ParseGps
import com.example.rosles.utils.mapkit.TokenMapKitYandex.TOKEN
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition

class MapTrack: BaseActivity("Трек на карте") {
    val viewModel by viewModels<ViewModels>()
    var flagSettings: Boolean = false
    lateinit var binding: MapLayoutBinding
    var listOfTrack: ArrayList<ParseGps.Waypoint> = arrayListOf<ParseGps.Waypoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfInitMapKitFactory()
        binding = MapLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListOfTrack()
        initSwitchLines()
        initMap()

        binding.card.background.alpha = 140
        binding.settings.setOnClickListener {
            if (!flagSettings)
                animateSwitch()
            else
                animateSwitchBack()
        }
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
        MapKitFactory.setApiKey(TOKEN)
        MapKitFactory.initialize(this)
    }

    private fun initListOfTrack() {
        listOfTrack = intent.getSerializableExtra("listOfTrack")
                as ArrayList<ParseGps.Waypoint>
    }

    private fun initMap() {
        if (!listOfTrack.isEmpty()) {
            toZoom(listOfTrack.first().lat, listOfTrack.first().lon)
            printTrackGivenSwitch()
        }
    }

    private fun initSwitchLines() {
        binding.switchLines.isChecked = true
        binding.switchLines.setOnCheckedChangeListener {_, _ ->
            binding.yandexMap.map.mapObjects.clear()
            printTrackGivenSwitch()
        }
    }

    private fun selfInitMapKitFactory() {
        try {
            initMapKitFactory()
        } catch (E: AssertionError){
            Log.e("MAP EXCEPTIONS", "MapKitFactory.initialize as if failed")
        }
    }

    private fun toZoom(lat: Double, lon: Double) {
        binding.yandexMap.map.move(
            CameraPosition(Point(lat, lon),
                20.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2f), null
        )
    }

    private fun printTrackGivenSwitch() {
        for (i in 0 until  listOfTrack.size - 1 ) {
            val p1 = Point(listOfTrack[i].lat, listOfTrack[i].lon)
            val p2 = Point(listOfTrack[i+1].lat, listOfTrack[i+1].lon)
            val distanceBetweenPoints = getDistanceBetweenPoints(p1, p2)

            if (binding.switchLines.isChecked) {
                printAll(p1, p2, distanceBetweenPoints)
            } else {
                printOnlyBlue(p1, p2, distanceBetweenPoints)
            }
        }
    }

    private fun printAll(p1: Point, p2: Point, distanceBetweenPoints: Float) {
        printPoint(p1)
        printPoint(p2)

        if (distanceBetweenPoints >= 50f) {
            printRedLine(p1, p2)
        } else {
            printLine(p1, p2)
        }
    }

    private fun printOnlyBlue(p1: Point, p2: Point, distanceBetweenPoints: Float) {
        if (distanceBetweenPoints >= 50f) {
            return
        }
        printLine(p1, p2)
        printPoint(p1)
        printPoint(p2)
    }

    private fun printPoint(p: Point) {
        binding.yandexMap.map.mapObjects.addCollection().addPlacemark(
            Point(p.latitude, p.longitude)
        ).opacity = 1.5f
    }

    private fun printLine(p1: Point, p2: Point) {
        binding.yandexMap.map.mapObjects.addCollection().addPolyline(
            Polyline(listOf(p1, p2))
        )
    }

    private fun printRedLine(p1: Point, p2: Point) {
        binding.yandexMap.map.mapObjects.addCollection().addPolyline(
            Polyline(listOf(p1, p2))
        ).setStrokeColor(Color.RED)
    }

    private fun getDistanceBetweenPoints(p1: Point, p2: Point,): Float {
        val loc1 = Location("")
        loc1.latitude = p1.latitude
        loc1.longitude = p1.longitude

        val loc2 = Location("")
        loc2.latitude = p2.latitude
        loc2.longitude = p1.longitude

        return loc1.distanceTo(loc2)
    }

    private fun animateSwitch() {
        val translationX = ObjectAnimator.ofFloat(
            binding.card,
            "translationX", 0f, -binding.card.width.toFloat()
        )
        translationX.duration = 500

        val visibility = ObjectAnimator.ofFloat(binding.card, "alpha", 0f, 1f)
        visibility.duration = 500

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationX, visibility)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                flagSettings = true
                binding.settings.setImageResource(R.drawable.icbaselinesettings)
            }
        })

        animatorSet.start()
    }

    private fun animateSwitchBack() {
        val translationX = ObjectAnimator.ofFloat(
            binding.card,
            "translationX", -binding.card.width.toFloat(), 0f
        )
        translationX.duration = 500

        val visibility = ObjectAnimator.ofFloat(
            binding.card, "alpha", 1f, 0f
        )
        visibility.duration = 500

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationX, visibility)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                flagSettings = false
                binding.settings.setImageResource(R.drawable.icbaselinesettings_un)
            }
        })
        animatorSet.start()
    }
}