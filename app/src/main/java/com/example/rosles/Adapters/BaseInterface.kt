package com.example.rosles.Adapters

import android.view.View
import android.widget.TextView
import com.example.rosles.databinding.ItemUdelBinding

interface BaseInterface {
    fun onClick(itemView: Any)
    fun onClickButton(itemView: TextView): Unit
}