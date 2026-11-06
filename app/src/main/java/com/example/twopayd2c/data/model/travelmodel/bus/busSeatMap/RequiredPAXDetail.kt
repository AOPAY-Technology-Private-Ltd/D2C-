package com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap

import com.google.gson.annotations.SerializedName


data class RequiredPAXDetail (

  @SerializedName("age"      ) var age     : Boolean? = null,
  @SerializedName("dob"      ) var dob     : Boolean? = null,
  @SerializedName("gender"   ) var gender  : Boolean? = null,
  @SerializedName("iD_Proof" ) var iDProof : Boolean? = null,
  @SerializedName("name"     ) var name    : Boolean? = null,
  @SerializedName("title"    ) var title   : Boolean? = null

)