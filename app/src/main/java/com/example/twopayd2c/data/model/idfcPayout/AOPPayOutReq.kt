package com.example.twopayd2c.data.model.idfcPayout

import com.google.gson.annotations.SerializedName


data class AOPPayOutReq (

  @SerializedName("accountNumber"   ) var accountNumber   : String? = null,
  @SerializedName("amount"          ) var amount          : String? = null,
  @SerializedName("transactionType" ) var transactionType : String? = null,
  @SerializedName("beneficiaryIFSC" ) var beneficiaryIFSC : String? = null,
  @SerializedName("beneficiaryName" ) var beneficiaryName : String? = null,
  @SerializedName("emailID"         ) var emailID         : String? = null,
  @SerializedName("mobileNo"        ) var mobileNo        : String? = null,
  @SerializedName("registrationID"  ) var registrationID  : String? = null

)