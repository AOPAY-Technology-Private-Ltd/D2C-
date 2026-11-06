package com.example.twopayd2c.data.model.travelmodel.bus.busBooking

import com.google.gson.annotations.SerializedName


data class BusTempBookingReq(

  @SerializedName("boarding_Id"          ) var boardingId: String?               = null,
  @SerializedName("corporatePaymentMode" ) var corporatePaymentMode: Int?                  = null,
  @SerializedName("corporateStatus"      ) var corporateStatus: String?               = null,
  @SerializedName("costCenterId"         ) var costCenterId: Int?                  = null,
  @SerializedName("customer_Mobile"      ) var customerMobile: String?               = null,
  @SerializedName("deal_Key"             ) var dealKey: String?               = null,
  @SerializedName("dropping_Id"          ) var droppingId: String?               = null,
  @SerializedName("gst"                  ) var gst: Boolean?              = null,
  @SerializedName("gstin"                ) var gstin: String?               = null,
  @SerializedName("gstinHolderAddress"   ) var gstinHolderAddress: String?               = null,
  @SerializedName("gstinHolderName"      ) var gstinHolderName: String?               = null,
  @SerializedName("paX_Details"          ) var paXDetails: List<PaXDetails> = arrayListOf(),
  @SerializedName("Passenger_Email"      ) var PassengerEmail: String?               = null,
  @SerializedName("Passenger_Mobile"     ) var PassengerMobile: String?               = null,
  @SerializedName("ProjectId"            ) var ProjectId: Int?                  = null,
  @SerializedName("Remarks"              ) var Remarks: String?               = null,
  @SerializedName("Bus_Key"              ) var BusKey: String?               = null,
  @SerializedName("Search_Key"           ) var SearchKey: String?               = null,
  @SerializedName("SeatMap_Key"          ) var SeatMapKey: String?               = null,
  @SerializedName("sendEmail"            ) var sendEmail: Boolean?              = null,
  @SerializedName("sendSMS"              ) var sendSMS: Boolean?              = null,
  @SerializedName("iP_Address"           ) var iPAddress: String?               = null,
  @SerializedName("request_Id"           ) var requestId: String?               = null,
  @SerializedName("imeI_Number"          ) var imeINumber: String?               = null,
  @SerializedName("registrationID"       ) var registrationID: String?               = null

)