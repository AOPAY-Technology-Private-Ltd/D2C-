package com.example.twopayd2c.data.model.travelmodel

import com.google.gson.annotations.SerializedName


data class RecentSearchListModel(

	@field:SerializedName("fromcity")
	val fromcity: String? = null,

	@field:SerializedName("fromcityid")
	val fromcityId: String? = null,

	@field:SerializedName("fromcountryname")
	val fromCountryName: String? = null,

	@field:SerializedName("searchdate")
	val searchdate: String? = null,

	@field:SerializedName("tocity")
	val tocity: String? = null,

	@field:SerializedName("tocountryname")
	val toCountryName: String? = null,

	@field:SerializedName("tocityid")
	val tocityId: String? = null
)
