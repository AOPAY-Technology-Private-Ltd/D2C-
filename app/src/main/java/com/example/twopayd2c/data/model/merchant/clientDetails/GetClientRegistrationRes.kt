package com.example.twopayd2c.data.model.merchant.clientDetails

import com.google.gson.annotations.SerializedName


data class GetClientRegistrationRes (

  @SerializedName("RID"               ) var RID               : Int?    = null,
  @SerializedName("RegisterationDate" ) var RegisterationDate : String? = null,
  @SerializedName("CompanyCode"       ) var CompanyCode       : String? = null,
  @SerializedName("CompanyName"       ) var CompanyName       : String? = null,
  @SerializedName("CompanyHead"       ) var CompanyHead       : String? = null,
  @SerializedName("ContactPerson"     ) var ContactPerson     : String? = null,
  @SerializedName("Companylogo"       ) var Companylogo       : String? = null,
  @SerializedName("Address_1"         ) var Address1          : String? = null,
  @SerializedName("Address_2"         ) var Address2          : String? = null,
  @SerializedName("Address_3"         ) var Address3          : String? = null,
  @SerializedName("PinCode"           ) var PinCode           : String? = null,
  @SerializedName("State"             ) var State             : String? = null,
  @SerializedName("District"          ) var District          : String? = null,
  @SerializedName("City"              ) var City              : String? = null,
  @SerializedName("Country"           ) var Country           : String? = null,
  @SerializedName("PhoneNo_1"         ) var PhoneNo1          : String? = null,
  @SerializedName("PhoneNo_2"         ) var PhoneNo2          : String? = null,
  @SerializedName("Mobile_No"         ) var MobileNo          : String? = null,
  @SerializedName("Email_ID"          ) var EmailID           : String? = null,
  @SerializedName("HoldAmt"           ) var HoldAmt           : String? = null,
  @SerializedName("Status"            ) var Status            : Boolean? = null,
  @SerializedName("message"           ) var message           : String? = null,
  @SerializedName("Value"             ) var Value             : String? = null

)