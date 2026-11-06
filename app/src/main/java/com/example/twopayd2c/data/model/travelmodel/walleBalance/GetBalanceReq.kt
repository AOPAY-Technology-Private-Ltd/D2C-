package com.example.twopayd2c.data.model.travelmodel.walleBalance

import com.google.gson.annotations.SerializedName


data class GetBalanceReq (

  @SerializedName("parmUser" ) var parmUser : String? = null,
  @SerializedName("flag"     ) var flag     : String? = null

)