package com.example.twopayd2c.data.model.recharge.plan


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("TOPUP") var topup: ArrayList<TOPUP> = arrayListOf(),
    @SerializedName("3G/4G") var g3G4G: ArrayList<ThreeGFourg> = arrayListOf(),
    @SerializedName("Roaming") var roaming: ArrayList<Romaing> = arrayListOf(),
    @SerializedName("COMBO") var combo: ArrayList<COMBO> = arrayListOf()

)