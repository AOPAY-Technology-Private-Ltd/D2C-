package com.example.twopayd2c.data.model.recharge.newapiflowforrecharge

import com.google.gson.annotations.SerializedName

data class RechargeOperatorsReq(
    @SerializedName("RegistrationID")
    var registrationID : String ?,

    @SerializedName("displayName")
    var displayName : String

)
