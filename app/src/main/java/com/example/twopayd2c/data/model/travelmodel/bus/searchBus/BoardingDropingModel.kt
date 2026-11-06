package com.example.twopayd2c.data.model.travelmodel.bus.searchBus

import com.google.gson.annotations.SerializedName

data class BoardingDropingModel(
    var date : String,
    var boarddropname:String,
    var imageicon : Int,
    var boardingId : String,
    var boardingTime     : String
)
