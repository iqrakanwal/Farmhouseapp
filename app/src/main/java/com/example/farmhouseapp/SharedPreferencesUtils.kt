package com.example.farmhouseapp

import android.content.Context
import android.content.SharedPreferences


object SharedPreferencesUtils {

    fun getFirstTime(mContext: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("firstrime", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirsttime", true)
    }


    fun getFirstName(mContext: Context): String? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("firstname", Context.MODE_PRIVATE)
        return sharedPreferences.getString("name", "")
    }

    fun setFirstName(mContext: Context, mValue: String?) {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences("firstname", Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("name", mValue)
        sharedPreferencesEditor.apply()
    }

    fun getFarmName(mContext: Context): String? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("farmname", Context.MODE_PRIVATE)
        return sharedPreferences.getString("farm", "")
    }


    fun setFarmName(mContext: Context, mValue: String) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("farmname", Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("farm", mValue)
        sharedPreferencesEditor.apply()
    }

    fun setUserRole(mContext: Context, mValue: String) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("userrole", Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("role", mValue)
        sharedPreferencesEditor.apply()
    }

    fun setUserEmail(mContext: Context, mValue: String) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("useremail", Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("email", mValue)
        sharedPreferencesEditor.apply()
    }

    fun getUserEmail(mContext: Context): String? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("useremail", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", "")
    }

    fun getUserRole(mContext: Context): String? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("userrole", Context.MODE_PRIVATE)
        return sharedPreferences.getString("role", "")
    }
    fun getUserPhone(mContext: Context): String? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("userphonenum", Context.MODE_PRIVATE)
        return sharedPreferences.getString("phone", "")
    }


    fun getUserUid(mContext: Context): String? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("UUID", Context.MODE_PRIVATE)
        return sharedPreferences.getString("uid", "")
    }



    fun getOwner(mContext: Context): String? {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("ownername", Context.MODE_PRIVATE)
        return sharedPreferences.getString("owner", "")
    }

    fun setOwner(mContext: Context, mValue: String) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("ownername", Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("owner", mValue)
        sharedPreferencesEditor.apply()
    }

    fun setUserPhone(mContext: Context, mValue: String) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("userphonenum", Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("phone", mValue)
        sharedPreferencesEditor.apply()
    }
    fun setUUid(mContext: Context, mValue: String) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences("UUID", Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("uid", mValue)
        sharedPreferencesEditor.apply()
    }


}