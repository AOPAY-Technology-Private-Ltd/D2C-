package com.example.twopayd2c.data.model
import com.google.gson.annotations.SerializedName
data class RetailerWiseServicesResponse(

    @field:SerializedName("returnCode")
    val returnCode: String? = null,

    @field:SerializedName("data")
    val data: List<RetailerWiseServicesDataItem?>? = null,

    @field:SerializedName("returnMessage")
    val returnMessage: String? = null,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean? = null
)


data class RetailerWiseServicesDataItem(

    @field:SerializedName("activeYN")
    val activeYN: String? = null,

    @field:SerializedName("featureCode")
    val featureCode: String? = null,

    @field:SerializedName("featureName")
    val featureName: String? = null
)
