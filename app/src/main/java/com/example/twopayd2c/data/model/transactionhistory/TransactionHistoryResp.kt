package com.example.twopayd2c.data.model.transactionhistory

import com.google.gson.annotations.SerializedName

data class TransactionHistoryResp(

	@field:SerializedName("returnCode")
	val returnCode: String? = null,

	@field:SerializedName("data")
	val data: MutableList<TransactionHistoryDataItem?>? = null,

	@field:SerializedName("returnMessage")
	val returnMessage: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class TransactionHistoryDataItem(

	@field:SerializedName("refNo")
	val refNo: String? = null,

	@field:SerializedName("transferFrom")
	val transferFrom: String? = null,

	@field:SerializedName("orderID")
	val orderID: String? = null,

	@field:SerializedName("signedAmount")
	val signedAmount: Double? = null,

	@field:SerializedName("closingBalance")
	val closingBalance: Double? = null,

	@field:SerializedName("remark")
	val remark: String? = null,

	@field:SerializedName("mobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("rid")
	val rid: Int? = null,

	@field:SerializedName("transactionDate")
	val transactionDate: String? = null,

	@field:SerializedName("transferToMsg")
	val transferToMsg: String? = null,

	@field:SerializedName("transferTo")
	val transferTo: String? = null,

	@field:SerializedName("userID")
	val userID: String? = null,

	@field:SerializedName("services_charge_GSTAmt")
	val servicesChargeGSTAmt: Double? = null,

	@field:SerializedName("services_charge_Without_GST")
	val servicesChargeWithoutGST: Double? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("amt_Transfer_TransID")
	val amtTransferTransID: String? = null,

	@field:SerializedName("ref_TransID")
	val refTransID: String? = null,

	@field:SerializedName("transferFromMsg")
	val transferFromMsg: String? = null,

	@field:SerializedName("ledgerType")
	val ledgerType: String? = null,

	@field:SerializedName("services_charge_Amt")
	val servicesChargeAmt: Double? = null,

	@field:SerializedName("recordDateTime")
	val recordDateTime: String? = null,

	@field:SerializedName("openingBalance")
	val openingBalance: Double? = null,

	@field:SerializedName("transferAmt")
	val transferAmt: Double? = null
)
