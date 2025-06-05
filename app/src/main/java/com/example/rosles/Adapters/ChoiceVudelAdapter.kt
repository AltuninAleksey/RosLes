package com.example.rosles.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.Models.Poroda
import com.example.rosles.R
import com.example.rosles.RequestClass.SubjectRF
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.databinding.ItemUdelBinding
import com.example.rosles.databinding.VedomostitemBinding
import com.example.roslesdef.Models.ItemWood

class ChoiceVudelAdapter() : BaseAdapter<Poroda, VedomostitemBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = VedomostitemBinding.inflate(inflater, parent, false)


    var choise_second_item: View? = null
    var listener: ((data: Poroda) -> Unit)? = null

    override fun bindViewHolder(holder: ViewBindingHolder, data: Poroda) {

        holder.binding {


            itemBlock.setOnClickListener {
                if (choise_second_item != null) {
                    choise_second_item!!.setBackgroundResource(R.color.color_background)
                }
                choise_second_item = it
                itemBlock.setBackgroundResource(R.color.activecolumn)
                if (expandableLayout0.isExpanded) {
                    expandableLayout0.collapse()

                } else {
                    expandableLayout0.expand()
                }
                listener?.invoke(items[holder.adapterPosition])


            }
            data.apply {
                numberLesnich.setText(data.id.substringBefore('-'))
                nameLesnich.setText(data.nameForestly)
                districtForestly.setText(data.nameDistrictForestly)
                quter.setText(data.quarterName)
                vudel.setText(data.soilLot)
                dateTime.setText(data.date)
                squareVudel.setText(data.square)
                tract.setText(data.dacha)
                if (data.markUpdate > 0) {
                    markUpdateItem.visibility = View.VISIBLE
                } else {
                    markUpdateItem.visibility = View.GONE
                }

            }
        }


    }
}






