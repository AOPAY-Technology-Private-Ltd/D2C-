package com.example.twopayd2c.data.model.travelmodel.bus.city

import com.google.gson.annotations.SerializedName


data class CityDetails (

  @SerializedName("cityID"   ) var cityID   : Int?    = null,
  @SerializedName("cityName" ) var cityName : String? = null

)