package com.example.twopayd2c.data.model.travelmodel.bus.searchBus

import com.google.gson.annotations.SerializedName


data class FareDetails (

  @SerializedName("amount"     ) var amount     : Double?     = null,
  @SerializedName("fare_Desc"  ) var fareDesc   : String?  = null,
  @SerializedName("refundable" ) var refundable : Boolean? = null

)