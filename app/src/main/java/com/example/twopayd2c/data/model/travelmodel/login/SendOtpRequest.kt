package com.example.twopayd2c.data.model.travelmodel.login

import com.google.gson.annotations.SerializedName

data class SendOtpRequest(

	@field:SerializedName("taskType")
	val taskType: String? = null,

	@field:SerializedName("mobileOrEmail")
	val mobileOrEmail: String? = null,

	@field:SerializedName("otP_Type")
	val otPType: String? = null,

	@field:SerializedName("otpCode")
	val otpCode: String? = null
)
