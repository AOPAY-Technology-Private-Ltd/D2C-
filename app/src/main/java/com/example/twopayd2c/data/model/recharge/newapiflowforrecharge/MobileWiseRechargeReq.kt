package com.example.twopayd2c.data.model.recharge.newapiflowforrecharge

import com.google.gson.annotations.SerializedName

data class MobileWiseRechargeReq(
    @SerializedName("Number")
    var number : String,

    @SerializedName("RegistrationID")
    var registrationID : String ?
)

