package com.example.twopayd2c.data.model.travelmodel.login

import com.google.gson.annotations.SerializedName

data class CustomerLoginDetailsRequest(

	@field:SerializedName("loginId")
	val loginId: String? = null
)
