package com.example.twopayd2c.data.model.recharge.operator

import com.google.gson.annotations.SerializedName


data class RechargeOperatorsListRes(

    @SerializedName("responsecode" ) var responsecode: Int?            = null,
    @SerializedName("status"       ) var status: Boolean?              = null,
    @SerializedName("data"         ) var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("message"      ) var message: String?         = null

)