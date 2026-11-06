package com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent

import com.google.gson.annotations.SerializedName


data class TransferAmountToAgentsWithCalculationReq (

  @SerializedName("Agentid"         ) var Agentid         : String? = null,
  @SerializedName("APIName"         ) var APIName         : String? = null,
  @SerializedName("Category"        ) var Category        : String? = null,
  @SerializedName("OperatorCode"    ) var OperatorCode    : String? = null,
  @SerializedName("Amount"          ) var Amount          : Int?    = null,
  @SerializedName("CompanyCode"     ) var CompanyCode     : String? = null,
  @SerializedName("TransIpAddress"  ) var TransIpAddress  : String? = null,
  @SerializedName("Amount_Type"     ) var AmountType      : String? = null,
  @SerializedName("Remark"          ) var Remark          : String? = null,
  @SerializedName("TransactionDate" ) var TransactionDate : String? = null,
  @SerializedName("MobileNo"        ) var MobileNo        : String? = null

)