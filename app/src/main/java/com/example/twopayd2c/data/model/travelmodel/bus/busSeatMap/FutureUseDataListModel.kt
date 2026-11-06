package com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap


data class SelectedSeatWithFutureList(
    val selectedSeatPosition: Int,
    val futureList: MutableList<FutureUseDataListModel>
)

data class FutureUseDataListModel(
    var userName: String,
    var age : String,
    var gender: String,
    var seatNumber : String,
    var checked: Boolean = false
)
