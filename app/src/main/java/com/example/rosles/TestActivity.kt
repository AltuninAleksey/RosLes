package com.example.rosles

import android.os.Bundle
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.Adapters.ChoiceSubjectAdapter
import com.example.rosles.Adapters.ChoiceVudelAdapter
import com.example.rosles.Models.Poroda
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.databinding.TestRecyclerBinding

import com.example.rosles.databinding.VedomostitemBinding

class TestActivity:BaseActivity("test") {
    private lateinit var binding: TestRecyclerBinding
    private lateinit var adapter: ChoiceVudelAdapter
    private val db = DBCountWood(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = ChoiceVudelAdapter().apply {
            setData(db.readbyporoda())
        }

        binding.testRecycler.adapter=adapter

    }
}