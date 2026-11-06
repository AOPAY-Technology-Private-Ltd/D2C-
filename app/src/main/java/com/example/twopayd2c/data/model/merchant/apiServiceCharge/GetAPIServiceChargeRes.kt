package com.example.twopayd2c.data.model.merchant.apiServiceCharge

import com.google.gson.annotations.SerializedName


data class GetAPIServiceChargeRes (

  @SerializedName("OperatorName"            ) var OperatorName           : String? = null,
  @SerializedName("Dis_CommissionType"      ) var DisCommissionType      : String? = null,
  @SerializedName("Dis_Commission"          ) var DisCommission          : String? = null,
  @SerializedName("Sub_Dis_CommissionType"  ) var SubDisCommissionType   : String? = null,
  @SerializedName("Sub_Dis_Commission"      ) var SubDisCommission       : String? = null,
  @SerializedName("Retailer_CommissionType" ) var RetailerCommissionType : String? = null,
  @SerializedName("Retailer_Commission"     ) var RetailerCommission     : String? = null,
  @SerializedName("Customer_CommissionType" ) var CustomerCommissionType : String? = null,
  @SerializedName("Customer_Commission"     ) var CustomerCommission     : String? = null,
  @SerializedName("ServiceType"             ) var ServiceType            : String? = null,
  @SerializedName("ServiceCharge"           ) var ServiceCharge          : String? = null,
  @SerializedName("ServiceCharge_Slab"      ) var ServiceChargeSlab      : String? = null,
  @SerializedName("Status"                  ) var Status                 : Boolean? = null,
  @SerializedName("message"                 ) var message                : String? = null,
  @SerializedName("Value"                   ) var Value                  : String? = null

)