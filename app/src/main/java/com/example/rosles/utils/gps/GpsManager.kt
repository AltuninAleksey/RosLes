package com.example.rosles.utils.gps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class GpsManager (private val activityCompat: AppCompatActivity, private val looper: Looper) {
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    val handler: Handler = Handler(looper)

    fun init() {
        // проверяем что разрешение получено
        locationManager = activityCompat.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Получаем обновленные координаты
                latitude = location.latitude
                longitude = location.longitude
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String) {
                // Ваш код для обработки включения поставщика местоположения
            }

            override fun onProviderDisabled(provider: String) {
                // Ваш код для обработки отключения поставщика местоположения
            }
        }
    }

    fun updateLocation() {
        checkAndRequestLocationPermission()

        // Запрашиваем обновления местоположения с использованием LocationListener
        handler.post {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                locationListener
            )
        }

        // Получаем последнее известное местоположение
        val lastKnownLocationNetworkProvider = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val lastKnownLocationGpsProvider = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocationGpsProvider != null) {
            // Если есть последнее известное местоположение, обновляем координаты
            latitude = lastKnownLocationGpsProvider.latitude
            longitude = lastKnownLocationGpsProvider.longitude
        } else if (lastKnownLocationNetworkProvider != null) {
            latitude = lastKnownLocationNetworkProvider.latitude
            longitude = lastKnownLocationNetworkProvider.longitude
        } else {
            throw IllegalStateException("Нет доступного местоположения")
        }
    }

    private fun checkAndRequestLocationPermission() {
        val permissionToRequest = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(
                    activityCompat,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }

        // Проверяем разрешение на точное местоположение
        if (ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Проверяем разрешение на грубое местоположение
        if (ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        // Если есть разрешения для запроса, выполняем запрос
        if (permissionToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activityCompat,
                permissionToRequest.toTypedArray(),
                1
            )
            throw IllegalStateException("Разрешение на GPS не предоставлено")
        }
    }
}