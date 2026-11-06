package com.example.twopayd2c.data.model.merchant.apiServiceCharge

import com.google.gson.annotations.SerializedName


data class GetPayoutCommercialReq (
  @SerializedName("merchant"      ) var merchant      : String? = null,
  @SerializedName("productId"     ) var productId     : String? = null,
  @SerializedName("modeofPayment" ) var modeofPayment : String? = null

)