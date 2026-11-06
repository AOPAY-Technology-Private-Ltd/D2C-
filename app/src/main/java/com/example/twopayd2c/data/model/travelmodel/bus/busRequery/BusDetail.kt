package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class BusDetail (

  @SerializedName("ac"                           ) var ac                         : Boolean?         = null,
  @SerializedName("arrival_Time"                 ) var arrivalTime                : String?          = null,
  @SerializedName("boardingDetails"              ) var boardingDetails            : BoardingDetails? = BoardingDetails(),
  @SerializedName("bus_Type"                     ) var busType                    : String?          = null,
  @SerializedName("cancellationPolicy"           ) var cancellationPolicy         : String?          = null,
  @SerializedName("departure_Time"               ) var departureTime              : String?          = null,
  @SerializedName("droppingDetails"              ) var droppingDetails            : DroppingDetails? = DroppingDetails(),
  @SerializedName("free_WIFI"                    ) var freeWIFI                   : Boolean?         = null,
  @SerializedName("from_City"                    ) var fromCity                   : String?          = null,
  @SerializedName("lastMinute_Zero_Cancellation" ) var lastMinuteZeroCancellation : Boolean?         = null,
  @SerializedName("live_Tracking"                ) var liveTracking               : Boolean?         = null,
  @SerializedName("operator_Id"                  ) var operatorId                 : String?          = null,
  @SerializedName("operator_Name"                ) var operatorName               : String?          = null,
  @SerializedName("partial_Cancellation_Allowed" ) var partialCancellationAllowed : Boolean?         = null,
  @SerializedName("remarks"                      ) var remarks                    : String?          = null,
  @SerializedName("seat_Type"                    ) var seatType                   : Int?             = null,
  @SerializedName("supplier_RefNo"               ) var supplierRefNo              : String?          = null,
  @SerializedName("to_City"                      ) var toCity                     : String?          = null,
  @SerializedName("travelDate"                   ) var travelDate                 : String?          = null,
  @SerializedName("vehicle_Type"                 ) var vehicleType                : String?          = null,
  @SerializedName("mTicket"                      ) var mTicket                    : Boolean?         = null

)