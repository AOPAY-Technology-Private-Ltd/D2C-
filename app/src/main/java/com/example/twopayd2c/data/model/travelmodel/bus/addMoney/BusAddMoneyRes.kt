package com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney

import com.google.gson.annotations.SerializedName


data class BusAddMoneyRes (

  @SerializedName("amount"          ) var amount         : Double?            = null,
  @SerializedName("debitedAmount"   ) var debitedAmount  : Double?            = null,
  @SerializedName("paymentID"       ) var paymentID      : String?         = null,
  @SerializedName("response_Header" ) var responseHeader : ResponseHeader? = ResponseHeader(),
  @SerializedName("statuss"         ) var statuss        : String?         = null,
  @SerializedName("message"         ) var message        : String?         = null,
  @SerializedName("value"           ) var value          : String?         = null

)