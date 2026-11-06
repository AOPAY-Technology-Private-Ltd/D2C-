package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class AirReprintRespo(@SerializedName("airPNRDetails")
                              val airPNRDetails: MutableList<AirPNRDetailsItem?>,
                              @SerializedName("gst")
                              val gst: Boolean = false,
                              @SerializedName("remark")
                              val remark: String = "",
                              @SerializedName("child_Count")
                              val childCount: String = "",
                              @SerializedName("response_Header")
                              val responseHeader: ResponseHeader,
                              @SerializedName("booking_Type")
                              val bookingType: String = "",
                              @SerializedName("gstin")
                              val gstin: String = "",
                              @SerializedName("travel_Type")
                              val travelType: String = "",
                              @SerializedName("booking_DateTime")
                              val bookingDateTime: String = "",
                              @SerializedName("invoice_Number")
                              val invoiceNumber: String = "",
                              @SerializedName("biller_Id")
                              val billerId: String = "",
                              @SerializedName("adult_Count")
                              val adultCount: String = "",
                              @SerializedName("value")
                              val value: String = "",
                              @SerializedName("booking_RefNo")
                              val bookingRefNo: String = "",
                              @SerializedName("class_of_Travel")
                              val classOfTravel: String = "",
                              @SerializedName("corporatePaymentMode")
                              val corporatePaymentMode: String = "",
                              @SerializedName("retailerDetail")
                              val retailerDetail: RetailerDetail,
                              @SerializedName("companyDetail")
                              val companyDetail: CompanyDetail,
                              @SerializedName("paX_Mobile")
                              val paXMobile: String = "",
                              @SerializedName("message")
                              val message: String = "",
                              @SerializedName("statuss")
                              val statuss: String = "",
                              @SerializedName("airRequeryResponse_Key")
                              val airRequeryResponseKey: String = "",
                              @SerializedName("bookingPaymentDetail")
                              val bookingPaymentDetail: List<BookingPaymentDetailItem>?,
                              @SerializedName("infant_Count")
                              val infantCount: String = "",
                              @SerializedName("paX_EmailId")
                              val paXEmailId: String = "",
                              @SerializedName("customerDetail")
                              val customerDetail: CustomerDetail)