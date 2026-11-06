package com.example.twopayd2c.data.model.recharge.qrCode

import com.google.gson.annotations.SerializedName


data class Details (

  @SerializedName("payeeVPA"   ) var payeeVPA   : String? = null,
  @SerializedName("merchantId" ) var merchantId : String? = null,
  @SerializedName("UPIRefID"   ) var UPIRefID   : String? = null,
  @SerializedName("txnNote"    ) var txnNote    : String? = null,
  @SerializedName("intent_url" ) var intentUrl  : String? = null

)