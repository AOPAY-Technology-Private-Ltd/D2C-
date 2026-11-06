package com.example.twopayd2c.data.model.merchant.clientDetails

import com.google.gson.annotations.SerializedName


data class GetClientRegistrationReq (

  @SerializedName("CompanyCode" ) var CompanyCode : String? = null

)