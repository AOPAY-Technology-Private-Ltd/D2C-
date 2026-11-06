package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class FlightAddPaymentResponse(@SerializedName("amount")
                                    val amount: Int = 0,
                                    @SerializedName("debitedAmount")
                                    val debitedAmount: Int = 0,
                                    @SerializedName("paymentID")
                                    val paymentID: String = "",
                                    @SerializedName("response_Header")
                                    val responseHeader: FlightResponseHeader,
                                    @SerializedName("message")
                                    val message: String = "",
                                    @SerializedName("statuss")
                                    val statuss: String = "",
                                    @SerializedName("value")
                                    val value: String = "")



data class FlightResponseHeader(@SerializedName("status_Id")
                          val statusId: String = "",
                          @SerializedName("error_Code")
                          val errorCode: String = "",
                          @SerializedName("error_Desc")
                          val errorDesc: String = "",
                          @SerializedName("error_InnerException")
                          val errorInnerException: String = "",
                          @SerializedName("request_Id")
                          val requestId: String = "")

