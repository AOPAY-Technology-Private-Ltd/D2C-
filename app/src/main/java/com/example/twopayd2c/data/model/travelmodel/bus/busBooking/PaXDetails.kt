package com.example.twopayd2c.data.model.travelmodel.bus.busBooking

import com.google.gson.annotations.SerializedName


data class PaXDetails (
  @SerializedName("Age"            ) var Age           : Int?              = null,
  @SerializedName("DOB"            ) var DOB           : String?           = null,
  @SerializedName("Fare"           ) var Fare          : ArrayList<String> = arrayListOf(),
  @SerializedName("Gender"         ) var Gender        : String?              = null,
  @SerializedName("Id_Number"      ) var IdNumber      : String?           = null,
  @SerializedName("Id_Type"        ) var IdType        : Int?              = null,
  @SerializedName("Ladies_Seat"    ) var LadiesSeat    : Boolean?          = null,
  @SerializedName("PAX_Id"         ) var PAXId         : String?           = null,
  @SerializedName("PAX_Name"       ) var PAXName       : String?           = null,
  @SerializedName("Penalty_Charge" ) var PenaltyCharge : String?           = null,
  @SerializedName("Primary"        ) var Primary       : Boolean?          = null,
  @SerializedName("Seat_Number"    ) var SeatNumber    : String?           = null,
  @SerializedName("Status"         ) var Status        : String?           = null,
  @SerializedName("Ticket_Number"  ) var TicketNumber  : String?           = null,
  @SerializedName("Title"          ) var Title         : String?           = null
)