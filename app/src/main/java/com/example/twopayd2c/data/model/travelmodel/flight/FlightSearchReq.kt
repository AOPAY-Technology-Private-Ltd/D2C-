package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class FlightSearchReq(
     @SerializedName("request_Id")
     var requestId  : String?,

     @SerializedName("imeI_Number")
     var imeInumber  : String?,

     @SerializedName("travel_Type")
     var travelType  : Int?,

     @SerializedName("booking_Type")
     var bookingType  : Int?,

     @SerializedName("Adult_Count")
     var adultCount  : String?,

     @SerializedName("Child_Count")
     var childCount  : String?,

     @SerializedName("Infant_Count")
     var infantCount  : String?,

     @SerializedName("Class_Of_Travel")
     var classOfTravel  : String?,

     @SerializedName("InventoryType")
     var inventoryType  : Int?,

     @SerializedName("Source_Type")
     var sourceType  : Int?,

     @SerializedName("SrCitizen_Search")
     var srCitizenSearch  : Boolean?,

     @SerializedName("StudentFare_Search")
     var studentFareSearch  : Boolean?,

     @SerializedName("DefenceFare_Search")
     var defenceFareSearch  : Boolean?,

     @SerializedName("registrationID")
     var registartionId  : String?,

     @SerializedName("ipAddress")
     var ipAddress  : String?,

     @SerializedName("Filtered_Airline")
     var filterAirlineList  : List<FilterAirLine>?,

     @SerializedName("tripInfo")
     var tripeInfoList  : List<TripInfo>?

 )

data class TripInfo( @SerializedName("origin")
                          var origin  : String?,
                          @SerializedName("destination")
                          var destination  : String?,
                          @SerializedName("travelDate")
                          var traveldate  : String?,
                          @SerializedName("trip_Id")
                          var tripId  : Int?)


data class FilterAirLine ( @SerializedName("Airline_Code")
                          var airlineCode  : String?
                         )