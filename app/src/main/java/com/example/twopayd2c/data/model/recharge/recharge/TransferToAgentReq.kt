package com.example.twopayd2c.data.model.recharge.recharge

import com.google.gson.annotations.SerializedName

data class TransferToAgentReq(

	@field:SerializedName("merchantCode")
	val merchantCode: String? = null,

	@field:SerializedName("transferFrom")
	val transferFrom: String? = null,

	@field:SerializedName("amount_Type")
	val amountType: String? = null,

	@field:SerializedName("transIpAddress")
	val transIpAddress: String? = null,

	@field:SerializedName("remark")
	val remark: String? = null,

	@field:SerializedName("transferTo")
	val transferTo: String? = null,

	@field:SerializedName("transferToMsg")
	val transferToMsg: String? = null,

	@field:SerializedName("gstAmt")
	val gstAmt: Int? = null,

	@field:SerializedName("parmUserName")
	val parmUserName: String? = null,

	@field:SerializedName("services_charge_GSTAmt")
	val servicesChargeGSTAmt: Int? = null,

	@field:SerializedName("services_charge_Without_GST")
	val servicesChargeWithoutGST: Int? = null,

	@field:SerializedName("actual_Transaction_Amount")
	val actualTransactionAmount: Double? = null,

	@field:SerializedName("actual_Commission_Amt")
	val actualCommissionAmt: Int? = null,

	@field:SerializedName("commission_Without_GST")
	val commissionWithoutGST: Int? = null,

	@field:SerializedName("transferFromMsg")
	val transferFromMsg: String? = null,

	@field:SerializedName("net_Commission_Amt")
	val netCommissionAmt: Int? = null,

	@field:SerializedName("tdS_Amt")
	val tdSAmt: Double? = null,

	@field:SerializedName("services_charge_Amt")
	val servicesChargeAmt: Int? = null,

	@field:SerializedName("customer_virtual_address")
	val customerVirtualAddress: String? = null,

	@field:SerializedName("transferAmt")
	val transferAmt: Double? = null
)
