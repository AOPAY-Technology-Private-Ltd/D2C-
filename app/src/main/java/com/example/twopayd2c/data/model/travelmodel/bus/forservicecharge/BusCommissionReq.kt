package com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge

import com.google.gson.annotations.SerializedName

data class BusCommissionReq(

	@field:SerializedName("productSource")
	val productSource: String? = null,

	@field:SerializedName("seatType")
	val seatType: String? = null,

	@field:SerializedName("retailerType")
	val retailerType: String? = null,

	@field:SerializedName("busCategory")
	val busCategory: String? = null,

	@field:SerializedName("admincode")
	val admincode: String? = null,

	@field:SerializedName("userType")
	val userType: String? = null,

	@field:SerializedName("retailerID")
	val retailerID: String? = null
)
