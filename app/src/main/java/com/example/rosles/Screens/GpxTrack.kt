package com.example.rosles.Screens

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Looper

import android.view.View
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import com.example.rosles.BaseActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.DialogAddFileGpxBinding
import com.example.rosles.databinding.GpsTrackerBinding
import com.example.rosles.setSizeRelativeCurrentWindow
import com.example.rosles.utils.gps.GpsManager
import com.example.rosles.utils.gps.simpleframework.ParseGps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.Map


class GpxTrack: BaseActivity("GPS Трекер") {

    val viewModel by viewModels<ViewModels>()
    private lateinit var binding: GpsTrackerBinding
    private lateinit var blinkingAnimatorOfWarn: ObjectAnimator
    private lateinit var dialogCreateFile: Dialog
    private var activetableRow: TableRow? = null
    private var activetableNameFile: String? = null
    private val gpsTracker = GpsManager(this, Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GpsTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTable()
        initAnimation()
        initDialog()

        binding.toolbar.addbutton.setOnClickListener {
            dialogCreateFile.show()
        }
        binding.toolbar.delete.setOnClickListener {
            deleteActiveFile()
        }
        binding.toolbar.share.setOnClickListener {
            shareActiveFile()
        }
        binding.toolbar.showTrack.setOnClickListener {
            warn("В РАЗРАБОТКЕ")
            if (activetableNameFile == null) {
                return@setOnClickListener
            }
            val intent = Intent(this, MapTrack::class.java)
            val listOfTrack: ArrayList<ParseGps.Waypoint> = arrayListOf<ParseGps.Waypoint>()
            ParseGps.loadGpxFile(this, activetableNameFile!!)?.waypoints?.forEach {
                listOfTrack.add(it)
            }
            intent.putExtra("listOfTrack", listOfTrack)
            startActivity(intent)
        }

        binding.buttonStart.setOnClickListener {
            if (activetableNameFile == null) {
                warn("Вы не выбрали файл для записи")
                return@setOnClickListener
            }
            startRecordingIfNotActiveRecord()
        }

        binding.buttonPause.setOnClickListener {
            if (SaveState.recordingFileName != null) {
                if (SaveState.isPauseRecord) {
                    continueRecord()
                } else {
                    pauseRecord()
                }
            }
        }

        binding.buttonStop.setOnClickListener {
            if (!SaveState.recordingFileName.isNullOrEmpty()) {
                stopRecording()
            }
        }

    }

    private fun continueRecord() {
        SaveState.isPauseRecord = false
        showWarnRecordToFile()
        if (!saveUpdateLocation()) {
            return
        }
        startRecordTrackInList()
    }

    private fun pauseRecord() {
        SaveState.isPauseRecord = true
        showWarnPauseRecordToFile()
        closeGpsCoroutine()
    }

    private fun showWarnRecordToFile() {
        binding.warnRecording.text = "Идёт запись в файл '${SaveState.recordingFileName}'"
        binding.warnRecording.visibility = View.VISIBLE
        binding.buttonPause.text = "Пауза"
        blinkingAnimatorOfWarn.start()
    }

    private fun showWarnPauseRecordToFile() {
        binding.warnRecording.text = "Пауза записи в файл '${SaveState.recordingFileName}'"
        binding.warnRecording.visibility = View.VISIBLE
        blinkingAnimatorOfWarn.cancel()
        binding.buttonPause.text = "Продолжить"
        binding.warnRecording.alpha = 1.0f
    }

    private fun unShowWarnRecordToFile() {
        binding.warnRecording.visibility = View.GONE
        blinkingAnimatorOfWarn.cancel()
    }

    private fun startRecordTrackInList() {
        if (SaveState.gpsCoroutine == null || SaveState.gpsCoroutine?.isCompleted == true) {
            SaveState.gpsCoroutine = CoroutineScope(Dispatchers.IO).launch {
                println("---------------------------gpsCoroutine--------------------- START")
                var i = 0

                gpsTracker.updateLocation()

                while (true) {
                    delay(2000)
                    println("---------------------------gpsCoroutine--------------------- WORK ${i++} \n" +
                            "longitude=${gpsTracker.longitude}, latitude=${gpsTracker.latitude}")
                    val waypoint = ParseGps.Waypoint()
                    waypoint.lon = gpsTracker.longitude
                    waypoint.lat = gpsTracker.latitude
                    SaveState.listWaypoint.add(waypoint)
                }
            }
        }
    }

    private fun startRecordingIfNotActiveRecord() {
        if (SaveState.recordingFileName == null) {
            SaveState.listWaypoint.clear()
            SaveState.recordingFileName = activetableNameFile
            ParseGps.loadGpxFile(this, SaveState.recordingFileName!!)?.waypoints?.forEach {
                SaveState.listWaypoint.add(it)
            }
            if (!saveUpdateLocation()) {
                return
            }
            startRecordTrackInList()
            showWarnRecordToFile()
        } else {
            warn("Запись уже идёт")
        }
    }

