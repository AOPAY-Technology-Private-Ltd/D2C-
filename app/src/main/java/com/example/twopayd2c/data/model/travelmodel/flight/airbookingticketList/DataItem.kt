package com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList

import com.google.gson.annotations.SerializedName

data class DataItem(@SerializedName("booking_RefNo")
                    val bookingRefNo: String = "",
                    @SerializedName("air_PNR")
                    val airPNR: String = "",
                    @SerializedName("travelDate")
                    val travelDate: String = "",
                    @SerializedName("origin")
                    val origin: String = "",
                    @SerializedName("apiData")
                    val apiData: ApiData,
                    @SerializedName("destination")
                    val destination: String = "",
                    @SerializedName("ticket_Status_Desc")
                    val ticketStatusDesc: String = "",
                    @SerializedName("flightNumber")
                    val flightNumber: String = "",
                    var isCardVisible: Boolean = false
               )