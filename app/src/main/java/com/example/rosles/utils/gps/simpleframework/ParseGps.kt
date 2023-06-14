package com.example.rosles.utils.gps.simpleframework

import android.content.Context
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.io.File
import java.io.FileOutputStream
import java.io.Serializable

class ParseGps {
    @Root(name = "gpx")
    class GpxFile: Serializable {
        @field:ElementList(entry = "wpt", inline = true)
        var waypoints: List<Waypoint>? = null
    }

    public class Waypoint: Serializable {
        @field:Element
        var lat: Double = 0.0

        @field:Element
        var lon: Double = 0.0

        @field:Element(required = false)
        var name: String? = null

        @field:Element(required = false)
        var desc: String? = null
    }

    companion object {
        fun savePointsAsGpx(context: Context, fileName: String, points: List<Waypoint> = listOf()) {
            val gpxFile = GpxFile()
            val serializer: Serializer = Persister()
            val file = File(context.getExternalFilesDir(null), fileName)

            gpxFile.waypoints = points
            for(el in points) {
                println(el.lon.toString() + " " + el.lat.toString())
            }
            try {
                val fileOutputStream = FileOutputStream(file)
                serializer.write(gpxFile, file)
                fileOutputStream.close()
                println("GPX" + " GPX file saved successfully.")
            } catch (e: Exception) {
                e.printStackTrace()
                println("GPX" + " Error saving GPX file.")
            }
        }

        fun loadGpxFile(context: Context, fileName: String): GpxFile? {
            val serializer: Serializer = Persister()
            val file = File(context.getExternalFilesDir(null), fileName)

            var gpxFile: GpxFile? = null
            try {
                gpxFile = serializer.read(GpxFile::class.java, file)
            } catch (e: Exception) {
                e.printStackTrace()
                println("GPX" + " Error loading GPX file.")
            }

            val arrayList = gpxFile?.waypoints
            var i = 0
            arrayList?.forEach {
                println(i++.toString() + " - " + it.lon.toString() + " " + it.lat.toString() + " ")
            }
            return gpxFile
        }
    }


}