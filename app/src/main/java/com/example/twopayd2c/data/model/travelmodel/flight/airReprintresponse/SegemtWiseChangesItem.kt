package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class SegemtWiseChangesItem(@SerializedName("cancelStatus")
                                 val cancelStatus: String = "",
                                 @SerializedName("segement_Id")
                                 val segementId: String = "",
                                 @SerializedName("origin")
                                 val origin: String = "",
                                 @SerializedName("cancelRequestId")
                                 val cancelRequestId: String = "",
                                 @SerializedName("destination")
                                 val destination: String = "",
                                 @SerializedName("rescheduleStatus")
                                 val rescheduleStatus: String = "",
                                 @SerializedName("rescheduleRequestId")
                                 val rescheduleRequestId: String = "",
                                 @SerializedName("return_Flight")
                                 val returnFlight: Boolean = false)