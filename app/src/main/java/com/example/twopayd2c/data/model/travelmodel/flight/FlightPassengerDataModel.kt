package com.example.twopayd2c.data.model.travelmodel.flight

data class FlightPassengerDataModel(
    var gender : String,
    var genderType : String,
    var passengerName : String,
    var passengerLName : String,
    var age : String,
    var passportNo : String ,
    var passportIssueCountryName : String,
    var passportExpiryDate : String,
    var typeOfPassenger : String,
    var checked :Boolean
)
