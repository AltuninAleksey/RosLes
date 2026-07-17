package com.example.rosles.Adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.databinding.ItemDualListCardBinding

data class GridRowItem(
    val id: Int,
    var cell1: String = "",
    var cell2: String = "",
    var cell3: String = ""
)

class DualListItemAdapter(
    private val items: MutableList<GridRowItem> = mutableListOf()
) : RecyclerView.Adapter<DualListItemAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemDualListCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var cell1Watcher: TextWatcher? = null
        var cell2Watcher: TextWatcher? = null
        var cell3Watcher: TextWatcher? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDualListCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val adapterPos = position

        with(holder.binding) {
            // --- Cell 1 ---
            editCell1.removeTextChangedListener(holder.cell1Watcher)
            holder.cell1Watcher = null
            editCell1.setText(item.cell1)
            holder.cell1Watcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (adapterPos < items.size) {
                        items[adapterPos].cell1 = s?.toString() ?: ""
                    }
                }
            }
            editCell1.addTextChangedListener(holder.cell1Watcher)

            // --- Cell 2 ---
            editCell2.removeTextChangedListener(holder.cell2Watcher)
            holder.cell2Watcher = null
            editCell2.setText(item.cell2)
            holder.cell2Watcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (adapterPos < items.size) {
                        items[adapterPos].cell2 = s?.toString() ?: ""
                    }
                }
            }
            editCell2.addTextChangedListener(holder.cell2Watcher)

            // --- Cell 3 ---
            editCell3.removeTextChangedListener(holder.cell3Watcher)
            holder.cell3Watcher = null
            editCell3.setText(item.cell3)
            holder.cell3Watcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (adapterPos < items.size) {
                        items[adapterPos].cell3 = s?.toString() ?: ""
                    }
                }
            }
            editCell3.addTextChangedListener(holder.cell3Watcher)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        with(holder.binding) {
            editCell1.removeTextChangedListener(holder.cell1Watcher)
            editCell2.removeTextChangedListener(holder.cell2Watcher)
            editCell3.removeTextChangedListener(holder.cell3Watcher)
        }
        holder.cell1Watcher = null
        holder.cell2Watcher = null
        holder.cell3Watcher = null
    }

    override fun getItemCount() = items.size

    fun addItems(newItems: List<GridRowItem>) {
        val startPos = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPos, newItems.size)
    }
}
