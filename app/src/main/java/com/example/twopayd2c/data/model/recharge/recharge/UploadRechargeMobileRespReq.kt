package com.example.twopayd2c.data.model.recharge.recharge

import com.google.gson.annotations.SerializedName

data class UploadRechargeMobileRespReq(

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("request_Param")
	val requestParam: String? = null,

	@field:SerializedName("operators")
	val operators: String? = null,

	@field:SerializedName("registrationID")
	val registrationID: String? = null,

	@field:SerializedName("ipAddress")
	val ipAddress: String? = null,

	@field:SerializedName("paramuser")
	val paramuser: String? = null,

	@field:SerializedName("canumber")
	val canumber: String? = null,

	@field:SerializedName("referenceid")
	val referenceid: String? = null
)


data class UploadRechargeMobileRespRespReq(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("response_code")
	val responsecode: String? = null,

	@field:SerializedName("operatorid")
	val operators: String? = null,

	@field:SerializedName("ackno")
	val ackno: String? = null,

	@field:SerializedName("refid")
	val refid: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("registrationID")
	val registrationID: String? = null,

	@field:SerializedName("ipAddress")
	val ipAddress: String? = null,

	@field:SerializedName("response_Param")
	val responseParam: String? = null,

	@field:SerializedName("paramuser")
	val paramuser: String? = null
)
