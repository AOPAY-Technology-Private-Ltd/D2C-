package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.flight.PaXDetailsFlight
import com.example.twopayd2c.databinding.PassengerdetailsItemlayoutBinding

class PassengerDetailsForReviewTripAdapter(
    var context: Context,
    var passengerDataList: MutableList<PaXDetailsFlight>
) : RecyclerView.Adapter<PassengerDetailsForReviewTripAdapter.ViewHolder>() {


    class ViewHolder(var binding: PassengerdetailsItemlayoutBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerDetailsForReviewTripAdapter.ViewHolder {
        var view = PassengerdetailsItemlayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: PassengerDetailsForReviewTripAdapter.ViewHolder, position: Int) {
        holder.binding.userName.text = "${passengerDataList[position].title} ${passengerDataList[position].firstName} ${passengerDataList[position].lastName}"
        var gender = when (passengerDataList[position].gender) {
            0 -> "Male"
            1 -> "Female"
            else -> "Others"
        }
        holder.binding.gender.text = gender

        if(passengerDataList[position].age.isNullOrBlank()){
         holder.binding.yearcard.visibility = View.GONE
         holder.binding.age.visibility = View.GONE
        }
        else{
            holder.binding.yearcard.visibility = View.VISIBLE
            holder.binding.age.visibility = View.VISIBLE
        }
        holder.binding.age.text = "${passengerDataList[position].age} yrs"
        val paxType = when (passengerDataList[position].paxtype) {
             0 ->  "ADULT"
             1 ->  "CHILD"
              2 -> "INFANT"
               else -> "" // fallback default
        }
        holder.binding.travellertype.text =  paxType

    }


    override fun getItemCount(): Int {
        return passengerDataList.size
    }

}