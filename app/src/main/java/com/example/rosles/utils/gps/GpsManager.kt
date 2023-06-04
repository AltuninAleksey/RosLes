package com.example.rosles.utils.gps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class GpsManager (private val activityCompat: AppCompatActivity) {
    public var longitude: Double = 0.0
    public var latitude: Double = 0.0

    public fun updateLocation() {
        // проверяем что разрешение получено
        val locationManager = activityCompat.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Проверяем разрешения на использование местоположения
        if (ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activityCompat,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            throw IllegalStateException("Разрешение на GPS не предоставлено")
        }

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Получаем обновленные координаты
                latitude = location.latitude
                longitude = location.longitude
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        // Запрашиваем обновления местоположения с использованием LocationListener
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )

        // Получаем последнее известное местоположение
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            // Если есть последнее известное местоположение, обновляем координаты
            latitude = lastKnownLocation.latitude
            longitude = lastKnownLocation.longitude
        } else {
            throw IllegalStateException("Нет доступного местоположения")
        }
    }
}