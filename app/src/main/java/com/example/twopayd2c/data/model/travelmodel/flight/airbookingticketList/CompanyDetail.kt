package com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList

import com.google.gson.annotations.SerializedName

data class CompanyDetail(@SerializedName("country")
                         val country: String = "",
                         @SerializedName("pincode")
                         val pincode: String = "",
                         @SerializedName("website")
                         val website: String = "",
                         @SerializedName("address")
                         val address: String = "",
                         @SerializedName("city")
                         val city: String = "",
                         @SerializedName("panNo")
                         val panNo: String = "",
                         @SerializedName("companyName")
                         val companyName: String = "",
                         @SerializedName("sacCode")
                         val sacCode: String = "",
                         @SerializedName("gstNo")
                         val gstNo: String = "",
                         @SerializedName("contactPerson")
                         val contactPerson: String = "",
                         @SerializedName("mobileNo")
                         val mobileNo: String = "",
                         @SerializedName("phoneNo")
                         val phoneNo: String = "",
                         @SerializedName("logo")
                         val logo: String = "",
                         @SerializedName("state")
                         val state: String = "",
                         @SerializedName("fax")
                         val fax: String = "",
                         @SerializedName("email")
                         val email: String = "")