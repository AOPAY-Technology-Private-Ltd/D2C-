package com.example.twopayd2c.data.model.recharge.mobile

import com.google.gson.annotations.SerializedName


data class MobileCheckRes(

  @SerializedName("response_code" ) var responseCode: Int?     = null,
  @SerializedName("status"        ) var status:       Boolean? = null,
  @SerializedName("info"          ) var info: Info?    = Info(),
  @SerializedName("message"       ) var message: String?  = null

)