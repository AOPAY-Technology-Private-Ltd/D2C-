package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class CompanyDetail (

  @SerializedName("address"       ) var address       : String? = null,
  @SerializedName("city"          ) var city          : String? = null,
  @SerializedName("companyName"   ) var companyName   : String? = null,
  @SerializedName("contactPerson" ) var contactPerson : String? = null,
  @SerializedName("country"       ) var country       : String? = null,
  @SerializedName("email"         ) var email         : String? = null,
  @SerializedName("fax"           ) var fax           : String? = null,
  @SerializedName("gstNo"         ) var gstNo         : String? = null,
  @SerializedName("logo"          ) var logo          : String? = null,
  @SerializedName("mobileNo"      ) var mobileNo      : String? = null,
  @SerializedName("panNo"         ) var panNo         : String? = null,
  @SerializedName("phoneNo"       ) var phoneNo       : String? = null,
  @SerializedName("pincode"       ) var pincode       : String? = null,
  @SerializedName("sacCode"       ) var sacCode       : String? = null,
  @SerializedName("state"         ) var state         : String? = null,
  @SerializedName("website"       ) var website       : String? = null

)