package com.example.roslesdef.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.databinding.ItemForestBinding
import com.example.roslesdef.Models.SpinerItem


class ForestAdapter(private var names: List<SpinerItem>) : RecyclerView.Adapter<ForestAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemForestBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForestAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForestBinding.inflate(inflater,parent,false)
        return ForestAdapter.ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ForestAdapter.ViewHolder, position: Int) {

        with(holder.binding){
            name.text=names[position].name
            checkBox.isChecked=names[position].check
            checkBox.setOnClickListener{
                if (checkBox.isChecked){
                    names[position].check=true
                }else{
                    names[position].check=false
                }
            }

        }


    }

    fun getArraydata():List<SpinerItem>{
        return names
    }
    override fun getItemCount() = names.size
}