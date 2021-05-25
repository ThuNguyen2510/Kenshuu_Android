package com.example.kenshuu.utils

import android.util.Log
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import com.pixplicity.easyprefs.library.Prefs

class PrefsManager {


    fun getUserName(): String? =
        if (Prefs.getString("username",null) != null) {
            Prefs.getString("username",null)
        } else null

    fun getToken() : String? =
        if(Prefs.getString("token",null)!=null){
            Prefs.getString("token",null)
        }else null
    fun getPass() :String?=
        if(Prefs.getString("password",null)!=null){
            Prefs.getString("password",null)
        }else null
    fun getUserId() :String?=
        if(Prefs.getString("userId",null)!=null){
            Prefs.getString("userId",null)
        }else null
    fun saveUser(user: User) {
        Prefs.putString("username", user.familyName)
        Prefs.putString("token",user.token)
    }
    fun savePass(pass: String){
        Prefs.putString("password",pass)
    }
    fun saveUserId(pass: String){
        Prefs.putString("userId",pass)
    }

    fun clearUser() {
        Prefs.putString("username", null)
        Prefs.putString("token",null)
        Prefs.putString("userId",null)
        Prefs.putString("password",null)
    }
}