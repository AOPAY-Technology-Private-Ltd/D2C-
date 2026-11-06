package com.example.twopayd2c.data.model.recharge.beneficiary

import com.google.gson.annotations.SerializedName


data class AddBeneficiaryReq (

  @SerializedName("account_number"        ) var accountNumber       : String?        = null,
  @SerializedName("address"               ) var address             : String?        = null,
  @SerializedName("allowed_payment_types" ) var allowedPaymentTypes : ArrayList<Int> = arrayListOf(),
  @SerializedName("email"                 ) var email               : String?        = null,
  @SerializedName("ifsc_code"             ) var ifscCode            : String?        = null,
  @SerializedName("mobile_number"         ) var mobileNumber        : String?        = null,
  @SerializedName("name"                  ) var name                : String?        = null,
  @SerializedName("vpa"                   ) var vpa                 : String?        = null,
  @SerializedName("RegistrationID"        ) var RegistrationID      : String?        = null,
  @SerializedName("BOSflag"               ) var BOSflag             : String?        = null

)