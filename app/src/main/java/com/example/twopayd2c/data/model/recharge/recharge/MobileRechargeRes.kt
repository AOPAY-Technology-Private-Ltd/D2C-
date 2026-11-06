package com.example.twopayd2c.data.model.recharge.recharge

import com.google.gson.annotations.SerializedName


data class MobileRechargeRes(

    @SerializedName("responsecode" ) var responsecode: Int?     = null,
    @SerializedName("status"       ) var status: Boolean? = null,
    @SerializedName("operatorid"   ) var operatorid: String?  = null,
    @SerializedName("ackno"        ) var ackno: String?  = null,
    @SerializedName("message"      ) var message: String?  = null,
    @SerializedName("refid"        ) var refid: String?  = null

)