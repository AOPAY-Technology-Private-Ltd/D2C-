package com.example.twopayd2c.data.model.recharge.recharge

import com.google.gson.annotations.SerializedName


data class MobileRechargeReq (

  @SerializedName("operator"    ) var operator    : String? = null,
  @SerializedName("canumber"    ) var canumber    : String? = null,
  @SerializedName("amount"      ) var amount      : String? = null,
  @SerializedName("referenceid" ) var referenceid : String? = null,
  @SerializedName("RegisterID"  ) var RegisterID  : String? = null

)