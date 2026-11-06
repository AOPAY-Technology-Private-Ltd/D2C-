package com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList

import com.google.gson.annotations.SerializedName

data class FaresItem(@SerializedName("fareDetails")
                     val fareDetails: List<FareDetailsItem>?,
                     @SerializedName("food_onboard")
                     val foodOnboard: String = "",
                     @SerializedName("gstMandatory")
                     val gstMandatory: Boolean = false,
                     @SerializedName("productClass")
                     val productClass: String = "",
                     @SerializedName("fare_Key")
                     val fareKey: Any? = null,
                     @SerializedName("warning")
                     val warning: String = "",
                     @SerializedName("promptMessage")
                     val promptMessage: Any? = null,
                     @SerializedName("refundable")
                     val refundable: Boolean = false,
                     @SerializedName("fare_Id")
                     val fareId: Any? = null,
                     @SerializedName("fareType")
                     val fareType: String = "",
                     @SerializedName("lastFewSeats")
                     val lastFewSeats: Any? = null,
                     @SerializedName("seats_Available")
                     val seatsAvailable: Any? = null)