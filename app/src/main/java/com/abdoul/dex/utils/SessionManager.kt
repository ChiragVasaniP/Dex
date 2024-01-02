package com.abdoul.dex.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import android.preference.PreferenceManager
import com.abdoul.dex.models.loginModel.LoginData

class SessionManager(context: Context?) {
    private val mPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var mEditor: SharedPreferences.Editor = mPrefs.edit()
    fun setStringData(key: String?, `val`: String?) {
        mEditor.putString(key, `val`)
        mEditor.commit()
    }

    fun getStringData(key: String?): String? {
        return mPrefs.getString(key, "")
    }

    fun setFloatData(key: String?, `val`: Float) {
        mEditor.putFloat(key, `val`)
        mEditor.commit()
    }

    fun getFloatData(key: String?): Float {
        return mPrefs.getFloat(key, 0f)
    }

    fun setBooleanData(key: String?, `val`: Boolean?) {
        mEditor.putBoolean(key, `val`!!)
        mEditor.commit()
    }

    fun getBooleanData(key: String?): Boolean {
        return mPrefs.getBoolean(key, false)
    }

    fun setIntData(key: String?, `val`: Int) {
        mEditor.putInt(key, `val`)
        mEditor.commit()
    }

    fun getIntData(key: String?): Int {
        return mPrefs.getInt(key, 0)
    }

    fun setUserDetails(key: String?, `val`: LoginData?) {
        mEditor.putString(key, Gson().toJson(`val`))
        mEditor.commit()
    }

    fun getUserDetails(key: String?): LoginData {
        return Gson().fromJson(mPrefs.getString(key, ""), LoginData::class.java)
    }

    fun logoutUser() {
        mEditor.clear()
        mEditor.commit()
    }

    companion object {
        @JvmField
        var login = "login"
        var isopen = "isopen"
        var user = "users"
        @JvmField
        var dcharge = "dcharge"
        @JvmField
        var address = "address"
        @JvmField
        var wallet = "wallet"
        @JvmField
        var pincode = "pincode"
        @JvmField
        var contact = "contact"
        @JvmField
        var api_token = "api_token"

    }

}