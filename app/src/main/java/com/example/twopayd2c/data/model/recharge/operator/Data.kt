package com.example.twopayd2c.data.model.recharge.operator

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"       ) var id       : String? = null,
  @SerializedName("name"     ) var name     : String? = null,
  @SerializedName("category" ) var category : String? = null

)