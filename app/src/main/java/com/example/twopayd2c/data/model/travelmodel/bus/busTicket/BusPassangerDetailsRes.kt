package com.example.twopayd2c.data.model.travelmodel.bus.busTicket

import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.PaXDetails
import com.google.gson.annotations.SerializedName

data class BusPassangerDetailsRes(@SerializedName("returnCode")
                                  val returnCode: String = "",
                                  @SerializedName("data")
                                  val data: List<Data>?,
                                  @SerializedName("returnMessage")
                                  val returnMessage: String = "",
                                  @SerializedName("isSuccess")
                                  val isSuccess: Boolean = false)

data class Data(@SerializedName("booking_RefNo")
                    val bookingRefNo: String = "",
                    @SerializedName("ticket_Status_Id")
                    val ticketStatusId: String = "",
                    @SerializedName("loginID")
                    val loginID: String = "",
                    @SerializedName("apiData")
                    val apiData: ApiData,
                    @SerializedName("registrationID")
                    val registrationID: String = "",
                    @SerializedName("ticket_Status_Desc")
                    val ticketStatusDesc: String = "")

data class ApiData(@SerializedName("booking_RefNo")
                   val bookingRefNo: String = "",
                   @SerializedName("corporatePaymentMode")
                   val corporatePaymentMode: String = "",
                   @SerializedName("paX_Details")
                   val paXDetails: List<PaXDetailsItem>?,
                   @SerializedName("transport_PNR")
                   val transportPNR: String = "",
                   @SerializedName("retailerDetail")
                   val retailerDetail: RetailerDetail,
                   @SerializedName("companyDetail")
                   val companyDetail: List<CompanyDetailItem>?,
                   @SerializedName("response_Header")
                   val responseHeader: ResponseHeader,
                   @SerializedName("noofPax")
                   val noofPax: String = "",
                   @SerializedName("message")
                   val message: String = "",
                   @SerializedName("statuss")
                   val statuss: String = "",
                   @SerializedName("bookingPaymentDetails")
                   val bookingPaymentDetails: List<BookingPaymentDetailsItem>?,
                   @SerializedName("ticket_Status_Desc")
                   val ticketStatusDesc: String = "",
                   @SerializedName("ticket_Status_Id")
                   val ticketStatusId: String = "",
                   @SerializedName("customerDetail")
                   val customerDetail: CustomerDetail,
                   @SerializedName("bookingDate")
                   val bookingDate: String = "",
                   @SerializedName("bus_Detail")
                   val busDetail: BusDetail,
                   @SerializedName("value")
                   val value: String = "",
                   @SerializedName("cancellationPolicy")
                   val cancellationPolicy: String = "")


data class CustomerDetail(@SerializedName("customer_Email")
                          val customerEmail: String = "",
                          @SerializedName("customer_Mobile")
                          val customerMobile: String = "",
                          @SerializedName("customer_Name")
                          val customerName: String = "",
                          @SerializedName("customer_City")
                          val customerCity: String = "",
                          @SerializedName("customer_Address")
                          val customerAddress: String = "",
                          @SerializedName("customer_Id")
                          val customerId: Any? = null)

data class CompanyDetailItem(@SerializedName("country")
                             val country: String = "",
                             @SerializedName("pincode")
                             val pincode: String = "",
                             @SerializedName("website")
                             val website: String = "",
                             @SerializedName("address")
                             val address: String = "",
                             @SerializedName("city")
                             val city: String = "",
                             @SerializedName("panNo")
                             val panNo: String = "",
                             @SerializedName("companyName")
                             val companyName: String = "",
                             @SerializedName("sacCode")
                             val sacCode: String = "",
                             @SerializedName("gstNo")
                             val gstNo: String = "",
                             @SerializedName("contactPerson")
                             val contactPerson: String = "",
                             @SerializedName("mobileNo")
                             val mobileNo: String = "",
                             @SerializedName("phoneNo")
                             val phoneNo: String = "",
                             @SerializedName("logo")
                             val logo: String = "",
                             @SerializedName("state")
                             val state: String = "",
                             @SerializedName("fax")
                             val fax: String = "",
                             @SerializedName("email")
                             val email: String = "")

data class PaXDetailsItem(@SerializedName("fare")
                          val fare: Fare,
                          @SerializedName("gender")
                          val gender: String = "",
                          @SerializedName("penalty_Charge")
                          val penaltyCharge: Any? = null,
                          @SerializedName("ladies_Seat")
                          val ladiesSeat: Boolean = false,
                          @SerializedName("paX_Name")
                          val paXName: String = "",
                          @SerializedName("ticket_Number")
                          val ticketNumber: String = "",
                          @SerializedName("id_Type")
                          val idType: String = "",
                          @SerializedName("paX_Id")
                          val paXId: String = "",
                          @SerializedName("title")
                          val title: String = "",
                          @SerializedName("dob")
                          val dob: Any? = null,
                          @SerializedName("id_Number")
                          val idNumber: Any? = null,
                          @SerializedName("seat_Number")
                          val seatNumber: String = "",
                          @SerializedName("age")
                          val age: String = "",
                          @SerializedName("primary")
                          val primary: Boolean = false,
                          @SerializedName("status")
                          val status: String = "")


