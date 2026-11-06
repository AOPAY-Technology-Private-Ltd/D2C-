package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class AirportListResp(@SerializedName("returnCode")
                           val returnCode: String = "",
                           @SerializedName("data")
                           val data: List<DataItem>?,
                           @SerializedName("returnMessage")
                           val returnMessage: String = "",
                           @SerializedName("isSuccess")
                           val isSuccess: Boolean = false)

data class DataItem(@SerializedName("country")
                    val country: String = "",
                    @SerializedName("city")
                    val city: String = "",
                    @SerializedName("airportDescription")
                    val airportDescription: String = "",
                    @SerializedName("airportCode")
                    val airportCode: String = ""){
    override fun toString(): String {
        return city // or any custom display logic like "$city ($airportCode)"
    }
}