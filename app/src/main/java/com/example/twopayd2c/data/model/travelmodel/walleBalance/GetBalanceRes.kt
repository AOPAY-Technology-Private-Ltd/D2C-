package com.example.twopayd2c.data.model.travelmodel.walleBalance

import com.example.twopayd2c.data.model.travelmodel.walleBalance.Data
import com.google.gson.annotations.SerializedName


data class GetBalanceRes (

  @SerializedName("isSuccess"     ) var isSuccess     : Boolean?        = null,
  @SerializedName("returnMessage" ) var returnMessage : String?         = null,
  @SerializedName("returnCode"    ) var returnCode    : String?         = null,
  @SerializedName("data"          ) var data          : ArrayList<Data> = arrayListOf()

)