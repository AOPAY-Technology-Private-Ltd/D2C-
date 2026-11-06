package com.example.twopayd2c.data.model.travelmodel

import com.google.gson.annotations.SerializedName

data class PopularCityNameListModel(
    @field:SerializedName("cityname")
    val tocity: String? = null,

    @field:SerializedName("image")
    val image: Int ,

    @SerializedName("cityID"   )
    var cityID   : Int?    = null,

)
