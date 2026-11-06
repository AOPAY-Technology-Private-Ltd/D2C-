package com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney

import com.google.gson.annotations.SerializedName


data class BusAddMoneyReq (
  @SerializedName("clientRefNo"     ) var clientRefNo     : String? = null,
  @SerializedName("refNo"           ) var refNo           : String? = null,
  @SerializedName("transactionType" ) var transactionType : Int?    = null,
  @SerializedName("productId"       ) var productId       : String? = null,
  @SerializedName("iP_Address"      ) var iPAddress       : String? = null,
  @SerializedName("request_Id"      ) var requestId       : String? = null,
  @SerializedName("imeI_Number"     ) var imeINumber      : String? = null,
  @SerializedName("registrationID"  ) var registrationID  : String? = null

)