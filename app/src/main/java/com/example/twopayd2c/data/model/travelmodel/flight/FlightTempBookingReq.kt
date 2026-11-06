package com.example.twopayd2c.data.model.travelmodel.flight

import com.google.gson.annotations.SerializedName

data class FlightTempBookingReq(
    @SerializedName("customer_Mobile")
    val customerMobile: String = "",

    @SerializedName("passenger_Mobile")
    val passengerMobile: String = "",

    @SerializedName("whatsAPP_Mobile")
    val whatsAPPMobile: String = "",

    @SerializedName("passenger_Email")
    val passengerEmail: String = "",

    @SerializedName("gst")
    val gst: Boolean = false,

    @SerializedName("gsT_Number")
    val gsT_Number: String = "",

    @SerializedName("gsT_HolderName")
    val gsTHolderName: String = "",

    @SerializedName("gsT_Address")
    val gsTAddress: String = "",

    @SerializedName("costCenterId")
    val costCenterId: Int = 0,


    @SerializedName("projectId")
    val projectId: Int = 0,


    @SerializedName("bookingRemark")
    val bookingRemark: String = "",


    @SerializedName("corporateStatus")
    val corporateStatus: Int = 0,

    @SerializedName("corporatePaymentMode")
    val corporatePaymentMode: Int = 0,

    @SerializedName("missedSavingReason")
    val missedSavingReason: String = "",

    @SerializedName("corpTripType")
    val corpTripType: String = "",

    @SerializedName("corpTripSubType")
    val corpTripSubType: String = "",

    @SerializedName("tripRequestId")
    val tripRequestId: String = "",

    @SerializedName("bookingAlertIds")
    val bookingAlertIds: String = "",

    @SerializedName("iP_Address")
    val iPAddress: String? = "",

    @SerializedName("request_Id")
    val requestId: String? = "",

    @SerializedName("imeI_Number")
    val imeINumber: String = "",

    @SerializedName("registrationID")
    val registrationID: String? = "",

    @SerializedName("bookingFlightDetails")
    val bookingFlightDetails: MutableList<BookingFlightDetails> = mutableListOf() ,

    @SerializedName("paX_Details")
    val paX_Details: MutableList<PaXDetailsFlight> = mutableListOf()

)

data class BookingFlightDetails(
    @SerializedName("search_Key")
    val searchKey: String = "",

    @SerializedName("flight_Key")
    val flightKey: String ? = "",

    @SerializedName("bookingSSRDetails")
    val bookingSSRDetails: MutableList<BookingSSRDetails> = mutableListOf()
)


data class BookingSSRDetails(
    @SerializedName("Pax_Id")
    val PaxId: Int = 0,

    @SerializedName("SSR_Key")
    val SSRKey: String = ""
)



data class PaXDetailsFlight(
    @SerializedName("pax_Id")
    val pax_Id: Int = 0,
    @SerializedName("pax_type")
    val paxtype: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("first_Name")
    val firstName: String = "",
    @SerializedName("last_Name")
    val lastName: String = "",
    @SerializedName("gender")
    val gender: Int = 0,
    @SerializedName("age")
    val age: String = "",
    @SerializedName("dob")
    val dob: String = "",
    @SerializedName("passport_Number")
    val passportNumber: String = "",
    @SerializedName("passport_Issuing_Country")
    val passportIssuingCountry: String = "",
    @SerializedName("passport_Expiry")
    val passportExpiry: String = "",
    @SerializedName("nationality")
    val nationality: String = "",
    @SerializedName("frequentFlyerDetails")
    val frequentFlyerDetails: String = "",

)
