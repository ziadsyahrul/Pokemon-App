package com.ziad.pokeappcompose.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.ziad.pokeappcompose.utils.Constant
import javax.inject.Inject

class SessionPreferenceManager @Inject constructor(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("session_pref", Context.MODE_PRIVATE)

    fun saveUserLogin(userId: Int, username: String) {
        sharedPref.edit {
            putBoolean(Constant.IS_LOGGED_IN, true)
                .putInt(Constant.USER_ID, userId)
                .putString(Constant.USERNAME, username)
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(Constant.IS_LOGGED_IN, false)
    }

    fun getUsername(): String? = sharedPref.getString(Constant.USERNAME, null)

    fun logout() {
        sharedPref.edit {
            clear()
        }
    }

}