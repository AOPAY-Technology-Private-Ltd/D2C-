package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName


data class BusTempBookingRequest (
  @SerializedName("iP_Address"     ) var iPAddress      : String? = null,
  @SerializedName("request_Id"     ) var requestId      : String? = null,
  @SerializedName("imeI_Number"    ) var imeINumber     : String? = null,
  @SerializedName("corporatePaymentMode" ) var corporatePaymentMode : String? = null,
  @SerializedName("corporateStatus" ) var corporateStatus : String? = null,
  @SerializedName("costCenterId" ) var costCenterId : String? = null,
  @SerializedName("customer_Mobile" ) var customer_Mobile : String? = null,
  @SerializedName("deal_Key" ) var dealKey : String? = null,
  @SerializedName("boarding_Id" ) var boardingId : String? = null,
  @SerializedName("dropping_Id" ) var droppingId : String? = null,
  @SerializedName("gst" ) var gst : String? = null,
  @SerializedName("gstin" ) var gstin : String? = null,
  @SerializedName("gstinHolderAddress" ) var gstinHolderAddress : String? = null,
  @SerializedName("gstinHolderName" ) var gstinHolderName : String? = null,
  @SerializedName("bus_Key" ) var busKey : String? = null,
  @SerializedName("search_Key" ) var searchKey : String? = null,
  @SerializedName("seatMap_Key" ) var seatMapKey : String? = null,
  @SerializedName("sendEmail" ) var sendEmail : String? = null,
  @SerializedName("sendSMS" ) var sendSMS : String? = null,
  @SerializedName("createdBy" ) var createdBy : String? = null,
  @SerializedName("registrationID" ) var registrationID : String? = null,
  @SerializedName("loginId" ) var loginId : String? = null,
  @SerializedName("apiRequest" ) var apiRequest : String? = null

)