package com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent

import com.google.gson.annotations.SerializedName


data class TransferAmountToAgentsWithCalculationRes (

  @SerializedName("Status"  ) var Status  : Boolean? = null,
  @SerializedName("message" ) var message : String? = null,
  @SerializedName("Value"   ) var Value   : String? = null

)