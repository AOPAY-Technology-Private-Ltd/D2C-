package com.example.twopayd2c.data.model.recharge.payout

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("payout_ref"                    ) var payoutRef                 : String? = null,
  @SerializedName("status"                        ) var status                    : String? = null,
  @SerializedName("utr"                           ) var utr                       : String? = null,
  @SerializedName("bank_response"                 ) var bankResponse              : String? = null,
  @SerializedName("dr_ledger_passed_on_timestamp" ) var drLedgerPassedOnTimestamp : String? = null,
  @SerializedName("cr_ledger_passed_on_timestamp" ) var crLedgerPassedOnTimestamp : String? = null

)