package com.example.twopayd2c.data.model.travelmodel.bus.busRequery

data class TicketDetailsForGenerateTicket(
    val cancellationPolicy: String,
    val pnrNumber:String,
    val orderId:String,
    val departureFrom:String,
    val arrivalTo:String,
    val departureTime:String,
    val departureDate:String,
    val arrivalTime:String,
    val arrivalDate:String,
    val busOperatorName:String,
    val driverContactVehicleNumber:String,
    val boardingPoint:String,
    val droppingPoint:String,
    val reportingTime:String,
    val boardingTime:String,
    val busType:String,
    val SleeperFare:String,
    val SeaterFare:String,
    val operatorGST:String,
    val onwordFare:String,
    val convenienceFee:String,
    val promoDiscount:String,
    val grandTotal:String
)
