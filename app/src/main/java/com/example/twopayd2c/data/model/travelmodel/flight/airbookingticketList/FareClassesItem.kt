package com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList

import com.google.gson.annotations.SerializedName

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