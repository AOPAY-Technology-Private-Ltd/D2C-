package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

import com.google.gson.annotations.SerializedName


data class BusRequeryRes (

  @SerializedName("bookingChangeRequests" ) var bookingChangeRequests : ArrayList<String>                = arrayListOf(),
  @SerializedName("bookingDate"           ) var bookingDate           : String?                          = null,
  @SerializedName("bookingPaymentDetails" ) var bookingPaymentDetails : ArrayList<BookingPaymentDetails> = arrayListOf(),
  @SerializedName("booking_RefNo"         ) var bookingRefNo          : String?                          = null,
  @SerializedName("bus_Detail"            ) var busDetail             : BusDetail?                       = BusDetail(),
  @SerializedName("cancellationPolicy"    ) var cancellationPolicy    : String?                          = null,
  @SerializedName("companyDetail"         ) var companyDetail         : ArrayList<CompanyDetail>         = arrayListOf(),
  @SerializedName("corporatePaymentMode"  ) var corporatePaymentMode  : Int?                             = null,
  @SerializedName("customerDetail"        ) var customerDetail        : CustomerDetail?                  = CustomerDetail(),
  @SerializedName("noofPax"               ) var noofPax               : String?                          = null,
  @SerializedName("paX_Details"           ) var paXDetails            : ArrayList<PaXDetails>            = arrayListOf(),
  @SerializedName("response_Header"       ) var responseHeader        : ResponseHeader?                  = ResponseHeader(),
  @SerializedName("retailerDetail"        ) var retailerDetail        : RetailerDetail?                  = RetailerDetail(),
  @SerializedName("ticket_Status_Desc"    ) var ticketStatusDesc      : String?                          = null,
  @SerializedName("ticket_Status_Id"      ) var ticketStatusId        : String?                          = null,
  @SerializedName("transport_PNR"         ) var transportPNR          : String?                          = null,
  @SerializedName("statuss"               ) var statuss               : String?                          = null,
  @SerializedName("message"               ) var message               : String?                          = null,
  @SerializedName("value"                 ) var value                 : String?                          = null

)