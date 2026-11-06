package com.example.twopayd2c.data.model.travelmodel.flight

data class FlightTicketDataModel(
    var compnayName:String,
    var companyAddress:String,
    var gstno:String,
    var mail:String,
    var flightName:String,
    var flightcode:String,
    var PNR :String,
    var referenceNo:String,
    var issuedDate :String,
    var flightNo:String,
    var className:String,
    var origin:String,
    var destination:String,
    var originDateTime:String,
    var destinationDateTime:String,
    var duration:String,
    var status:String,
    var passangerMobileNo:String,
    var passangerEmailId:String,
    var paxDetails:MutableList<PaxDetails> = mutableListOf(),
    var baseFare:String,
    var taxesfees:String,
    var transactionfee:String,
    var GrossFare:String


)
data class PaxDetails(
    var ticketNo:String,
    var passangerName:String,
    var passangerType:String,
    var cabinweight:String,
    var checkinweight:String,
    var gender:String,
    var status:String
)

