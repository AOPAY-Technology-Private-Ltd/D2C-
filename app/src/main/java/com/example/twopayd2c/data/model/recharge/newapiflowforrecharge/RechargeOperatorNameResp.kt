package com.example.twopayd2c.data.model.recharge.newapiflowforrecharge

import com.google.gson.annotations.SerializedName

data class RechargeOperatorNameResp(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("Status")
	val statusBos: String? = "",

	@field:SerializedName("data")
	val data: MutableList<DataItemOperator>? = null,

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = false,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null

)

data class DataItemOperator(

	@field:SerializedName("productId")
	val productId: Int? = null,

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null

)
