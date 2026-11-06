package com.example.twopayd2c.data.model.travelmodel.bus.searchBus

import com.google.gson.annotations.SerializedName


data class BusSearchReq (

  @SerializedName("from_City"      ) var fromCity       : String? = null,
  @SerializedName("to_City"        ) var toCity         : String? = null,
  @SerializedName("travelDate"     ) var travelDate     : String? = null,
  @SerializedName("iP_Address"     ) var iPAddress      : String? = null,
  @SerializedName("request_Id"     ) var requestId      : String? = null,
  @SerializedName("imeI_Number"    ) var imeINumber     : String? = null,
  @SerializedName("registrationID" ) var registrationID : String? = null

)