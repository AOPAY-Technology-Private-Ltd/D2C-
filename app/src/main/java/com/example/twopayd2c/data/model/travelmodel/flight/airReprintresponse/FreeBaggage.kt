package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class FreeBaggage(@SerializedName("check_In_Baggage")
                       val checkInBaggage: String = "",
                       @SerializedName("hand_Baggage")
                       val handBaggage: String = "",
                       @SerializedName("displayRemarks")
                       val displayRemarks: String = "")