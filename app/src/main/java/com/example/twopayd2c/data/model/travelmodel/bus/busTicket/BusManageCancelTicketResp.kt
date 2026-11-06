package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class BusManageCancelTicketResp(

	@field:SerializedName("outputMessage")
	val outputMessage: String? = null,

	@field:SerializedName("data")
	val data: List<Any?>? = null
)
