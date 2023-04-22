package com.example.rosles.Models

import com.example.roslesdef.Models.ItemWood

data class FavoriteLes(
    val nameBreed: String,
    val id: String
) : Modeldb, ToItemWood {

    override fun toItemWood(): ItemWood {
        return  ItemWood(nameBreed, id.toInt())
    }
}
