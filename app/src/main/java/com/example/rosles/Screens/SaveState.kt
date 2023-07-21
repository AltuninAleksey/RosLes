package com.example.rosles.Screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.rosles.utils.gps.GpsManager
import com.example.rosles.utils.gps.simpleframework.ParseGps
import kotlinx.coroutines.Job

object SaveState {
    private const val PREF_NAME = "SaveStatePrefs"
    private const val KEY_IS_PAUSE_RECORD = "isPauseRecord"
    private const val KEY_RECORDING_FILE_NAME = "recordingFileName"
    private const val KEY_INTENT_SERVICE = "saveIntentService"

    var gpsCoroutine: Job? = null
    var isPauseRecord: Boolean = false
    var recordingFileName: String? = null
    val listWaypoint: ArrayList<ParseGps.Waypoint> = arrayListOf()
    var saveGpsManager: GpsManager? = null
    var saveIntentService: Intent? = null

    fun saveState(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        // Сохранение состояния в SharedPreferences
        editor.putBoolean(KEY_IS_PAUSE_RECORD, isPauseRecord)
        editor.putString(KEY_RECORDING_FILE_NAME, recordingFileName)
        editor.putString(KEY_INTENT_SERVICE, saveIntentService?.toUri(Intent.URI_INTENT_SCHEME))

        editor.apply()
    }

    fun loadState(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Загрузка состояния из SharedPreferences
        isPauseRecord = prefs.getBoolean(KEY_IS_PAUSE_RECORD, false)
        recordingFileName = prefs.getString(KEY_RECORDING_FILE_NAME, null)
        val intentString = prefs.getString(KEY_INTENT_SERVICE, null)
        if (intentString != null) {
            Intent.parseUri(intentString, Intent.URI_INTENT_SCHEME).also {
                saveIntentService = it
            }
        }
    }

}