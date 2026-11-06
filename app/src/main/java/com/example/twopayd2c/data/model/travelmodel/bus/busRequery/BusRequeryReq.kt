package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class BusRequeryReq (

  @SerializedName("booking_RefNo"  ) var bookingRefNo   : String? = null,
  @SerializedName("iP_Address"     ) var iPAddress      : String? = null,
  @SerializedName("request_Id"     ) var requestId      : String? = null,
  @SerializedName("imeI_Number"    ) var imeINumber     : String? = null,
  @SerializedName("registrationID" ) var registrationID : String? = null

)