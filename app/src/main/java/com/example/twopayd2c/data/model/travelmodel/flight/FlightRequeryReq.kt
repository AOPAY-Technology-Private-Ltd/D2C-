package com.example.twopayd2c.data.model.travel.flight

import com.google.gson.annotations.SerializedName

data class FlightRequeryReq(

    @SerializedName("loginID")
    var loginId  : String?,

    @SerializedName("booking_RefNo")
    var bookingRefNo  : String?,

    @SerializedName("iP_Address")
    var ipAddress  : String?,

    @SerializedName("request_Id")
    var requestId  : String?,

    @SerializedName("imeI_Number")
    var imeinumber  : String?,

    @SerializedName("registrationID")
    var registrationID  : String?,

    @SerializedName("air_PNR")
    var airPnr  : String?,

    @SerializedName("flightNumber")
    var flightNumber  : String?,

    @SerializedName("travelDate")
    var travelDate  : String?,

    @SerializedName("ticket_Status_Id")
    var ticketStatusId  : String?,

    @SerializedName("ticket_Status_Desc")
    var ticketStatusDesc  : String?,

    @SerializedName("apiResponse")
    var apiResponse  : String?,

    @SerializedName("createdBy")
    var createdBy  : String?,

    @SerializedName("destination")
    var destination  : String?,

    @SerializedName("origin")
    var origin  : String?
)
