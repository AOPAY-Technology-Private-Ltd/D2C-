package com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal

import com.google.gson.annotations.SerializedName


data class GetMerchantBalanceReq (

  @SerializedName("parmUser" ) var parmUser : String? = null,
  @SerializedName("flag"     ) var flag     : String? = null

)