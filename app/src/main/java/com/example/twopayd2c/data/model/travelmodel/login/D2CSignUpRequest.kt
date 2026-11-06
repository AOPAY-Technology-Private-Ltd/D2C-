package com.example.twopayd2c.data.model.travelmodel.login

import com.google.gson.annotations.SerializedName

data class D2CSignUpRequest(

	@field:SerializedName("merchantCode")
	val merchantCode: String? = null,

	@field:SerializedName("ipAddress")
	val ipAddress: String? = null,

	@field:SerializedName("adminCode")
	val adminCode: String? = null,

	@field:SerializedName("emailID")
	val emailID: String? = null,

	@field:SerializedName("mobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("state")
	val state: String? = null
)
