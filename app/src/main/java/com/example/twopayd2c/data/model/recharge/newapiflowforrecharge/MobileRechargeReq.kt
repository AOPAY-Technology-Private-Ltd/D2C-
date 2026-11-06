package com.example.twopayd2c.data.model.recharge.newapiflowforrecharge

import com.google.gson.annotations.SerializedName

data class MobileRechargeReq(
    @SerializedName("RegistrationID")
    var registrationId: String ?,

    @SerializedName("ProductID")
    var productId: String,

    @SerializedName("Amount")
    var amount: String,

    @SerializedName("Geocode")
    var geoCoder: String,

    @SerializedName("CustomerNumber")
    var customerNumber: String

)
