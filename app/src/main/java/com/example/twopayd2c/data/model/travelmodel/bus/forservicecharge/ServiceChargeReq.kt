package com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge

import com.google.gson.annotations.SerializedName

data class ServiceChargeReq(

    @field:SerializedName("taskType")
    val taskType: String? = null,

    @field:SerializedName("adminCode")
    val adminCode: String? = null,

    @field:SerializedName("rid")
    val rid: Int? = null,

    @field:SerializedName("cancellationChargeType")
    val cancellationChargeType: String? = null,

    @field:SerializedName("cancellationChargeValue")
    val cancellationChargeValue: Int? = null,

    @field:SerializedName("actor")
    val actor: String? = null,

    @field:SerializedName("appMode")
    val appMode: String? = null

)
