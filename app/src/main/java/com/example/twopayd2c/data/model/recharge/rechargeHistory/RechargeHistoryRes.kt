package com.example.twopayd2c.data.model.recharge.rechargeHistory

import com.google.gson.annotations.SerializedName


data class RechargeHistoryRes (

  @SerializedName("isSuccess"     ) var isSuccess     : Boolean?        = null,
  @SerializedName("returnMessage" ) var returnMessage : String?         = null,
  @SerializedName("returnCode"    ) var returnCode    : String?         = null,
  @SerializedName("data"          ) var data          : ArrayList<Data> = arrayListOf()

//  @SerializedName("Message"        ) var Message        : String? = null,
//  @SerializedName("Status"         ) var Status         : Boolean? = null,
//  @SerializedName("RID"            ) var RID            : Int?    = null,
//  @SerializedName("Date"           ) var Date           : String? = null,
//  @SerializedName("RetailerID"     ) var RetailerID     : String? = null,
//  @SerializedName("ServiceType"    ) var ServiceType    : String? = null,
//  @SerializedName("Amount"         ) var Amount         : String? = null,
//  @SerializedName("Msg"            ) var Msg            : String? = null,
//  @SerializedName("TransdactionId" ) var TransdactionId : String? = null

//  @SerializedName("RID"            ) var RID            : Int?    = null,
//  @SerializedName("API_TransId"    ) var APITransId     : String? = null,
//  @SerializedName("DATE"           ) var DATE           : String? = null,
//  @SerializedName("TIME"           ) var TIME           : String? = null,
//  @SerializedName("AGENTID"        ) var AGENTID        : String? = null,
//  @SerializedName("SERVICETYPE"    ) var SERVICETYPE    : String? = null,
//  @SerializedName("TRANSACTIONAMT" ) var TRANSACTIONAMT : String? = null,
//  @SerializedName("MOBILENO"       ) var MOBILENO       : String? = null,
//  @SerializedName("STATUS"         ) var STATUS         : String? = null,
//  @SerializedName("REMARKS"        ) var REMARKS        : String? = null,
//  @SerializedName("TransID"        ) var TransID        : String? = null,
//  @SerializedName("RefundStatus"   ) var RefundStatus   : String? = null,
//  @SerializedName("RefundTransID"  ) var RefundTransID  : String? = null,
//  @SerializedName("ReqStatus"      ) var ReqStatus      : String? = null,
//  @SerializedName("Message"        ) var Message        : String? = null,
//  @SerializedName("Status"         ) var Status         : Boolean? = null

)