package com.example.rosles

import android.app.Dialog
import android.util.DisplayMetrics

fun Dialog.setSizeRelativeCurrentWindow(widthRatio: Double, heightRatio: Double) {
    val displayMetrics = DisplayMetrics()

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = this.context.display
        display?.getRealMetrics(displayMetrics)
    } else {
        @Suppress("DEPRECATION")
        val display = this.window?.windowManager?.defaultDisplay
        @Suppress("DEPRECATION")
        display?.getMetrics(displayMetrics)
    }
    val height = displayMetrics.heightPixels
    val width = displayMetrics.widthPixels

    this.window?.setLayout((width*widthRatio).toInt(), (height*heightRatio).toInt())
}