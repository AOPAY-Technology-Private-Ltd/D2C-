package com.example.twopayd2c.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.twopayd2c.data.model.travelmodel.RecentSearchListModel
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.FutureUseDataListModel
import com.example.twopayd2c.data.model.travelmodel.flight.FlightPassengerDataModel
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.data.model.travelmodel.flight.TripDetailsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreference(mContext: Context) {

    private val preferences: SharedPreferences = mContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mMStash: SharedPreference? = null


        @Synchronized
        fun getInstance(context: Context): SharedPreference? {
            if (mMStash == null) {
                mMStash = SharedPreference(context.applicationContext)
            }
            return mMStash
        }

    }



    fun setStringValue(key: String?, value: String?) {
        preferences.edit()!!.putString(key, value).commit()
    }


    fun getStringValue(key: String?, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    fun setBooleanValue(keyFlag: String?, value: Boolean) {
        preferences.edit().putBoolean(keyFlag, value).apply()
    }


    fun getBoolanValue(keyFlag: String?, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(keyFlag, defaultValue)
    }

    fun savelist(key: String?, list: List<RecentSearchListModel?>?){
        val gson = com.google.gson.Gson()
        val json = gson.toJson(list)
        preferences.edit().putString(key, json).apply()
    }

    fun getList(key: String?): List<RecentSearchListModel> {
        val gson = Gson()
        val json = preferences.getString(key, null)
        val type = object : TypeToken<List<RecentSearchListModel>>() {}.type
        return if (json != null) gson.fromJson(json, type) else emptyList()
    }

    fun saveForFutureData(key:String,list: List<FutureUseDataListModel?>?){
        val gson = com.google.gson.Gson()
        val json = gson.toJson(list)
        preferences.edit().putString(key, json).apply()
    }

    fun getFutureDataList(key: String?): List<FutureUseDataListModel> {
        val gson = Gson()
        val json = preferences.getString(key, null)
        val type = object : TypeToken<List<FutureUseDataListModel>>() {}.type
        return if (json != null) gson.fromJson(json, type) else emptyList()
    }

    fun setFlightSearchData(key:String, list: List<TripDetailsItem>?, context:Context){
        val editor = preferences.edit()
        val json = Gson().toJson(list)
        preferences.edit().putString(key, json).apply()
    }

    fun saveFlightFilterlist(key: String?, list: List<FlightsItem?>?){
        val gson = com.google.gson.Gson()
        val json = gson.toJson(list)
        preferences.edit().putString(key, json).apply()
    }

    fun getFlightFilterList(key: String?): List<FlightsItem> {
        val gson = Gson()
        val json = preferences.getString(key, null)
        val type = object : TypeToken<List<FlightsItem>>() {}.type
        return if (json != null) gson.fromJson(json, type) else emptyList()
    }

    fun saveForFutureFlightData(key:String,list: List<FlightPassengerDataModel?>?){
        val gson = com.google.gson.Gson()
        val json = gson.toJson(list)
        preferences.edit().putString(key, json).apply()
    }

    fun getFutureFlightDataList(key: String?): List<FlightPassengerDataModel> {
        val gson = Gson()
        val json = preferences.getString(key, null)
        val type = object : TypeToken<List<FlightPassengerDataModel>>() {}.type
        return if (json != null) gson.fromJson(json, type) else emptyList()
    }

}