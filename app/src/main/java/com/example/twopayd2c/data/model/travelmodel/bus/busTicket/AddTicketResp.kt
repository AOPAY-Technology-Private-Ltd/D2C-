package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class AddTicketResp(
    @SerializedName("isSuccess"   ) val isSuccess: Boolean,
    @SerializedName("returnMessage"   ) val returnMessage: String,
    @SerializedName("returnCode"   ) val returnCode: String,
    @SerializedName("data"   ) val data: Any?
)
