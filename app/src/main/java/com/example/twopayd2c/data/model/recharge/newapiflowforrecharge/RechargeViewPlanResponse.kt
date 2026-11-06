package com.example.twopayd2c.data.model.recharge.newapiflowforrecharge

import com.google.gson.annotations.SerializedName

data class RechargeViewPlanResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("Status")
	val statusBos: String? = "",

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class JioLinkItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class DataPacksItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class GamingItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class TopUpItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class EntertainmentPlansItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class ValueItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class AnnualPlansItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class InFlightPacksItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class TrueUnlimitedUpgradeItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class InternationalRoamingItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class PopularPlansItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class IRWiFiCallingItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class JioPhoneDataAddOnItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class JioSaavnProItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class ISDItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class JioBharatPhoneItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class JioPhoneItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class True5GUnlimitedPlansItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)


data class Data(

	@field:SerializedName("Popular Plans")
	val popularPlans: List<PopularPlansItem?>? = null,

	@field:SerializedName("Data Packs")
	val dataPacks: List<DataPacksItem?>? = null,

	@field:SerializedName("IR Wi-Fi Calling")
	val iRWiFiCalling: List<IRWiFiCallingItem?>? = null,

	@field:SerializedName("JioBharat Phone")
	val jioBharatPhone: List<JioBharatPhoneItem?>? = null,

	@field:SerializedName("Jio Phone Prima")
	val jioPhonePrima: List<JioPhonePrimaItem?>? = null,

	@field:SerializedName("In-Flight Packs")
	val inFlightPacks: List<InFlightPacksItem?>? = null,

	@field:SerializedName("JioLink")
	val jioLink: List<JioLinkItem?>? = null,

	@field:SerializedName("True Unlimited Upgrade")
	val trueUnlimitedUpgrade: List<TrueUnlimitedUpgradeItem?>? = null,

	@field:SerializedName("Annual Plans")
	val annualPlans: List<AnnualPlansItem?>? = null,

	@field:SerializedName("International Roaming")
	val internationalRoaming: List<InternationalRoamingItem?>? = null,

	@field:SerializedName("Top-up")
	val topUp: List<TopUpItem?>? = null,

	@field:SerializedName("Entertainment Plans")
	val entertainmentPlans: List<EntertainmentPlansItem?>? = null,

	@field:SerializedName("Value")
	val value: List<ValueItem?>? = null,

	@field:SerializedName("JioPhone")
	val jioPhone: List<JioPhoneItem?>? = null,

	@field:SerializedName("True 5G Unlimited Plans")
	val true5GUnlimitedPlans: List<True5GUnlimitedPlansItem>? = null,

	@field:SerializedName("ISD")
	val iSD: List<ISDItem?>? = null,

	@field:SerializedName("Gaming")
	val gaming: List<GamingItem?>? = null,

	@field:SerializedName("JioPhone Data Add-on")
	val jioPhoneDataAddOn: List<JioPhoneDataAddOnItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("JioSaavn Pro")
	val jioSaavnPro: List<JioSaavnProItem?>? = null,

	@field:SerializedName("desc")
	val desc: Any? = null
)

data class JioPhonePrimaItem(

	@field:SerializedName("rs")
	val rs: Int? = null,

	@field:SerializedName("validity")
	val validity: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)
