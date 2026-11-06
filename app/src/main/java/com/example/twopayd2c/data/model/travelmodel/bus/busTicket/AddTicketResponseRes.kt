package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class AddTicketResponseRes(
    @SerializedName("returnCode"   ) val returnCode: String = "",
    @SerializedName("data"         ) val data: Any? = null,
    @SerializedName("returnMessage") val returnMessage: String = "",
    @SerializedName("isSuccess"    ) val isSuccess: Boolean = false
)