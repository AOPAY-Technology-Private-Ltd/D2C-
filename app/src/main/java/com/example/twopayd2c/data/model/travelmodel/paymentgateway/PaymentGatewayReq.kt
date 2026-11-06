package com.example.twopayd2c.data.model.travelmodel.paymentgateway

import com.google.gson.annotations.SerializedName

data class PaymentGatewayReq(

	@field:SerializedName("payerMobile")
	val payerMobile: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("RegistrationID")
	val registrationID: String? = null,

	@field:SerializedName("payerName")
	val payerName: String? = null,

	@field:SerializedName("callbackUrl")
	val callbackUrl: String? = null
)
