package com.example.twopayd2c.data.model.recharge

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"          ) var id          : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("category"    ) var category    : String? = null,
  @SerializedName("viewbill"    ) var viewbill    : String? = null,
  @SerializedName("regex"       ) var regex       : String? = null,
  @SerializedName("displayname" ) var displayname : String? = null,
  @SerializedName("ad1_d_name"  ) var ad1DName    : String? = null,
  @SerializedName("ad1_name"    ) var ad1Name     : String? = null,
  @SerializedName("ad1_regex"   ) var ad1Regex    : String? = null,
  @SerializedName("ad2_d_name"  ) var ad2DName    : String? = null,
  @SerializedName("ad2_name"    ) var ad2Name     : String? = null,
  @SerializedName("ad2_regex"   ) var ad2Regex    : String? = null,
  @SerializedName("ad3_d_name"  ) var ad3DName    : String? = null,
  @SerializedName("ad3_name"    ) var ad3Name     : String? = null,
  @SerializedName("ad3_regex"   ) var ad3Regex    : String? = null

)