package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class PaxTicketDetailsItem(@SerializedName("first_Name")
                                val firstName: String = "",
                                @SerializedName("gender")
                                val gender: String = "",
                                @SerializedName("pax_type")
                                val paxType: String = "",
                                @SerializedName("passport_Issuing_Country")
                                val passportIssuingCountry: String = "",
                                @SerializedName("title")
                                val title: String = "",
                                @SerializedName("fares")
                                val fares: List<FaresItem>?,
                                @SerializedName("ticketDetails")
                                val ticketDetails: List<TicketDetailsItem>?,
                                @SerializedName("passport_Number")
                                val passportNumber: String = "",
                                @SerializedName("nationality")
                                val nationality: String = "",
                                @SerializedName("dob")
                                val dob: String = "",
                                @SerializedName("frequentFlyerDetails")
                                val frequentFlyerDetails: Any? = null,
                                @SerializedName("passport_Expiry")
                                val passportExpiry: String = "",
                                @SerializedName("ticketStatus")
                                val ticketStatus: String = "",
                                @SerializedName("last_Name")
                                val lastName: String = "",
                                @SerializedName("age")
                                val age: String = "",
                                @SerializedName("pax_Id")
                                val paxId: String = "")