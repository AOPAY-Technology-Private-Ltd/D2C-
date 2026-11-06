package com.example.twopayd2c.data.model.travelmodel.login

import com.google.gson.annotations.SerializedName

data class D2CSignUpResponse(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("data")
	val data: D2CData? = null,

	@field:SerializedName("returnMessage")
	val returnMessage: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class D2CData(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
