package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName

data class BusTicketCancelResponseReq(

	@field:SerializedName("booking_RefNo")
	val bookingRefNo: String? = null,

	@field:SerializedName("loginId")
	val loginId: String? = null,

	@field:SerializedName("error_Code")
	val errorCode: String? = null,

	@field:SerializedName("createdby")
	val createdby: String? = null,

	@field:SerializedName("apiResponse")
	val apiResponse: String? = null,

	@field:SerializedName("registrationID")
	val registrationID: String? = null,

	@field:SerializedName("error_Desc")
	val errorDesc: String? = null
)
