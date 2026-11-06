package com.example.twopayd2c.data.model.recharge.recharge

import com.google.gson.annotations.SerializedName

data class DthInfoReq(
    @SerializedName("RegistrationID")
    var registrationId : String ?,

    @SerializedName("Circle")
    var circle : String,

    @SerializedName("Operator")
    var operator : String,

    @SerializedName("Opnumber")
    var opnumber : String,

)
