package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class AddTicketResponseReq(
    @SerializedName("request_Id"             ) var requestId   : String? = null,
    @SerializedName("iP_Address"             ) var iPAddress      : String? = null,
    @SerializedName("imeI_Number"             ) var imeINumber      : String? = null,
    @SerializedName("booking_RefNo"           ) var bookingRefNo     : String? = null,
    @SerializedName("error_Code"              ) var errorCode     : String? = null,
    @SerializedName("error_Desc"              ) var errorDesc     : String? = null,
    @SerializedName("error_InnerException"    ) var errorInnerException     : String? = null,
    @SerializedName("createdBy"               ) var createdBy : String? = null,
    @SerializedName("status_Id"               ) var statusId     : String? = null,
    @SerializedName("supplier_Refno"          ) var supplierRefNo     : String? = null,
    @SerializedName("transport_PNR"           ) var transportPNR     : String? = null,
    @SerializedName("registrationID"          ) var registrationID : String? = null,
    @SerializedName("loginID"                 ) var loginID : String? = null,
    @SerializedName("apiResponse"             ) var apiResponse : String? = null,
)
