package com.example.twopayd2c.data.model.travel.flight

import com.google.gson.annotations.SerializedName

data class AirTicketingReq(
    @SerializedName("booking_RefNo")
    var BookingRefNo  : String?,
    @SerializedName("ticketing_Type")
    var TicketingType  : String?,
    @SerializedName("iP_Address")
    var iPAddress  : String?,
    @SerializedName("request_Id")
    var requestId  : String?,
    @SerializedName("imeI_Number")
    var imeINumber  : String?,
    @SerializedName("registrationID")
    var registrationID  : String?
)
