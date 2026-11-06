package com.example.twopayd2c.data.model.recharge.status

import com.google.gson.annotations.SerializedName


data class RechargeStatusRes (

  @SerializedName("response_code" ) var responseCode : Int?     = null,
  @SerializedName("status"        ) var status       : Boolean? = null,
  @SerializedName("data"          ) var data         : Data?    = Data(),
  @SerializedName("message"       ) var message      : String?  = null

)