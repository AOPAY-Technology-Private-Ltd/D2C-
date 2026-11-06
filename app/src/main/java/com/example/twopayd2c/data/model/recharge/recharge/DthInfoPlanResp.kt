package com.example.twopayd2c.data.model.recharge.recharge

import com.google.gson.annotations.SerializedName

data class DthInfoPlanResp(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("Status")
	val statusBos: String? = null,

	@field:SerializedName("data")
	val data: MutableList<DataItem>? = null,

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class DataItem(

	@field:SerializedName("NextRechargeDate")
	val nextRechargeDate: String? = null,

	@field:SerializedName("planname")
	val planname: String? = null,

	@field:SerializedName("MonthlyRecharge")
	val monthlyRecharge: Int? = null,

	@field:SerializedName("Balance")
	val balance: Any? = null,

	@field:SerializedName("customerName")
	val customerName: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
