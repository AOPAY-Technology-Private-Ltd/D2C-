package com.example.twopayd2c.data.model.recharge.newapiflowforrecharge

import com.google.gson.annotations.SerializedName

data class RechargePlanReq(
    @SerializedName("RegistrationID")
    var registrationID:String,

    @SerializedName("circle")
    var circle:String,

    @SerializedName("operator")
    var operator:String,

    @SerializedName("number")
    var number:String

)
