package com.example.rosles.utils

import android.content.Context
import android.util.Log

fun Context.getToken(): String = try {
    val token = getSharedPreferences("PreferencesName", Context.MODE_PRIVATE).getString("access_token", "") ?: ""
    if (token.isEmpty()) Log.e("TOKEN_ERROR", "Ошибка: пустой токен")
    token
} catch (e: Exception) {
    Log.e("TOKEN_ERROR", "Сбой при получении токена: ${e.message}")
    ""
}
