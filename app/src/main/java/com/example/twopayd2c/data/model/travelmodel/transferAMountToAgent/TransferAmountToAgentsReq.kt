package com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent

import com.google.gson.annotations.SerializedName

data class TransferAmountToAgentsReq(

	@field:SerializedName("transferFrom")
	val transferFrom: String? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("serviceCode")
	val serviceCode: String? = null,

	@field:SerializedName("transIpAddress")
	val transIpAddress: String? = null,

	@field:SerializedName("remark")
	val remark: String? = null,

	@field:SerializedName("transferTo")
	val transferTo: String? = null,

	@field:SerializedName("transferToMsg")
	val transferToMsg: String? = null,

	@field:SerializedName("transferFromMsg")
	val transferFromMsg: String? = null,

	@field:SerializedName("operatorCode")
	val operatorCode: String? = null,

	@field:SerializedName("subserviceCode")
	val subserviceCode: String? = null,

	@field:SerializedName("services_charge_GSTAmt")
	val servicesChargeGSTAmt: Double? = null,

	@field:SerializedName("services_charge_Without_GST")
	val servicesChargeWithoutGST: Double? = null,

	@field:SerializedName("depositBankName")
	val depositBankName: String? = null,

	@field:SerializedName("actual_Transaction_Amount")
	val actualTransactionAmount: String? = null,

	@field:SerializedName("accountHolder")
	val accountHolder: String? = null,

	@field:SerializedName("benfiid")
	val benfiid: String? = null,

	@field:SerializedName("apporvedStatus")
	val apporvedStatus: String? = null,

	@field:SerializedName("registrationId")
	val registrationId: String? = null,

	@field:SerializedName("retailerCommissionAmt")
	val retailerCommissionAmt: Double? = null,

	@field:SerializedName("customer_virtual_address")
	val customerVirtualAddress: String? = null,

	@field:SerializedName("transferAmt")
	val transferAmt: Double? = null,

	@field:SerializedName("merchantCode")
	val merchantCode: String? = null,

	@field:SerializedName("amount_Type")
	val amountType: String? = null,

	@field:SerializedName("paymentMode")
	val paymentMode: String? = null,

	@field:SerializedName("orderID")
	val orderID: String? = null,

	@field:SerializedName("retailerId")
	val retailerId: String? = null,

	@field:SerializedName("parmUserName")
	val parmUserName: String? = null,

	@field:SerializedName("branchCode_ChecqueNo")
	val branchCodeChecqueNo: String? = null,

	@field:SerializedName("services_charge_Amt")
	val servicesChargeAmt: Double? = null,

	@field:SerializedName("operatorname")
	val operatorname: String? = null
)
