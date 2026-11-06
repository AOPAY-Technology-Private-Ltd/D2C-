package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class FareDetailsItem(@SerializedName("tds")
                           val tds: String = "",
                           @SerializedName("airportTaxes")
                           val airportTaxes: List<AirportTaxesItem>?,
                           @SerializedName("fareClasses")
                           val fareClasses: List<FareClassesItem>?,
                           @SerializedName("free_Baggage")
                           val freeBaggage: FreeBaggage,
                           @SerializedName("airportTax_Amount")
                           val airportTaxAmount: String = "",
                           @SerializedName("gst")
                           val gst: String = "",
                           @SerializedName("trade_Markup_Amount")
                           val tradeMarkupAmount: String = "",
                           @SerializedName("service_Fee_Amount")
                           val serviceFeeAmount: String = "",
                           @SerializedName("basic_Amount")
                           val basicAmount: String = "",
                           @SerializedName("net_Commission")
                           val netCommission: String = "",
                           @SerializedName("total_Amount")
                           val totalAmount: String = "",
                           @SerializedName("promo_Discount")
                           val promoDiscount: String = "",
                           @SerializedName("yQ_Amount")
                           val yQAmount: String = "",
                           @SerializedName("currency_Code")
                           val currencyCode: String = "",
                           @SerializedName("gross_Commission")
                           val grossCommission: String = "",
                           @SerializedName("paX_Type")
                           val paXType: String = "")