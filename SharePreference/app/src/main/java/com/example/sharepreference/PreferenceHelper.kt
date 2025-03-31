package com.example.sharepreference

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUserData(username: String, password: String) {
        sharedPreferences.edit().apply {
            putString("USERNAME", username)
            putString("PASSWORD", password)
            apply() // Hoặc dùng commit() nếu cần lưu ngay lập tức
        }
    }

    fun getUserData(): Pair<String?, String?> {
        val username = sharedPreferences.getString("USERNAME", null)
        val password = sharedPreferences.getString("PASSWORD", null)
        return Pair(username, password)
    }

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }
}
