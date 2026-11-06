package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class TicketDetailsItem(@SerializedName("segemtWiseChanges")
                             val segemtWiseChanges: List<SegemtWiseChangesItem>?,
                             @SerializedName("ticket_Number")
                             val ticketNumber: String = "",
                             @SerializedName("flight_Id")
                             val flightId: String = "",
                             @SerializedName("pax_ID")
                             val paxID: Int )