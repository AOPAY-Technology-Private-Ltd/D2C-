package com.example.twopayd2c.data.model.recharge.recharge

import com.google.gson.annotations.SerializedName

data class RechargeapiresponseReq(

	@field:SerializedName("recharge_PayableAmount")
	val rechargePayableAmount: String? = null,

	@field:SerializedName("refund_Req_Status")
	val refundReqStatus: String? = null,

	@field:SerializedName("apI_bal")
	val apIBal: String? = null,

	@field:SerializedName("apI_TransId")
	val apITransId: String? = null,

	@field:SerializedName("transId")
	val transId: String? = null,

	@field:SerializedName("recharge_Amount")
	val rechargeAmount: String? = null,

	@field:SerializedName("transIpAddress")
	val transIpAddress: String? = null,

	@field:SerializedName("apI_error_code")
	val apIErrorCode: String? = null,

	@field:SerializedName("recharge_MobileNo_CaNo")
	val rechargeMobileNoCaNo: String? = null,

	@field:SerializedName("retailerID")
	val retailerID: String? = null,

	@field:SerializedName("apI_service")
	val apIService: String? = null,

	@field:SerializedName("apI_resText")
	val apIResText: String? = null,

	@field:SerializedName("mode")
	val mode: String? = null,

	@field:SerializedName("recharge_Operator")
	val rechargeOperator: String? = null,

	@field:SerializedName("apI_urid")
	val apIUrid: String? = null,

	@field:SerializedName("recharge_ServiceType")
	val rechargeServiceType: String? = null,

	@field:SerializedName("apI_amount")
	val apIAmount: String? = null,

	@field:SerializedName("apI_commissionBal")
	val apICommissionBal: String? = null,

	@field:SerializedName("companyCode")
	val companyCode: String? = null,

	@field:SerializedName("apI_operatorId")
	val apIOperatorId: String? = null,

	@field:SerializedName("updatedBy")
	val updatedBy: String? = null,

	@field:SerializedName("recharge_Date")
	val rechargeDate: String? = null,

	@field:SerializedName("refund_Status")
	val refundStatus: String? = null,

	@field:SerializedName("refund_TransID")
	val refundTransID: String? = null,

	@field:SerializedName("updatedOn")
	val updatedOn: String? = null,

	@field:SerializedName("apI_orderId")
	val apIOrderId: String? = null,

	@field:SerializedName("apI_status")
	val apIStatus: String? = null,

	@field:SerializedName("apI_billName")
	val apIBillName: String? = null,

	@field:SerializedName("apI_mobile")
	val apIMobile: String? = null,

	@field:SerializedName("apI_billAmount")
	val apIBillAmount: String? = null,

	@field:SerializedName("gateway")
	val gateway: String? = null
)
