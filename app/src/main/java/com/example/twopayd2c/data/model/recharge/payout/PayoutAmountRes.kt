package com.example.twopayd2c.data.model.recharge.payout

import com.google.gson.annotations.SerializedName


data class PayoutAmountRes (

  @SerializedName("data"    ) var data    : ArrayList<Data>? = null,
  @SerializedName("status"  ) var status  : Int?            = null,
  @SerializedName("statuss" ) var statuss : Boolean?         = null,
  @SerializedName("message" ) var message : String?         = null,
  @SerializedName("value"   ) var value   : String?         = null

)