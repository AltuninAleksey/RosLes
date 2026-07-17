package com.example.rosles.Screens

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.Adapters.DualListItemAdapter
import com.example.rosles.Adapters.GridRowItem
import com.example.rosles.databinding.ActivityDualListBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DualListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDualListBinding
    private val leftAdapter = DualListItemAdapter()
    private val rightAdapter = DualListItemAdapter()
    private var isLoadingLeft = false
    private var isLoadingRight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDualListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupMetadata()
        setupLeftRecyclerView()
        setupRightRecyclerView()
        loadInitialData()
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener { finish() }
        binding.btnSave.setOnClickListener {
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupMetadata() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        binding.tvDate.text = "Дата: ${dateFormat.format(Date())}"

        val breeds = arrayOf(
            "Сосна", "Ель", "Береза", "Осина",
            "Дуб", "Липа", "Ольха", "Кедр"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, breeds)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBreed.adapter = adapter
    }

    private fun setupLeftRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvLeft.layoutManager = layoutManager
        binding.rvLeft.adapter = leftAdapter
        binding.rvLeft.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val total = leftAdapter.itemCount
                if (lastVisible >= total - 2 && total > 2 && !isLoadingLeft) {
                    loadMoreItemsLeft()
                }
            }
        })
    }

    private fun setupRightRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRight.layoutManager = layoutManager
        binding.rvRight.adapter = rightAdapter
        binding.rvRight.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val total = rightAdapter.itemCount
                if (lastVisible >= total - 2 && total > 2 && !isLoadingRight) {
                    loadMoreItemsRight()
                }
            }
        })
    }

    private fun loadInitialData() {
        val leftItems = (1..15).map { GridRowItem(it) }
        val rightItems = (1..15).map { GridRowItem(it + 100) }
        leftAdapter.addItems(leftItems)
        rightAdapter.addItems(rightItems)
    }

    private fun loadMoreItemsLeft() {
        isLoadingLeft = true
        lifecycleScope.launch {
            delay(600)
            val startId = leftAdapter.itemCount + 1
            val newItems = (startId until startId + 5).map { GridRowItem(it) }
            leftAdapter.addItems(newItems)
            isLoadingLeft = false
        }
    }

    private fun loadMoreItemsRight() {
        isLoadingRight = true
        lifecycleScope.launch {
            delay(600)
            val startId = rightAdapter.itemCount + 101
            val newItems = (startId until startId + 5).map { GridRowItem(it) }
            rightAdapter.addItems(newItems)
            isLoadingRight = false
        }
    }
}
