package com.example.twopayd2c.data.model.recharge.plan

import com.google.gson.annotations.SerializedName


data class MobileBrowserPlanReq (

  @SerializedName("RegisterID" ) var RegisterID : String? = null,
  @SerializedName("circle"     ) var circle     : String? = null,
  @SerializedName("op"         ) var op         : String? = null

)