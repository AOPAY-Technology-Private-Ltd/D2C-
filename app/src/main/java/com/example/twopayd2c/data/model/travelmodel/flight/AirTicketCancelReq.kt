package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class AirTicketCancelReq (
    @SerializedName("airTicketCancelDetails")
    var ticketCancelDetailsList  : List<AirTicketCancelDetails>?,

    @SerializedName("airline_PNR")
    var airlinePNR  : String?,

    @SerializedName("refNo")
    var refNo  : String?,

    @SerializedName("cancelCode")
    var cancelCode  : String?,

    @SerializedName("reqRemarks")
    var reqRemarks  : String?,

    @SerializedName("cancellationType")
    var cancellationType  : Int?,

    @SerializedName("iP_Address")
    var ipAddress  : String?,

    @SerializedName("request_Id")
    var requestId  : String?,

    @SerializedName("imeI_Number")
    var imeINumber  : String?,

    @SerializedName("registrationID")
    var registrationId  : String?

)

data class AirTicketCancelDetails(
    @SerializedName("flightId")
    var flightId  : String?,
    @SerializedName("passengerId")
    var passangerId  : String?,
    @SerializedName("segmentId")
    var segmentId  : String?
)
