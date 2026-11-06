package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class FlightSearchResp(@SerializedName("tripDetails")
                            val tripDetails: List<TripDetailsItem>?,
                            @SerializedName("search_Key")
                            val searchKey: String = "",
                            @SerializedName("response_Header")
                            val responseHeader: ResponseHeader,
                            @SerializedName("message")
                            val message: String = "",
                            @SerializedName("statuss")
                            val statuss: String = "",
                            @SerializedName("value")
                            val value: String = "")

data class TripDetailsItem(@SerializedName("trip_Id")
                           val tripId: String = "",
                           @SerializedName("flights")
                           val flights: List<FlightsItem>?)

data class FlightsItem(@SerializedName("hasMoreClass")
                       val hasMoreClass: Boolean = false,
                       @SerializedName("inventoryType")
                       val inventoryType: String = "",
                       @SerializedName("airline_Code")
                       val airlineCode: String = "",
                       @SerializedName("origin")
                       val origin: String = "",
                       @SerializedName("destination")
                       val destination: String = "",
                       @SerializedName("insta_Cancel_Allowed")
                       val instaCancelAllowed: Boolean = false,
                       @SerializedName("block_Ticket_Allowed")
                       val blockTicketAllowed: Boolean = false,
                       @SerializedName("fares")
                       val fares: List<FaresItem>?,
                       @SerializedName("flight_Id")
                       val flightId: String = "",
                       @SerializedName("segments")
                       val segments: List<SegmentsItem>?,
                       @SerializedName("gsT_Entry_Allowed")
                       val gsTEntryAllowed: Boolean = false,
                       @SerializedName("flight_Numbers")
                       val flightNumbers: String = "",
                       @SerializedName("travelDate")
                       val travelDate: String = "",
                       @SerializedName("flight_Key")
                       val flightKey: String = "",
                       @SerializedName("cached")
                       val cached: Boolean = false,
                       @SerializedName("isFareChange")
                       val isFareChange: Boolean = false,
                       @SerializedName("isLCC")
                       val isLCC: Boolean = false,
                       @SerializedName("repriced")
                       val repriced: Boolean = false)

data class FaresItem(@SerializedName("fareDetails")
                     val fareDetails: List<FareDetailsItem>?,
                     @SerializedName("food_onboard")
                     val foodOnboard: Any? = null,
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
                     val fareId: String = "",
                     @SerializedName("fareType")
                     val fareType: String = "",
                     @SerializedName("lastFewSeats")
                     val lastFewSeats: Any? = null,
                     @SerializedName("seats_Available")
                     val seatsAvailable: String = "")

data class SegmentsItem(@SerializedName("origin_City")
                        val originCity: String = "",
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
                        val operatedBy: String = "",
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
                        val destinationCity: String = "",
                        @SerializedName("return_Flight")
                        val returnFlight: Boolean = false,
                        @SerializedName("aircraft_Type")
                        val aircraftType: String = "")

data class ResponseHeader(@SerializedName("status_Id")
                          val statusId: String = "",
                          @SerializedName("error_Code")
                          val errorCode: String = "",
                          @SerializedName("error_Desc")
                          val errorDesc: String = "",
                          @SerializedName("error_InnerException")
                          val errorInnerException: String = "",
                          @SerializedName("request_Id")
                          val requestId: String = "")

data class FreeBaggage(@SerializedName("check_In_Baggage")
                       val checkInBaggage: String = "",
                       @SerializedName("hand_Baggage")
                       val handBaggage: String = "",
                       @SerializedName("displayRemarks")
                       val displayRemarks: Any? = null)

data class FareDetailsItem(@SerializedName("tds")
                           val tds: String = "",
                           @SerializedName("airportTaxes")
                           val airportTaxes: List<AirportTaxesItem>?,
                           @SerializedName("fareClasses")
                           val fareClasses: List<FareClassesItem>?,
                           @SerializedName("free_Baggage")
                           val freeBaggage: FreeBaggage,
                           @SerializedName("airportTax_Amount")
                           val airportTaxAmount: String = "",
                           @SerializedName("gst")
                           val gst: String = "",
                           @SerializedName("trade_Markup_Amount")
                           val tradeMarkupAmount: String = "",
                           @SerializedName("service_Fee_Amount")
                           val serviceFeeAmount: String = "",
                           @SerializedName("basic_Amount")
                           val basicAmount: String = "",
                           @SerializedName("net_Commission")
                           val netCommission: String = "",
                           @SerializedName("total_Amount")
                           val totalAmount: String = "",
                           @SerializedName("promo_Discount")
                           val promoDiscount: String = "",
                           @SerializedName("yQ_Amount")
                           val yQAmount: String = "",
                           @SerializedName("currency_Code")
                           val currencyCode: String = "",
                           @SerializedName("gross_Commission")
                           val grossCommission: String = "",
                           @SerializedName("paX_Type")
                           val paXType: String = "")

data class AirportTaxesItem(@SerializedName("tax_Desc")
                            val taxDesc: String = "",
                            @SerializedName("tax_Code")
                            val taxCode: String = "",
                            @SerializedName("tax_Amount")
                            val taxAmount: String = "")

data class FareClassesItem(@SerializedName("privileges")
                           val privileges: Any? = null,
                           @SerializedName("class_Desc")
                           val classDesc: String = "",
                           @SerializedName("fareBasis")
                           val fareBasis: String = "",
                           @SerializedName("class_Code")
                           val classCode: String = "",
                           @SerializedName("segment_Id")
                           val segmentId: String = "")
