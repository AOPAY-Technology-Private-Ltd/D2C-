package com.example.twopayd2c.data.model.merchant.activeInActiveStatus

import com.google.gson.annotations.SerializedName


data class GetAPIActiveInactiveStatusRes (

  @SerializedName("RechargeAPI_Status"        ) var RechargeAPIStatus       : String? = null,
  @SerializedName("RechargeAPI_2_Status"      ) var RechargeAPI2Status      : String? = null,
  @SerializedName("MoneyTransferAPI_Status"   ) var MoneyTransferAPIStatus  : String? = null,
  @SerializedName("MoneyTransferAPI_2_Status" ) var MoneyTransferAPI2Status : String? = null,
  @SerializedName("Payout_API_2_Status"       ) var PayoutAPI2Status        : String? = null,
  @SerializedName("Payout_API_Status"         ) var PayoutAPIStatus         : String? = null,
  @SerializedName("Payin_API_Status"          ) var PayinAPIStatus          : String? = null,
  @SerializedName("Payin_API_2_Status"        ) var PayinAPI2Status         : String? = null,
  @SerializedName("Fastag_API_Status"         ) var FastagAPIStatus         : String? = null,
  @SerializedName("PANCardAPI_Status"         ) var PANCardAPIStatus        : String? = null,
  @SerializedName("AEPS_API_Status"           ) var AEPSAPIStatus           : String? = null,
  @SerializedName("Credit_Card_API_Status"    ) var CreditCardAPIStatus     : String? = null,
  @SerializedName("Status"                    ) var Status                  : Boolean? = null,
  @SerializedName("message"                   ) var message                 : String? = null,
  @SerializedName("Value"                     ) var Value                   : String? = null

)