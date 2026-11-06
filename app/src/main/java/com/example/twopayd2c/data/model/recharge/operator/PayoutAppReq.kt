package com.example.twopayd2c.data.model.recharge.operator

import com.google.gson.annotations.SerializedName


data class PayoutAppReq (

  @SerializedName("registrationID" ) var registrationID : String? = null,
  @SerializedName("accountName"    ) var accountName    : String? = null,
  @SerializedName("accountNumber"  ) var accountNumber  : String? = null,
  @SerializedName("ifscCode"       ) var ifscCode       : String? = null,
  @SerializedName("amount"         ) var amount         : Int?    = null,
  @SerializedName("txnNote"        ) var txnNote        : String? = null

)