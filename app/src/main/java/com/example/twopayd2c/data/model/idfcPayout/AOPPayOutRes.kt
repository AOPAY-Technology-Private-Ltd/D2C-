package com.example.twopayd2c.data.model.idfcPayout

import com.google.gson.annotations.SerializedName


data class AOPPayOutRes (

  @SerializedName("initiateAuthGenericFundTransferAPIResp" ) var initiateAuthGenericFundTransferAPIResp : InitiateAuthGenericFundTransferAPIResp? = InitiateAuthGenericFundTransferAPIResp(),
  @SerializedName("transactionAmount"                      ) var transactionAmount                      : String?                                 = null,
  @SerializedName("statuss"                                ) var statuss                                : String?                                 = null,
  @SerializedName("message"                                ) var message                                : String?                                 = null,
  @SerializedName("value"                                  ) var value                                  : String?                                 = null

)