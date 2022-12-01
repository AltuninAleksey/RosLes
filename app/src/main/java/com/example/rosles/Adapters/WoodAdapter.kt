package com.example.roslesdef.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    override fun onBindViewHolder(holder: WoodAdapter.ViewHolder, position: Int) {
        with(holder.binding){
            itemWood.text=names[position].name
            itemWood.setOnClickListener{
                activebutton?.setBackgroundResource(R.drawable.rounded_active)
                itemWood.setBackgroundResource(R.drawable.rounded_active_button)
                activebutton=itemWood
                inter.onClick(itemWood.text)
            }

        }


    }



    override fun getItemCount() = names.size
}