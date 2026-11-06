package com.example.twopayd2c.data.model.recharge.plan

import com.google.gson.annotations.SerializedName


data class MobileBrowserPlanRes(

  @SerializedName("status"        ) var status: Boolean? = null,
  @SerializedName("response_code" ) var responseCode: String? = null,
  @SerializedName("info"          ) var info: Info = Info(),
  @SerializedName("message"       ) var message: String?  = null
)



