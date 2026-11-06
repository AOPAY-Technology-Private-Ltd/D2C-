package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class SegmentsItem(@SerializedName("origin_City")
                        val originCity: Any? = null,
                        @SerializedName("airline_Code")
                        val airlineCode: String = "",
                        @SerializedName("origin_Terminal")
                        val originTerminal: String = "",
                        @SerializedName("origin")
                        val origin: String = "",
                        @SerializedName("destination")
                        val destination: String = "",
                        @SerializedName("stop_Over")
                        val stopOver: Any? = null,
                        @SerializedName("leg_Index")
                        val legIndex: String = "",
                        @SerializedName("operatedBy")
                        val operatedBy: Any? = null,
                        @SerializedName("destination_Terminal")
                        val destinationTerminal: String = "",
                        @SerializedName("duration")
                        val duration: String = "",
                        @SerializedName("departure_DateTime")
                        val departureDateTime: String = "",
                        @SerializedName("flight_Number")
                        val flightNumber: String = "",
                        @SerializedName("segment_Id")
                        val segmentId: String = "",
                        @SerializedName("arrival_DateTime")
                        val arrivalDateTime: String = "",
                        @SerializedName("airline_Name")
                        val airlineName: String = "",
                        @SerializedName("destination_City")
                        val destinationCity: Any? = null,
                        @SerializedName("return_Flight")
                        val returnFlight: Boolean = false,
                        @SerializedName("aircraft_Type")
                        val aircraftType: String = "")