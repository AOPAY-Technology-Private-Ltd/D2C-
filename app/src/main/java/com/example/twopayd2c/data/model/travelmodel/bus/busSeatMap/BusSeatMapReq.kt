package com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap

import com.google.gson.annotations.SerializedName


data class BusSeatMapReq (

  @SerializedName("boarding_Id"    ) var boardingId     : String? = null,
  @SerializedName("dropping_Id"    ) var droppingId     : String? = null,
  @SerializedName("bus_Key"        ) var busKey         : String? = null,
  @SerializedName("search_Key"     ) var searchKey      : String? = null,
  @SerializedName("iP_Address"     ) var iPAddress      : String? = null,
  @SerializedName("request_Id"     ) var requestId      : String? = null,
  @SerializedName("imeI_Number"    ) var imeINumber     : String? = null,
  @SerializedName("registrationID" ) var registrationID : String? = null

)