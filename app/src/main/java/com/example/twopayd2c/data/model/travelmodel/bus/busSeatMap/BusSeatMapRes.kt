package com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap

import com.google.gson.annotations.SerializedName


data class BusSeatMapRes (
  @SerializedName("boardingDetails"     ) var boardingDetails    : ArrayList<BoardingDetails> = arrayListOf(),
  @SerializedName("dropPoint_Mandatory" ) var dropPointMandatory : Boolean?                   = null,
  @SerializedName("droppingDetails"     ) var droppingDetails    : ArrayList<DroppingDetails> = arrayListOf(),
  @SerializedName("required_PAX_Detail" ) var requiredPAXDetail  : RequiredPAXDetail?         = RequiredPAXDetail(),
  @SerializedName("response_Header"     ) var responseHeader     : ResponseHeader?            = ResponseHeader(),
  @SerializedName("seatMap"             ) var seatMap            : ArrayList<SeatMap>         = arrayListOf(),
  @SerializedName("seatMap_Key"         ) var seatMapKey         : String?                    = null,
  @SerializedName("statuss"             ) var statuss            : String?                    = null,
  @SerializedName("message"             ) var message            : String?                    = null,
  @SerializedName("value"               ) var value              : String?                    = null

)