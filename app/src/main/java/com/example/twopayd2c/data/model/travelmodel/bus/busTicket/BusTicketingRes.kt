package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.google.gson.annotations.SerializedName


data class BusTicketingRes (

  @SerializedName("booking_RefNo"   ) var bookingRefNo   : String?         = null,
  @SerializedName("response_Header" ) var responseHeader : ResponseHeader? = ResponseHeader(),
  @SerializedName("supplier_Refno"  ) var supplierRefno  : String?         = null,
  @SerializedName("transport_PNR"   ) var transportPNR   : String?         = null,
  @SerializedName("statuss"         ) var statuss        : String?         = null,
  @SerializedName("message"         ) var message        : String?         = null,
  @SerializedName("value"           ) var value          : String?         = null

)