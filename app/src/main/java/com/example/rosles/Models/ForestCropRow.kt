package com.example.rosles.Models

/**
 * Строка лесных культур для отображения на экране:
 * данные djangoForest_fc_row + имя породы из djangoForest_breed.
 */
data class ForestCropRow(
    val uuid: String?,
    val number: Int?,
    val idBreed: Int?,
    val breedName: String?,
    val countLiving: Int?,
    val countDead: Int?
)
