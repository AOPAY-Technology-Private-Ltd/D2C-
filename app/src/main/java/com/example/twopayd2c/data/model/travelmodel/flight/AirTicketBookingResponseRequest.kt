package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class AirTicketBookingResponseRequest(

	@field:SerializedName("booking_RefNo")
	val bookingRefNo: String? = null,

	@field:SerializedName("supplier_Refno")
	val supplierRefno: String? = null,

	@field:SerializedName("airline_Code")
	val airlineCode: String? = null,

	@field:SerializedName("loginID")
	val loginID: String? = null,

	@field:SerializedName("hold_Validity")
	val holdValidity: String? = null,

	@field:SerializedName("failure_Remark")
	val failureRemark: String? = null,

	@field:SerializedName("airline_PNR")
	val airlinePNR: String? = null,

	@field:SerializedName("flight_Id")
	val flightId: String? = null,

	@field:SerializedName("status_Id")
	val statusId: String? = null,

	@field:SerializedName("error_Code")
	val errorCode: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("crS_PNR")
	val crSPNR: String? = null,

	@field:SerializedName("apiResponse")
	val apiResponse: String? = null,

	@field:SerializedName("error_Desc")
	val errorDesc: String? = null,

	@field:SerializedName("registrationID")
	val registrationID: String? = null,

	@field:SerializedName("error_InnerException")
	val errorInnerException: String? = null,

	@field:SerializedName("request_Id")
	val requestId: String? = null,

	@field:SerializedName("iP_Address")
	val iPAddress: String? = null,

	@field:SerializedName("record_Locator")
	val recordLocator: String? = null,

	@field:SerializedName("crS_Code")
	val crSCode: String? = null
)
