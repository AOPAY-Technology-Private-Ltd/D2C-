package com.example.twopayd2c.data.model.travelmodel.travel.flight

import com.google.gson.annotations.SerializedName

data class AirTicketBookingRequest(

	@field:SerializedName("booking_RefNo")
	val bookingRefNo: String? = null,

	@field:SerializedName("ticketing_Type")
	val ticketingType: String? = null,

	@field:SerializedName("loginID")
	val loginID: String? = null,

	@field:SerializedName("imeI_Number")
	val imeINumber: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("apiResponse")
	val apiResponse: String? = null,

	@field:SerializedName("registrationID")
	val registrationID: String? = null,

	@field:SerializedName("request_Id")
	val requestId: String? = null,

	@field:SerializedName("iP_Address")
	val iPAddress: String? = null
)
