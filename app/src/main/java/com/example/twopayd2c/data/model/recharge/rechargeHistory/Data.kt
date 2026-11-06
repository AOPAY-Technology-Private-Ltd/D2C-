package com.example.twopayd2c.data.model.recharge.rechargeHistory

import com.google.gson.annotations.SerializedName

data class Data (

  @SerializedName("rid"                  ) var rid                 : String? = null,
  @SerializedName("date"                 ) var date                : String? = null,
  @SerializedName("time"                 ) var time                : String? = null,
  @SerializedName("caNo"                 ) var caNo                : String? = null,
  @SerializedName("operator"             ) var operator            : String? = null,
  @SerializedName("amount"               ) var amount              : String? = null,
  @SerializedName("tranS_NO"             ) var tranSNO             : String? = null,
  @SerializedName("recharge_ServiceType" ) var rechargeServiceType : String? = null,
  @SerializedName("apI_status"           ) var apIStatus           : String? = null,
  @SerializedName("apI_resText"          ) var apIResText          : String? = null

)