package com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap

import com.google.gson.annotations.SerializedName


data class SeatMap (

  @SerializedName("bookable"    ) var bookable   : Boolean?    = null,
  @SerializedName("column"      ) var column     : Int?        = null,
  @SerializedName("fareMaster"  ) var fareMaster : FareMaster? = FareMaster(),
  @SerializedName("ladies_Seat" ) var ladiesSeat : Boolean?    = null,
  @SerializedName("length"      ) var length     : String?     = null,
  @SerializedName("row"         ) var row        : String?     = null,
  @SerializedName("seat_Key"    ) var seatKey    : String?     = null,
  @SerializedName("seat_Number" ) var seatNumber : String?     = null,
  @SerializedName("width"       ) var width      : String?     = null,
  @SerializedName("zIndex"      ) var zIndex     : String?     = null

)