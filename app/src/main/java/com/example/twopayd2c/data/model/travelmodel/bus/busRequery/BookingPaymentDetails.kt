package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class BookingPaymentDetails (

  @SerializedName("currency_Code"              ) var currencyCode              : String? = null,
  @SerializedName("gateway_Charges"            ) var gatewayCharges            : Double?    = null,
  @SerializedName("paymentConfirmation_Number" ) var paymentConfirmationNumber : String? = null,
  @SerializedName("payment_Amount"             ) var paymentAmount             : Double?    = null,
  @SerializedName("payment_Mode"               ) var paymentMode               : Int?    = null

)