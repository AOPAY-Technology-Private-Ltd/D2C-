package com.example.twopayd2c.data.model.recharge.beneficiary

import com.google.gson.annotations.SerializedName


data class AddBeneficiaryRes (

  @SerializedName("id"                    ) var id                  : Int?    = null,
  @SerializedName("allowed_payment_types" ) var allowedPaymentTypes : String? = null,
  @SerializedName("status"                ) var status              : String? = null,
  @SerializedName("name"                  ) var name                : String? = null,
  @SerializedName("bank_name"             ) var bankName            : String? = null,
  @SerializedName("ifsc_code"             ) var ifscCode            : String? = null,
  @SerializedName("account_number"        ) var accountNumber       : String? = null,
  @SerializedName("vpa"                   ) var vpa                 : String? = null,
  @SerializedName("mobile_number"         ) var mobileNumber        : String? = null,
  @SerializedName("email"                 ) var email               : String? = null,
  @SerializedName("address"               ) var address             : String? = null,
  @SerializedName("created_at"            ) var createdAt           : String? = null,
  @SerializedName("updated_at"            ) var updatedAt           : String? = null,
  @SerializedName("message"               ) var message             : String? = null,
  @SerializedName("subCode"               ) var subCode             : String? = null

)