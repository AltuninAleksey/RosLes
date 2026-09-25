package com.example.rosles.Models

/**
 * Имена по цепочке: участковое лесничество -> лесничество -> субъект РФ.
 * Поля nullable: справочник верхнего уровня может быть не докачан локально.
 */
data class ForestryChain(
    val districtName: String?,
    val forestlyName: String?,
    val subjectName: String?
)
