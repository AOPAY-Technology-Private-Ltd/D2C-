package com.example.twopayd2c.data.model.transactionhistory

import com.google.gson.annotations.SerializedName

data class TransactionHistoryReq(

	@field:SerializedName("agenttype")
	val agenttype: String? = null,

	@field:SerializedName("adminCode")
	val adminCode: String? = null,

	@field:SerializedName("userid")
	val userid: String? = null
)
