package com.example.twopayd2c.data.model.travelmodel.travel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class BusBookingListReq(
    @SerializedName("loginID" ) var loginID : String? = null,
    @SerializedName("startDate" ) val startDate: String? = null,
    @SerializedName("endDate"   ) val endDate: String? = null,

)
