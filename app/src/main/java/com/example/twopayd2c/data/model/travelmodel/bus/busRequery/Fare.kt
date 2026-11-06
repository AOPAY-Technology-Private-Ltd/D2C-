package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class Fare (

  @SerializedName("basic_Amount"         ) var basicAmount         : Int?              = null,
  @SerializedName("cancellation_Charges" ) var cancellationCharges : Int?              = null,
  @SerializedName("fareDetails"          ) var fareDetails         : ArrayList<String> = arrayListOf(),
  @SerializedName("gst"                  ) var gst                 : Int?              = null,
  @SerializedName("gross_Commission"     ) var grossCommission     : Int?              = null,
  @SerializedName("net_Commission"       ) var netCommission       : Int?              = null,
  @SerializedName("other_Amount"         ) var otherAmount         : Double?              = null,
  @SerializedName("service_Fee_Amount"   ) var serviceFeeAmount    : Int?              = null,
  @SerializedName("total_Amount"         ) var totalAmount         : Double?              = null,
  @SerializedName("trade_Markup_Amount"  ) var tradeMarkupAmount   : Int?              = null

)