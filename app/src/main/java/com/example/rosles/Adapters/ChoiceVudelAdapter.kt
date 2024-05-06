package com.example.rosles.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.Models.Poroda
import com.example.rosles.RequestClass.SubjectRF
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.databinding.ItemUdelBinding
import com.example.rosles.databinding.VedomostitemBinding
import com.example.roslesdef.Models.ItemWood

class ChoiceVudelAdapter: BaseAdapter<Poroda, VedomostitemBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = VedomostitemBinding.inflate(inflater, parent, false)



    override fun bindViewHolder(holder: ViewBindingHolder, data: Poroda) {
        holder.binding{

            data.apply {
                numberLesnich.setText(data.id)
                nameLesnich.setText(data.nameForestly)


            }
        }


    }
}






