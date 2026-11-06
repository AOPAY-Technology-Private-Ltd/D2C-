package com.example.twopayd2c.data.model.travel.flight

import com.google.gson.annotations.SerializedName

data class AirTicketingResponse (@SerializedName("booking_RefNo")
                                val bookingRefNo: String = "",
                                @SerializedName("response_Header")
                                val responseHeader: AirTicketingResponseHeader,
                                @SerializedName("message")
                                val message: String = "",
                                @SerializedName("statuss")
                                val statuss: String = "",
                                @SerializedName("value")
                                val value: String = "",
                                @SerializedName("airlinePNRDetails")
                                val airlinePNRDetails: List<AirlinePNRDetailsItem>?)


data class AirlinePNRDetailsItem(@SerializedName("status_Id")
                                 val statusId: String = "",
                                 @SerializedName("hold_Validity")
                                 val holdValidity: String? = null,
                                 @SerializedName("failure_Remark")
                                 val failureRemark: String? = null,
                                 @SerializedName("airlinePNRs")
                                 val airlinePNRs: List<AirlinePNRsItem>?,
                                 @SerializedName("flight_Id")
                                 val flightId: String = "")


data class AirlinePNRsItem(@SerializedName("supplier_RefNo")
                           val supplierRefNo: String = "",
                           @SerializedName("airline_Code")
                           val airlineCode: String = "",
                           @SerializedName("crS_PNR")
                           val crSPNR: String? = null,
                           @SerializedName("airline_PNR")
                           val airlinePNR: String = "",
                           @SerializedName("record_Locator")
                           val recordLocator: String = "",
                           @SerializedName("crS_Code")
                           val crSCode: String? = null)



data class AirTicketingResponseHeader(@SerializedName("status_Id")
                                      val statusId: String = "",
                                      @SerializedName("error_Code")
                                      val errorCode: String = "",
                                      @SerializedName("error_Desc")
                                      val errorDesc: String = "",
                                      @SerializedName("error_InnerException")
                                      val errorInnerException: String = "",
                                      @SerializedName("request_Id")
                                      val requestId: String = "")