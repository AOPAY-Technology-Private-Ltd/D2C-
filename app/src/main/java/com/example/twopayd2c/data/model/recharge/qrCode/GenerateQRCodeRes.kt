package com.example.twopayd2c.data.model.recharge.qrCode

import com.google.gson.annotations.SerializedName


data class GenerateQRCodeRes (

  @SerializedName("status_code"    ) var statusCode     : Int?     = null,
  @SerializedName("responsecode"   ) var responsecode   : Int?     = null,
  @SerializedName("status"         ) var status         : Boolean? = null,
  @SerializedName("message"        ) var message        : String?  = null,
  @SerializedName("details"        ) var details        : Details? = Details(),
  @SerializedName("txnRefranceNo"  ) var txnRefranceNo  : String?  = null,
  @SerializedName("RegistrationID" ) var RegistrationID : String?  = null

)