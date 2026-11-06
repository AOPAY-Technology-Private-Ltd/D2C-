package com.example.twopayd2c.data.model.recharge.plan

import com.google.gson.annotations.SerializedName


data class COMBO (

  @SerializedName("rs"          ) var rs         : String? = null,
  @SerializedName("desc"        ) var desc       : String? = null,
  @SerializedName("validity"    ) var validity   : String? = null,
  @SerializedName("last_update" ) var lastUpdate : String? = null

)