package com.example.rosles.Models

import com.example.roslesdef.Models.ItemWood

data class FavoritePodles(
    val name: String,
    val id: String,
    val idUndergrowthId: String
) : Modeldb, ToItemWood {

    override fun toItemWood(): ItemWood {
        return  ItemWood(name, idUndergrowthId.toInt())
    }
}
