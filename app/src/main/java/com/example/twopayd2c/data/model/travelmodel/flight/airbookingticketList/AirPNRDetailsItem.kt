package com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList

import com.google.gson.annotations.SerializedName

data class AirPNRDetailsItem(@SerializedName("supplier_RefNo")
                             val supplierRefNo: String = "",
                             @SerializedName("airline_Code")
                             val airlineCode: String = "",
                             @SerializedName("blockedExpiryDate")
                             val blockedExpiryDate: String = "",
                             @SerializedName("bookingChangeRequests")
                             val bookingChangeRequests: Any? = null,
                             @SerializedName("post_Markup")
                             val postMarkup: String = "",
                             @SerializedName("airline_PNR")
                             val airlinePNR: String = "",
                             @SerializedName("paxTicketDetails")
                             val paxTicketDetails: List<PaxTicketDetailsItem>?,
                             @SerializedName("gross_Amount")
                             val grossAmount: String = "",
                             @SerializedName("ticket_Status_Desc")
                             val ticketStatusDesc: String = "",
                             @SerializedName("ticket_Status_Id")
                             val ticketStatusId: String = "",
                             @SerializedName("ticketingDate")
                             val ticketingDate: String = "",
                             @SerializedName("failureRemark")
                             val failureRemark: Any? = null,
                             @SerializedName("crS_PNR")
                             val crSPNR: String = "",
                             @SerializedName("retailerPostMarkup")
                             val retailerPostMarkup: String = "",
                             @SerializedName("flights")
                             val flights: List<FlightsItem>?,
                             @SerializedName("record_Locator")
                             val recordLocator: String = "",
                             @SerializedName("airline_Name")
                             val airlineName: Any? = null,
                             @SerializedName("crS_Code")
                             val crSCode: String = "")