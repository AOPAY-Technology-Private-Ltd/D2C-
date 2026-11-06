package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class AirCommissionResp(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("data")
	val data: List<FlightDataItem?>? = null,

	@field:SerializedName("returnMessage")
	val returnMessage: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class FlightDataItem(

	@field:SerializedName("productSource")
	val productSource: String? = null,

	@field:SerializedName("retailerType")
	val retailerType: String? = null,

	@field:SerializedName("airCategory")
	val airCategory: String? = null,

	@field:SerializedName("servicesValue")
	val servicesValue: Double? = null,

	@field:SerializedName("servicesType")
	val servicesType: String? = null,

	@field:SerializedName("adminCode")
	val adminCode: String? = null,

	@field:SerializedName("userType")
	val userType: String? = null,

	@field:SerializedName("retailerID")
	val retailerID: String? = null,

	@field:SerializedName("isActive")
	val isActive: String? = null,

	@field:SerializedName("operatorID")
	val operatorID: String? = null,

	@field:SerializedName("commissionType")
	val commissionType: String? = null,

	@field:SerializedName("commissionValue")
	val commissionValue: Double? = null
)
