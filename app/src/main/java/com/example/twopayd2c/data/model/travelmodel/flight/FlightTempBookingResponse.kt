package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class FlightTempBookingResponse(@SerializedName("booking_RefNo")
                                     val bookingRefNo: String = "",
                                     @SerializedName("response_Header")
                                     val responseHeader: FlightTempResponseHeader,
                                     @SerializedName("message")
                                     val message: String = "",
                                     @SerializedName("statuss")
                                     val statuss: String = "",
                                     @SerializedName("value")
                                     val value: String = "")

data class FlightTempResponseHeader(@SerializedName("status_Id")
                          val statusId: String = "",
                          @SerializedName("error_Code")
                          val errorCode: String = "",
                          @SerializedName("error_Desc")
                          val errorDesc: String = "",
                          @SerializedName("error_InnerException")
                          val errorInnerException: String = "",
                          @SerializedName("request_Id")
                          val requestId: String = "")