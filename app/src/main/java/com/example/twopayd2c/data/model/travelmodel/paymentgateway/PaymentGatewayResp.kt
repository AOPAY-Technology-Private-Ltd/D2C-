package com.example.twopayd2c.data.model.travelmodel.paymentgateway

import com.google.gson.annotations.SerializedName

data class PaymentGatewayResp(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("PreparePOSTForm")
	val preparePOSTForm: String? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("PGOrderID")
	val pGOrderID: String? = null
)
