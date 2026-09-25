package com.example.rosles

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.Screens.Molodnyak
import com.example.rosles.Screens.StartScreen
import com.example.rosles.Screens.gps.gps_activity
import com.example.rosles.Screens.profile


open class BaseActivity(var tile: String ="Рослес"): AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //инциализация навигации
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        val view: View = supportActionBar!!.customView
        val back=view.findViewById<ImageView>(R.id.back)
        val menu=view.findViewById<ImageView>(R.id.burger)
        val title=view.findViewById<TextView>(R.id.text)
        title.setText(tile)
        back.setOnClickListener{
            finish()
        }
        menu.setOnClickListener{
            showpopupmenu(it)
        }
    }

    fun showpopupmenu (view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.main -> {
                    startActivity(Intent(this, StartScreen::class.java))
                }

                R.id.itemperechet -> {
                    startActivity(Intent(this, Molodnyak::class.java))
                }

                R.id.itemgps -> {
                    startActivity(Intent(this, gps_activity::class.java))
                }

                R.id.profile -> {
                    startActivity(Intent(this, profile::class.java))
                }
            }
            true
        }
        popup.show()

    }
}