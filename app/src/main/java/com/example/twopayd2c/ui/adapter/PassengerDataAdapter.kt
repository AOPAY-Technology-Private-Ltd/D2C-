package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BoardingDropingModel
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.PassengerSelectedSeatDataModel
import com.example.twopayd2c.databinding.ItemBookingPassengerBinding

import com.example.twopayd2c.databinding.PickupdropuppointslayoutBinding
import com.example.twopayd2c.utils.ConstantClass

class PassengerDataAdapter(
    var context: Context,
    var list: MutableList<PassengerSelectedSeatDataModel>
) : RecyclerView.Adapter<PassengerDataAdapter.ViewHolder>() {


    class ViewHolder(var bin: ItemBookingPassengerBinding) : RecyclerView.ViewHolder(bin.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PassengerDataAdapter.ViewHolder {
        val bin = ItemBookingPassengerBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }

    override fun onBindViewHolder(holder: PassengerDataAdapter.ViewHolder, position: Int) {
        holder.bin.passengerName.text = list[position].passengerName
        holder.bin.seatnumber.text = "${list[position].seatNumber}"
        holder.bin.age.text = "${list[position].age} yr"
        holder.bin.gender.text = list[position].gender

    }

    override fun getItemCount(): Int {
        return list.size
    }

}