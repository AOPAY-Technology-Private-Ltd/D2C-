package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class CustomerDetail (

  @SerializedName("customer_Address" ) var customerAddress : String? = null,
  @SerializedName("customer_City"    ) var customerCity    : String? = null,
  @SerializedName("customer_Email"   ) var customerEmail   : String? = null,
  @SerializedName("customer_Id"      ) var customerId      : String? = null,
  @SerializedName("customer_Mobile"  ) var customerMobile  : String? = null,
  @SerializedName("customer_Name"    ) var customerName    : String? = null

)