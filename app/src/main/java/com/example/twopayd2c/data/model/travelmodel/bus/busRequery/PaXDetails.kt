package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class PaXDetails (
  @SerializedName("age"            ) var age           : Int?     = null,
  @SerializedName("dob"            ) var dob           : String?  = null,
  @SerializedName("fare"           ) var fare          : Fare?    = Fare(),
  @SerializedName("gender"         ) var gender        : Int?     = null,
  @SerializedName("id_Number"      ) var idNumber      : String?  = null,
  @SerializedName("id_Type"        ) var idType        : Int?     = null,
  @SerializedName("ladies_Seat"    ) var ladiesSeat    : Boolean? = null,
  @SerializedName("paX_Id"         ) var paXId         : Int?     = null,
  @SerializedName("paX_Name"       ) var paXName       : String?  = null,
  @SerializedName("penalty_Charge" ) var penaltyCharge : String?  = null,
  @SerializedName("primary"        ) var primary       : Boolean? = null,
  @SerializedName("seat_Number"    ) var seatNumber    : String?  = null,
  @SerializedName("status"         ) var status        : String?  = null,
  @SerializedName("ticket_Number"  ) var ticketNumber  : String?  = null,
  @SerializedName("title"          ) var title         : String?  = null
)