package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class BusTicketCancellationChargeReq(
    @SerializedName("booking_RefNo")
    var bookingRefNo: String?,

    @SerializedName("iP_Address")
    val ipAddress: String?,

    @SerializedName("request_Id")
    val requestId: String?,

    @SerializedName("imeI_Number")
    val imeiNumber: String?,

    @SerializedName("registrationID")
    val registrationId: String?,

    @SerializedName("cancelTicket_Details")
    val cancelTicketDetails: List<CancelTicketDetail>?
)


data class BusTicketCancelReq(
    @SerializedName("booking_RefNo")
    val bookingRefNo: String?,

    @SerializedName("iP_Address")
    val ipAddress: String?,

    @SerializedName("request_Id")
    val requestId: String?,

    @SerializedName("imeI_Number")
    val imeiNumber: String?,

    @SerializedName("registrationID")
    val registrationId: String?,

    @SerializedName("cancellationCharge_Key")
    val cancellationChargeKey: String?,

    @SerializedName("busTicketstoCancel")
    val busTicketstoCancel: List<CancelTicketDetail>?
)


data class CancelTicketDetail(
    @SerializedName("seat_Number")
    val seatNumber: String,

    @SerializedName("ticket_Number")
    val ticketNumber: String,

    @SerializedName("transport_PNR")
    val transportPNR: String
)
