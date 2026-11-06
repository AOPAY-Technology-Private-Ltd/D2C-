package com.example.twopayd2c.data.model.idfcPayout

import com.google.gson.annotations.SerializedName


data class MetaData (

  @SerializedName("status"  ) var status  : String? = null,
  @SerializedName("message" ) var message : String? = null,
  @SerializedName("version" ) var version : String? = null,
  @SerializedName("time"    ) var time    : String? = null

)