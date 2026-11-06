package com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList

import com.google.gson.annotations.SerializedName

data class BookingPaymentDetailItem(@SerializedName("gateway_Charges")
                                    val gatewayCharges: String = "",
                                    @SerializedName("payment_Mode")
                                    val paymentMode: String = "",
                                    @SerializedName("paymentConfirmation_Number")
                                    val paymentConfirmationNumber: String = "",
                                    @SerializedName("currency_Code")
                                    val currencyCode: String = "",
                                    @SerializedName("payment_Amount")
                                    val paymentAmount: String = "")