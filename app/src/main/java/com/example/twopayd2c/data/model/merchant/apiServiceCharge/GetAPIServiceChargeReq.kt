package com.example.twopayd2c.data.model.merchant.apiServiceCharge

import com.google.gson.annotations.SerializedName


data class GetAPIServiceChargeReq (

  @SerializedName("APIName"     ) var APIName     : String? = null,
  @SerializedName("Category"    ) var Category    : String? = null,
  @SerializedName("Code"        ) var Code        : String? = null,
  @SerializedName("CompanyCode" ) var CompanyCode : String? = null

)