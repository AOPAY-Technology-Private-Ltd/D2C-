package com.example.twopayd2c.data.model.recharge

import com.google.gson.annotations.SerializedName


data class BillOperationPaymentReq (

  @SerializedName("mode"           ) var mode           : String? = null,
  @SerializedName("RegistrationID" ) var RegistrationID : String? = null

)