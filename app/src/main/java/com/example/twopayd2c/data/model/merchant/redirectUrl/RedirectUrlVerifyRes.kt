package com.example.twopayd2c.data.model.merchant.redirectUrl

import com.google.gson.annotations.SerializedName


data class RedirectUrlVerifyRes (

  @SerializedName("CompanyCode" ) var CompanyCode : String? = null,
  @SerializedName("Status"      ) var Status      : Boolean? = null,
  @SerializedName("message"     ) var message     : String? = null,
  @SerializedName("Value"       ) var Value       : String? = null

)