package com.quantam.it.assignment

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(ctx: Context) {

    private var id: SharedPreferences =
        ctx.getSharedPreferences("APP_PREFRENCES", Context.MODE_PRIVATE)

    fun saveId(userId: String) {
        id.edit().putString("id", userId).apply()
    }

    fun getId(): String? {
        return id.getString("id", "")
    }

    fun saveName(name: String) {
        id.edit().putString("name", name).apply()
    }

    fun getName(): String? {
        return id.getString("name", "")
    }

    fun setFbLoginStatus(isLogin:Boolean) {
        id.edit().putBoolean("LoginStatus", isLogin).apply()
    }

    fun getFbLoginStatus(): Boolean {
        return id.getBoolean("LoginStatus",false)
    }

}