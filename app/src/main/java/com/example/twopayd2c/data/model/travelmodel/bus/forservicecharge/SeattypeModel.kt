package com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge

data class SeattypeModel(
    var sleeper: Int ,
    var seater : Int ,
    var busType : String,
    var busfare : List<SeatFair>
)

data class SeatFair(
    var basicamount :Double,
    var otheramount : Double,
    var length : String ?
)

