package com.example.twopayd2c.data.model.idfcPayout

import com.google.gson.annotations.SerializedName


data class ResourceData (

  @SerializedName("status"                 ) var status                 : String? = null,
  @SerializedName("transactionReferenceNo" ) var transactionReferenceNo : String? = null,
  @SerializedName("transactionID"          ) var transactionID          : String? = null,
  @SerializedName("beneficiaryName"        ) var beneficiaryName        : String? = null

)