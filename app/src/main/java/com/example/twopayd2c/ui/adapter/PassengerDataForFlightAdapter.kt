package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BoardingDropingModel
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.PassengerSelectedSeatDataModel
import com.example.twopayd2c.data.model.travelmodel.flight.FlightPassengerDataModel
import com.example.twopayd2c.databinding.ItemBookingPassengerBinding
import com.example.twopayd2c.databinding.PassengerSavedFlightItemLayoutBinding

import com.example.twopayd2c.databinding.PickupdropuppointslayoutBinding
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.adultCount
import com.example.twopayd2c.utils.ConstantClass.Companion.childCount
import com.example.twopayd2c.utils.ConstantClass.Companion.infantCount

class PassengerDataForFlightAdapter(var context: Context, var list: MutableList<FlightPassengerDataModel>, var listner : setOnClickListner) : RecyclerView.Adapter<PassengerDataForFlightAdapter.ViewHolder>() {

    var selectedPosition :Int = RecyclerView.NO_POSITION
    class ViewHolder(var bin: PassengerSavedFlightItemLayoutBinding) : RecyclerView.ViewHolder(bin.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerDataForFlightAdapter.ViewHolder {
        val bin = PassengerSavedFlightItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }


    override fun onBindViewHolder(holder: PassengerDataForFlightAdapter.ViewHolder, position: Int) {
        var itemData = list[position]

        holder.bin.userName.text = "${list[position].genderType}${list[position].passengerName} ${list[position].passengerLName}"
        holder.bin.travellertype.text = "${list[position].typeOfPassenger}"
        holder.bin.gender.text = list[position].gender

        if(list[position].age.isNotEmpty()){
            holder.bin.yearcard.visibility= View.VISIBLE
            holder.bin.age.visibility= View.VISIBLE
            holder.bin.age.text = "${list[position].age} yr."
        }
        else{
            holder.bin.yearcard.visibility= View.GONE
            holder.bin.age.visibility= View.GONE
        }


        // radio checked state from model
        holder.bin.checkBox.isChecked = itemData.checked


        holder.bin.edititem.setOnClickListener {
           listner.clickOnEdit(position,itemData)
        }


        holder.bin.deleteitem.setOnClickListener {
            listner.clickOnDelete(position,itemData)
        }


       holder.bin.checkBox.setOnCheckedChangeListener { _, isChecked ->
           itemData.checked = isChecked
           listner. onPassengerCheckChanged(isChecked,itemData)
        }



    }


    override fun getItemCount(): Int {
        return list.size
    }


    interface  setOnClickListner{
        fun clickOnEdit(position:Int,item :FlightPassengerDataModel)
        fun clickOnDelete(position:Int,item: FlightPassengerDataModel)
        fun onPassengerCheckChanged(check: Boolean,item: FlightPassengerDataModel)
    }

}