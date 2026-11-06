package com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList

import com.google.gson.annotations.SerializedName

data class AirTicketListResp(@SerializedName("returnCode")
                             val returnCode: String = "",
                             @SerializedName("data")
                             val data: MutableList<DataItem>?,
                             @SerializedName("returnMessage")
                             val returnMessage: String = "",
                             @SerializedName("isSuccess")
                             val isSuccess: Boolean = false)