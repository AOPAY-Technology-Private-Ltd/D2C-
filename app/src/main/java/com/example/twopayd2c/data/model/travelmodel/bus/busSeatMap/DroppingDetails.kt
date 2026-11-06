package com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap

import com.google.gson.annotations.SerializedName


data class DroppingDetails (

  @SerializedName("dropping_Address"  ) var droppingAddress  : String? = null,
  @SerializedName("dropping_Contact"  ) var droppingContact  : String? = null,
  @SerializedName("dropping_Id"       ) var droppingId       : String? = null,
  @SerializedName("dropping_Landmark" ) var droppingLandmark : String? = null,
  @SerializedName("dropping_Name"     ) var droppingName     : String? = null,
  @SerializedName("dropping_Time"     ) var droppingTime     : String? = null

)