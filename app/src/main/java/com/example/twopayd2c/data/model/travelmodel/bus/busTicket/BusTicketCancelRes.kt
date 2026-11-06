package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class BusTicketCancelRes(@SerializedName("response_Header")
                              val responseHeader: ResponseHeader,
                              @SerializedName("message")
                              val message: String = "",
                              @SerializedName("statuss")
                              val statuss: String = "",
                              @SerializedName("value")
                              val value: String = "")