package com.example.twopayd2c.data.model.recharge.payout.payoutStatus

import com.google.gson.annotations.SerializedName


data class PayoutStatusReq (

  @SerializedName("txnRefranceID"  ) var txnRefranceID  : String? = null,
  @SerializedName("registrationID" ) var registrationID : String? = null

)