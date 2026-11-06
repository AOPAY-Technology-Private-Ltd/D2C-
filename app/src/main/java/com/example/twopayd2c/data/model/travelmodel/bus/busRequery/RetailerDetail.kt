package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class RetailerDetail (

  @SerializedName("email_Id"             ) var emailId             : String? = null,
  @SerializedName("mobile_Number"        ) var mobileNumber        : String? = null,
  @SerializedName("operator_Name"        ) var operatorName        : String? = null,
  @SerializedName("retailerServiceFee"   ) var retailerServiceFee  : Int?    = null,
  @SerializedName("retailer_Address"     ) var retailerAddress     : String? = null,
  @SerializedName("retailer_Area"        ) var retailerArea        : String? = null,
  @SerializedName("retailer_City"        ) var retailerCity        : String? = null,
  @SerializedName("retailer_CompanyName" ) var retailerCompanyName : String? = null,
  @SerializedName("retailer_Email"       ) var retailerEmail       : String? = null,
  @SerializedName("retailer_GSTno"       ) var retailerGSTno       : String? = null,
  @SerializedName("retailer_Id"          ) var retailerId          : String? = null,
  @SerializedName("retailer_Landmark"    ) var retailerLandmark    : String? = null,
  @SerializedName("retailer_Name"        ) var retailerName        : String? = null,
  @SerializedName("retailer_PANno"       ) var retailerPANno       : String? = null,
  @SerializedName("retailer_Pincode"     ) var retailerPincode     : String? = null,
  @SerializedName("retailer_State"       ) var retailerState       : String? = null

)