    private fun stopRecording() {
        println("---------------------------gpsCoroutine--------------------- STOP")
        ParseGps.savePointsAsGpx(this, SaveState.recordingFileName!!, SaveState.listWaypoint)
        SaveState.recordingFileName = null
        binding.buttonPause.text = "Пауза"
        updateTable()
        closeGpsCoroutine()
        unShowWarnRecordToFile()
        warn("Запись успешно сохранена")
    }
    private fun shareActiveFile() {
        if (!isActivetableRowAndNameFileNotNull())
            return
        val file = File(getExternalFilesDir(null), activetableNameFile!!)
        val uri = FileProvider.getUriForFile(this, "com.example.fileprovider", file)
        val shareIntent = Intent(Intent.ACTION_SEND)

        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(shareIntent, "Поделиться файлом"))
    }

    private fun deleteActiveFile(){
        if (!isActivetableRowAndNameFileNotNull())
            return
        if (activetableNameFile.equals(SaveState.recordingFileName)) {
            warn("Нельзя удалить файл в который идёт запись")
            return
        }

        val fileToDelete = File(getExternalFilesDir(null), activetableNameFile!!)
        if (fileToDelete.exists()) {
            fileToDelete.delete()
        }
        activetableRowAndNameFileToNull()
        updateTable()
    }

    private fun initAnimation() {
        blinkingAnimatorOfWarn = createBlinkingAnimator(binding.warnRecording)
        if (SaveState.recordingFileName != null) {
            if (SaveState.isPauseRecord) {
                showWarnPauseRecordToFile()
                binding.buttonPause.text = "Продолжить"
            } else {
                showWarnRecordToFile()
                binding.buttonPause.text = "Пауза"
            }
        }
    }

    private fun initDialog() {
        dialogCreateFile = Dialog(this)
        val bindingDialog: DialogAddFileGpxBinding = DialogAddFileGpxBinding.inflate(layoutInflater)

        dialogCreateFile.setContentView(bindingDialog.root)
        dialogCreateFile.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogCreateFile.setSizeRelativeCurrentWindow(0.8, 0.4)
        dialogCreateFile.setCancelable(true)

        bindingDialog.createFile.setOnClickListener {
            createFileWithCheck(bindingDialog.nameFile.text.toString())
        }
    }

    private fun createFileWithCheck(nameNewFile: String) {
        if (validateString(nameNewFile)) {
            ParseGps.savePointsAsGpx(this, nameNewFile + ".gpx")
            updateTable()
            dialogCreateFile.cancel()
        } else {
            warn("Не корректное имя файла. Разрашены только английские буквы, цифры и символ '_'")
        }
    }

    private fun initTable() {
        activetableRowAndNameFileToNull()
        val storageDir = this.getExternalFilesDir(null)
        val fileList = mutableListOf<File>()
        val dateFormat = SimpleDateFormat("dd-MM-yy/HH:mm", Locale.getDefault())
        storageDir?.let {dir ->
            if (dir.isDirectory) {
                val files = dir.listFiles()?.sortedByDescending { it.lastModified() }
                if (files != null) {
                    fileList.addAll(files)
                }
            }
        }

        var index = 1
        for (el in fileList) {
            if (!el.name.endsWith(".gpx")) {
                continue
            }
            val tableRow = TableRow(this)
            val valuesOfRow: List<String> = mutableListOf(
                index++.toString(),
                dateFormat.format(el.lastModified()),
                el.name
            )

            for (value in valuesOfRow) {
                val textView = TextView(this)
                textView.setTextColor(-0x1000000)
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView.text = value
                tableRow.addView(textView)

                tableRow.setOnClickListener {
                    activetableRow?.setBackgroundResource(R.color.color_transporent)
                    tableRow.setBackgroundResource(R.color.color_transporent)
                    activetableRow = tableRow
                    activetableNameFile = (tableRow.getChildAt(2) as TextView).text.toString()
                    activetableRow!!.setBackgroundResource(R.color.activecolumn)
                }

            }
            binding.tblLayout.addView(tableRow)
        }
    }

    private fun updateTable() {
        binding.tblLayout.removeAllViews()
        initTable()
    }

    private fun activetableRowAndNameFileToNull() {
        activetableRow = null
        activetableNameFile = null
    }

    private fun isActivetableRowAndNameFileNotNull() =
        activetableNameFile != null || activetableRow != null


    private fun createBlinkingAnimator(textView: TextView): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(textView, "alpha", 1.0f, 0.0f)
        animator.duration = 500
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.repeatMode = ObjectAnimator.REVERSE
        return animator
    }

    private fun closeGpsCoroutine() {
        SaveState.gpsCoroutine?.cancel()
        SaveState.gpsCoroutine = null
    }

    private fun saveUpdateLocation(): Boolean {
        try {
            gpsTracker.updateLocation()
        }  catch (illegalStateException: IllegalStateException) {
            warn(illegalStateException.message ?: "")
            return false
        }
        return true
    }

    fun validateString(input: String): Boolean {
        val pattern = "^[a-zA-Z0-9_]+$".toRegex()
        return input.matches(pattern)
    }
    private fun warn(textWarn: String) {
        Toast.makeText(this, textWarn, Toast.LENGTH_SHORT).show()
    }
}

