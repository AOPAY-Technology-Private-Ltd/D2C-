package com.example.twopayd2c.data.model.recharge.status

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("txnid"        ) var txnid        : String? = null,
  @SerializedName("operatorname" ) var operatorname : String? = null,
  @SerializedName("canumber"     ) var canumber     : String? = null,
  @SerializedName("amount"       ) var amount       : String? = null,
  @SerializedName("comm"         ) var comm         : String? = null,
  @SerializedName("tds"          ) var tds          : String? = null,
  @SerializedName("status"       ) var status       : String? = null,
  @SerializedName("refid"        ) var refid        : String? = null,
  @SerializedName("operatorid"   ) var operatorid   : String? = null,
  @SerializedName("dateadded"    ) var dateadded    : String? = null

)