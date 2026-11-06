package com.example.twopayd2c.data.model.travelmodel.flight


data class PassangerDataList(
    var title: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var dob: String = "",
    var gender: String = "",
    var passangerType: String = "",
    var passportno: String = "",
    var passportissuecountryname: String = "",
    var passportexpirydate: String = "",
    var paxDetailsListFromReprice: MutableList<RequiredPAXDetailsItem> = mutableListOf()
)


data class TempBookingPassangerDetails(
    var passengerMobile: String = "",
    var whatsAPPMobile: String = "",
    var passengerEmail: String = "",
    var gst: Boolean = false,
    var gsT_Number: String = "",
    var gsTHolderName: String = "",
    var gsTAddress: String = "",
)



