package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class GetAirTicketListReq(
    @SerializedName("loginID")
    var loginId  : String?
)
