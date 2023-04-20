package com.example.rosles.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.RequestClass.SubjectRF
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.databinding.ItemUdelBinding
import com.example.roslesdef.Models.ItemWood

class ChoiceSubjectAdapter(private val names: List<BaseRespObject>, val inter:BaseInterface) : RecyclerView
.Adapter<ChoiceSubjectAdapter.ViewHolder>() {

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
                inter.onClick(names[position].id)
            }
        }
    }
    override fun getItemCount() = names.size
}


