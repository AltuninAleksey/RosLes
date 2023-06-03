package com.example.rosles.Screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.GpsTrackerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.io.File


class GpxTrack : AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    private lateinit var binding: GpsTrackerBinding
    private var activetableRow: TableRow? = null
    private var activetableNameFile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GpsTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="GPS"

        binding.toolbar.reload.visibility= View.GONE
        binding.toolbar.open.visibility= View.GONE
        binding.toolbar.save.visibility= View.GONE

        var gpStracker = GPStracker(this)

        initTable()

        binding.toolbar.addbutton.setOnClickListener {
            // Имя файла, который вы хотите поделиться
            /*val file = File(getExternalFilesDir(null), activetableNameFile)

            val uri = FileProvider.getUriForFile(this, "com.example.rosles", file)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(Intent.createChooser(shareIntent, "Поделиться файлом"))*/
        }

        val listWaypoint = arrayListOf<Waypoint>()
        binding.buttonStart.setOnClickListener {
            if (SaveState.gpsCoroutine == null || SaveState.gpsCoroutine?.isCompleted == true) {
                SaveState.gpsCoroutine = CoroutineScope(Dispatchers.IO).launch {
                    println("---------------------------gpsCoroutine--------------------- START")
                    var i = 0
                    val location = gpStracker.location

                    while (true) {
                        delay(2000)
                        i++
                        println("---------------------------gpsCoroutine--------------------- WORK $i \n" +
                                "longitude=${location.longitude}, latitude=${location.latitude}")
                        val waypoint = Waypoint()
                        waypoint.lon = location.longitude
                        waypoint.lat = location.latitude
                        listWaypoint.add(waypoint)
                    }
                }
            }
        }

        binding.buttonPause.setOnClickListener {
        }

        binding.buttonStop.setOnClickListener {
            println("---------------------------gpsCoroutine--------------------- STOP")
            SaveState.gpsCoroutine?.cancel()
            SaveState.gpsCoroutine = null
            savePointsAsGpx(this, listWaypoint, "WAY3.gpx")
            binding.tblLayout.removeAllViews()
            initTable()
        }

        binding.toolbar.delete.setOnClickListener {
            val directory = getExternalFilesDir(null)
            val fileToDelete = File(directory, activetableNameFile)
            if (fileToDelete.exists()) {
                val deleted = fileToDelete.delete()
            }

            binding.tblLayout.removeAllViews()
            initTable()
        }
    }

    private fun initTable() {
        val storageDir = this.getExternalFilesDir(null)
        val fileList = mutableListOf<File>()
        storageDir?.let {
            if (it.isDirectory) {
                val files = it.listFiles()
                if (files != null) {
                    fileList.addAll(files)
                }
            }
        }

        var index = 1
        for (el in fileList) {
            var tableRow = TableRow(this)
            val valuesOfRow: List<String> = mutableListOf(
                index++.toString(),
                "data",
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.rosles.R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
            R.id.main->{startActivity(Intent(this, Dashboard::class.java))}
            R.id.itemperechet->{startActivity(Intent(this, MainActivity::class.java))}
            R.id.itemgps->{startActivity(Intent(this, gps_activity::class.java))
                finish()}
            R.id.profile->{startActivity(Intent(this, profile::class.java))
                finish()}

        }
        return true
    }
}

// Класс для представления точки в формате GPX
@Root(name = "gpx")
class GpxFile {
    @field:ElementList(entry = "wpt", inline = true)
    var waypoints: List<Waypoint>? = null
}

class Waypoint {
    @field:Element
    var lat: Double = 0.0

    @field:Element
    var lon: Double = 0.0

    @field:Element(required = false)
    var name: String? = null

    @field:Element(required = false)
    var desc: String? = null
}

fun savePointsAsGpx(context: Context, points: List<Waypoint>, fileName: String) {
    val gpxFile = GpxFile()
    val storageDir = context.getExternalFilesDir(null)
    gpxFile.waypoints = points

    val serializer: Serializer = Persister()
    val file = File(storageDir, fileName)

    try {
        serializer.write(gpxFile, file)
        println("GPX" + " GPX file saved successfully.")
    } catch (e: Exception) {
        e.printStackTrace()
        println("GPX" + " Error saving GPX file.")
    }
}