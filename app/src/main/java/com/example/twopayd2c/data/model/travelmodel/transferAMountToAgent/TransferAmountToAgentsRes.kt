package com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent

import com.google.gson.annotations.SerializedName

data class TransferAmountToAgentsRes(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("returnMessage")
	val returnMessage: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class Data(

	@field:SerializedName("refTransID")
	val refTransID: String? = null,

	@field:SerializedName("requestData")
	val requestData: Any? = null
)
