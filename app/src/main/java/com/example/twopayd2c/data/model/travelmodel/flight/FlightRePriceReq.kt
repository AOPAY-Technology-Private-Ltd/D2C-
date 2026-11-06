package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class FlightRePriceReq(
    @SerializedName("search_Key")
    var searchKey: String? = null,

    @SerializedName("customer_Mobile")
    var customerMobile: String? = null,

    @SerializedName("gsT_Input")
    var gstInput: Boolean? = false,

    @SerializedName("singlePricing")
    var singlePricing: Boolean? = false,

    @SerializedName("iP_Address")
    var ipAddress: String? = null,

    @SerializedName("request_Id")
    var requestId: String? = null,

    @SerializedName("imeI_Number")
    var imeiNumber: String? = null,

    @SerializedName("registrationID")
    var registrationId: String? = null,

    @SerializedName("airRepriceRequests")
    var airRepriceRequests: List<AirRepriceRequests> = emptyList()

)


data class AirRepriceRequests(
    @SerializedName("flight_Key")
    var flightKey: String? = null,

    @SerializedName("fare_Id")
    var fareId: String? = null
)

