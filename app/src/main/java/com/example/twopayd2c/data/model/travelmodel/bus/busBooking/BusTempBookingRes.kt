package com.example.twopayd2c.data.model.travelmodel.bus.busBooking

import com.google.gson.annotations.SerializedName


data class BusTempBookingRes (

  @SerializedName("booking_RefNo"   ) var bookingRefNo   : String?           = null,
  @SerializedName("response_Header" ) var responseHeader : ResponseHeader?   = ResponseHeader(),
  @SerializedName("updatedFares"    ) var updatedFares   : ArrayList<String> = arrayListOf(),
  @SerializedName("statuss"         ) var statuss        : String?           = null,
  @SerializedName("message"         ) var message        : String?           = null,
  @SerializedName("value"           ) var value          : String?           = null

)