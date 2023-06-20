package com.example.rosles.utils.gps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        if (ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activityCompat,
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            throw IllegalStateException("Разрешение на GPS не предоставлено")
        }
    }
}