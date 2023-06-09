package com.example.rosles.Screens

import com.example.rosles.utils.gps.simpleframework.ParseGps
import kotlinx.coroutines.Job

object SaveState {
    var gpsCoroutine: Job? = null
    var isPauseRecord: Boolean = false
    var recordingFileName: String? = null
    val listWaypoint: ArrayList<ParseGps.Waypoint> = arrayListOf()
}