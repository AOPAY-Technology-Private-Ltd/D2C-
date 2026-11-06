package com.example.twopayd2c.data.model.recharge.newapiflowforrecharge

import com.google.gson.annotations.SerializedName

data class RechargeCategoryResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("Status")
	val statusBos: String? = "",

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = false,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class DataItem(

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null
)
