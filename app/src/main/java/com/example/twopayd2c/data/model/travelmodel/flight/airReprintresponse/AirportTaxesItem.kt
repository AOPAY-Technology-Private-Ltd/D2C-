package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class AirportTaxesItem(@SerializedName("tax_Desc")
                            val taxDesc: String = "",
                            @SerializedName("tax_Code")
                            val taxCode: String = "",
                            @SerializedName("tax_Amount")
                            val taxAmount: String = "")