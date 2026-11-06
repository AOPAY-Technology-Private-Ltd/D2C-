package com.example.twopayd2c.data.model.recharge.payout

import com.google.gson.annotations.SerializedName


data class PayoutAmountReq(

  @SerializedName("registrationID"         ) var registrationID: String? = null,
  @SerializedName("upiID"                  ) var upiID: String? = null,
  @SerializedName("amount"                 ) var amount: String? = null

)