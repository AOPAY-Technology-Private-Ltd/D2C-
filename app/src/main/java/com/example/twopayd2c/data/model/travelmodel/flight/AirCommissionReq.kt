package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class AirCommissionReq(

	@field:SerializedName("productSource")
	val productSource: String? = null,

	@field:SerializedName("retailerType")
	val retailerType: String? = null,

	@field:SerializedName("airCategory")
	val airCategory: String? = null,

	@field:SerializedName("adminCode")
	val adminCode: String? = null,

	@field:SerializedName("userType")
	val userType: String? = null,

	@field:SerializedName("retailerID")
	val retailerID: String? = null,

	@field:SerializedName("operatorID")
	val operatorID: String? = null
)
