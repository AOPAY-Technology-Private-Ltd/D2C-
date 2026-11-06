package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class AirportListReq(
    @SerializedName("searchTerm")
    var searchTerm  : String?
)
