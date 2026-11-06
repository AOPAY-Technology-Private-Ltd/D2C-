package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class FlightAddPaymentReq(
    @SerializedName("clientRefNo")
    var clientRefNo  : String?,

    @SerializedName("refNo")
    var refNo  : String?,

    @SerializedName("transactionType")
    var transactionType  : Int?,

    @SerializedName("productId")
    var productId  : String?,

    @SerializedName("iP_Address")
    var iPAddress  : String?,

    @SerializedName("request_Id")
    var requestId  : String?,

    @SerializedName("imeI_Number")
    var imeINumber  : String?,

    @SerializedName("registrationID")
    var registrationID  : String?

)
