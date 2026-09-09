package com.example.rosles.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.R
import com.example.rosles.Screens.PerechetVedomostList
import com.example.rosles.databinding.ItemVedomostBinding

class VedomostListAdapter(
    private val rows: List<PerechetVedomostList.VedomostRow>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<VedomostListAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class ViewHolder(val binding: ItemVedomostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemVedomostBinding.inflate(inflater, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = rows[position]
        with(holder.binding) {
            vedomostTitle.text = "${row.forestry} · ${row.district} · ${row.tract}"
            vedomostSubtitle.text =
                "кв. ${row.quarter} · выдел ${row.allotment} · ${row.square} га · ${row.date}"
            root.setBackgroundResource(
                if (position == selectedPosition) R.color.activecolumn else R.color.color_transporent
            )
            root.setOnClickListener {
                val current = holder.adapterPosition
                if (current == RecyclerView.NO_POSITION) return@setOnClickListener
                val previous = selectedPosition
                selectedPosition = current
                if (previous != RecyclerView.NO_POSITION) notifyItemChanged(previous)
                notifyItemChanged(selectedPosition)
                onClick(selectedPosition)
            }
        }
    }

    override fun getItemCount() = rows.size
}
