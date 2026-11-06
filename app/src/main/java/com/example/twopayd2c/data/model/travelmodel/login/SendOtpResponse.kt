package com.example.twopayd2c.data.model.travelmodel.login

import com.google.gson.annotations.SerializedName

data class SendOtpResponse(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("data")
	val data: SendOTPData? = null,

	@field:SerializedName("returnMessage")
	val returnMessage: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class SendOTPData(

	@field:SerializedName("sendVia")
	val sendVia: String? = null,

	@field:SerializedName("otpCode")
	val otpCode: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
