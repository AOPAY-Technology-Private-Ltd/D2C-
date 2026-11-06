package com.example.twopayd2c.data.model.idfcPayout

import com.google.gson.annotations.SerializedName


data class InitiateAuthGenericFundTransferAPIResp (

  @SerializedName("metaData"     ) var metaData     : MetaData?     = MetaData(),
  @SerializedName("resourceData" ) var resourceData : ResourceData? = ResourceData()

)