package com.example.twopayd2c.data.model.travelmodel.travel.flight

import com.google.gson.annotations.SerializedName

data class AirTicketBookingResponse(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("returnMessage")
	val returnMessage: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)
