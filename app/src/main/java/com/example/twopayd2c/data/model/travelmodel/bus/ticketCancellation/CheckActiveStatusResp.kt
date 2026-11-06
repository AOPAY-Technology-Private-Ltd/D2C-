package com.example.twopayd2c.data.model.travelmodel.bus.ticketCancellation

import com.google.gson.annotations.SerializedName

data class CheckActiveStatusResp(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("returnMessage")
	val returnMessage: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class Data(

	@field:SerializedName("isActive")
	val isActive: Boolean? = null,

	@field:SerializedName("status")
	val status: String? = null
)
