package com.example.roslesdef.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.RequestClass.SubjectRF
import com.example.roslesdef.Models.ItemWood
import com.example.rosles.databinding.ItemUdelBinding



class UdelAdapter(private val names: List<ItemWood>, val inter:BaseInterface) : RecyclerView
.Adapter<UdelAdapter.ViewHolder>() {


    private var stateitem: ViewHolder? =null
    class ViewHolder(
        val binding: ItemUdelBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUdelBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            textUdelItem.text=names[position].name
            root.setOnClickListener{
                stateitem?.binding?.textButton?.isVisible=false
                stateitem?.binding?.delete?.isVisible=false
                textButton.isVisible=true
                delete.isVisible=true
                stateitem=holder
            }
            textButton.setOnClickListener{
                inter.onClick(holder.binding.textUdelItem.text)
            }
        }


    }



    override fun getItemCount() = names.size
}