data class RetailerDetail(@SerializedName("email_Id")
                          val emailId: String = "",
                          @SerializedName("retailerServiceFee")
                          val retailerServiceFee: String = "",
                          @SerializedName("retailer_Id")
                          val retailerId: String = "",
                          @SerializedName("retailer_Name")
                          val retailerName: String = "",
                          @SerializedName("retailer_Landmark")
                          val retailerLandmark: String = "",
                          @SerializedName("operator_Name")
                          val operatorName: Any? = null,
                          @SerializedName("retailer_Address")
                          val retailerAddress: String = "",
                          @SerializedName("retailer_Area")
                          val retailerArea: String = "",
                          @SerializedName("retailer_Pincode")
                          val retailerPincode: String = "",
                          @SerializedName("retailer_PANno")
                          val retailerPANno: String = "",
                          @SerializedName("retailer_City")
                          val retailerCity: String = "",
                          @SerializedName("mobile_Number")
                          val mobileNumber: String = "",
                          @SerializedName("retailer_GSTno")
                          val retailerGSTno: String = "",
                          @SerializedName("retailer_State")
                          val retailerState: String = "",
                          @SerializedName("retailer_CompanyName")
                          val retailerCompanyName: String = "",
                          @SerializedName("retailer_Email")
                          val retailerEmail: String = "")


data class BusDetail(@SerializedName("droppingDetails")
                     val droppingDetails: DroppingDetails,
                     @SerializedName("ac")
                     val ac: Boolean = false,
                     @SerializedName("supplier_RefNo")
                     val supplierRefNo: String = "",
                     @SerializedName("operator_Id")
                     val operatorId: String = "",
                     @SerializedName("live_Tracking")
                     val liveTracking: Boolean = false,
                     @SerializedName("lastMinute_Zero_Cancellation")
                     val lastMinuteZeroCancellation: Boolean = false,
                     @SerializedName("mTicket")
                     val mTicket: Boolean = false,
                     @SerializedName("departure_Time")
                     val departureTime: String = "",
                     @SerializedName("operator_Name")
                     val operatorName: String = "",
                     @SerializedName("arrival_Time")
                     val arrivalTime: String = "",
                     @SerializedName("seat_Type")
                     val seatType: String = "",
                     @SerializedName("free_WIFI")
                     val freeWIFI: Boolean = false,
                     @SerializedName("bus_Type")
                     val busType: String = "",
                     @SerializedName("travelDate")
                     val travelDate: String = "",
                     @SerializedName("boardingDetails")
                     val boardingDetails: BoardingDetails,
                     @SerializedName("from_City")
                     val fromCity: String = "",
                     @SerializedName("to_City")
                     val toCity: String = "",
                     @SerializedName("vehicle_Type")
                     val vehicleType: String = "",
                     @SerializedName("partial_Cancellation_Allowed")
                     val partialCancellationAllowed: Boolean = false,
                     @SerializedName("cancellationPolicy")
                     val cancellationPolicy: String = "",
                     @SerializedName("remarks")
                     val remarks: String = "")

data class DroppingDetails(@SerializedName("dropping_Id")
                           val droppingId: Any? = null,
                           @SerializedName("dropping_Landmark")
                           val droppingLandmark: Any? = null,
                           @SerializedName("dropping_Name")
                           val droppingName: String = "",
                           @SerializedName("filler")
                           val filler: Any? = null,
                           @SerializedName("dropping_Contact")
                           val droppingContact: String = "",
                           @SerializedName("dropping_Address")
                           val droppingAddress: String = "",
                           @SerializedName("dropping_Time")
                           val droppingTime: String = "")


data class Fare(@SerializedName("basic_Amount")
                val basicAmount: String = "",
                @SerializedName("net_Commission")
                val netCommission: String = "",
                @SerializedName("other_Amount")
                val otherAmount: String = "",
                @SerializedName("total_Amount")
                val totalAmount: String = "",
                @SerializedName("cancellation_Charges")
                val cancellationCharges: String = "",
                @SerializedName("gst")
                val gst: String = "",
                @SerializedName("trade_Markup_Amount")
                val tradeMarkupAmount: String = "",
                @SerializedName("gross_Commission")
                val grossCommission: String = "",
                @SerializedName("service_Fee_Amount")
                val serviceFeeAmount: String = "")

data class BoardingDetails(@SerializedName("boarding_Address")
                           val boardingAddress: String = "",
                           @SerializedName("boarding_Landmark")
                           val boardingLandmark: String = "",
                           @SerializedName("boarding_Time")
                           val boardingTime: String = "",
                           @SerializedName("filler")
                           val filler: Any? = null,
                           @SerializedName("boarding_Contact")
                           val boardingContact: String = "",
                           @SerializedName("boarding_Id")
                           val boardingId: Any? = null,
                           @SerializedName("boarding_Name")
                           val boardingName: String = "")

data class BookingPaymentDetailsItem(@SerializedName("gateway_Charges")
                                     val gatewayCharges: String = "",
                                     @SerializedName("payment_Mode")
                                     val paymentMode: String = "",
                                     @SerializedName("paymentConfirmation_Number")
                                     val paymentConfirmationNumber: String = "",
                                     @SerializedName("currency_Code")
                                     val currencyCode: String = "",
                                     @SerializedName("payment_Amount")
                                     val paymentAmount: String = "")


