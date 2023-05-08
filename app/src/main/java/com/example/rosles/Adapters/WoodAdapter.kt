package com.example.roslesdef.Adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.Adapters.BaseInterface
import com.example.roslesdef.Models.ItemWood
import com.example.rosles.R
import com.example.rosles.databinding.ItemWoodBinding

class WoodAdapter(private val names: List<ItemWood>,val inter:BaseInterface) : RecyclerView.Adapter<WoodAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemWoodBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WoodAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWoodBinding.inflate(inflater,parent,false)
        return WoodAdapter.ViewHolder(binding)
    }
    private var activebutton:TextView?=null
    private var favoritebutton:TextView?=null

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: WoodAdapter.ViewHolder, position: Int) {
        with(holder.binding){
            itemWood.text=names[position].name
            itemWood.setOnClickListener{

                if (activebutton!=favoritebutton){
                    activebutton?.setBackgroundResource(R.drawable.rounded)
                    activebutton?.setTextColor(Color.parseColor("#177164"))
                }
                itemWood.setBackgroundResource(R.drawable.rounded_active_button)
                itemWood.setTextColor(Color.parseColor("#ffffff"))
                activebutton=itemWood

                if(favoritebutton==activebutton){
                    itemWood.setBackgroundResource(R.drawable.rounded_red)
                }

                inter.onClick(itemWood.text)
            }
            itemWood.setOnLongClickListener{
                favoritebutton?.setBackgroundResource(R.drawable.rounded)
                favoritebutton?.setTextColor(Color.parseColor("#177164"))
                itemWood.setBackgroundResource(R.drawable.rounded_red)
                itemWood.setTextColor(Color.parseColor("#ffffff"))
                favoritebutton=itemWood
                inter.onClickButton(itemWood.text)
                return@setOnLongClickListener true

            }
        }


    }
    override fun getItemCount() = names.size
}