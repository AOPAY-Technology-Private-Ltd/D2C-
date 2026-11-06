package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class FlightsItem(@SerializedName("hasMoreClass")
                       val hasMoreClass: Boolean = false,
                       @SerializedName("inventoryType")
                       val inventoryType: String = "",
                       @SerializedName("airline_Code")
                       val airlineCode: String = "",
                       @SerializedName("origin")
                       val origin: String = "",
                       @SerializedName("destination")
                       val destination: String = "",
                       @SerializedName("insta_Cancel_Allowed")
                       val instaCancelAllowed: Boolean = false,
                       @SerializedName("block_Ticket_Allowed")
                       val blockTicketAllowed: Boolean = false,
                       @SerializedName("fares")
                       val fares: List<FaresItem>?,
                       @SerializedName("flight_Id")
                       val flightId: String = "",
                       @SerializedName("segments")
                       val segments: List<SegmentsItem>?,
                       @SerializedName("gsT_Entry_Allowed")
                       val gsTEntryAllowed: Boolean = false,
                       @SerializedName("flight_Numbers")
                       val flightNumbers: Any? = null,
                       @SerializedName("travelDate")
                       val travelDate: String = "",
                       @SerializedName("flight_Key")
                       val flightKey: String = "",
                       @SerializedName("cached")
                       val cached: Boolean = false,
                       @SerializedName("isFareChange")
                       val isFareChange: Boolean = false,
                       @SerializedName("isLCC")
                       val isLCC: Boolean = false,
                       @SerializedName("repriced")
                       val repriced: Boolean = false)