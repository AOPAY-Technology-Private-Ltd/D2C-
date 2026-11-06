package com.example.twopayd2c.data.model.recharge.mobile

import com.google.gson.annotations.SerializedName


data class MobileCheckReq (

  @SerializedName("RegisterID" ) var RegisterID : String? = null,
  @SerializedName("number"     ) var number     : String? = null,
  @SerializedName("type"       ) var type       : String? = null

)