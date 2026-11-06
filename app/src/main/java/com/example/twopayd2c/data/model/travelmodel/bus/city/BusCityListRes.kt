package com.example.twopayd2c.data.model.travelmodel.bus.city

import com.google.gson.annotations.SerializedName


data class BusCityListRes (

    @SerializedName("cityDetails"     ) var cityDetails    : ArrayList<CityDetails> = arrayListOf(),
    @SerializedName("response_Header" ) var responseHeader : ResponseHeader?        = ResponseHeader(),
    @SerializedName("statuss"         ) var statuss        : String?                = null,
    @SerializedName("message"         ) var message        : String?                = null,
    @SerializedName("value"           ) var value          : String?                = null

)