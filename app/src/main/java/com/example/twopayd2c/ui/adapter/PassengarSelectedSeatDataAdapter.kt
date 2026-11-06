package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.PassengerSelectedSeatDataModel
import com.example.twopayd2c.databinding.PassengerItemLayoutBinding


class PassengarSelectedSeatDataAdapter(private val context: Context, private val seatData: MutableList<PassengerSelectedSeatDataModel>,
                                       private val listener: OnClickListener) : RecyclerView.Adapter<PassengarSelectedSeatDataAdapter.CustomViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class CustomViewHolder(val binding: PassengerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = PassengerItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int = seatData.size


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.passengerSeatNo.text= "Seat No ${seatData[position].seatNumber}"
        if(selectedPosition== position){
            holder.binding.itemlayout.setBackgroundResource(R.drawable.border)
        }else{
            holder.binding.itemlayout.setBackgroundResource(R.drawable.editouterborder)
        }
        if(seatData[position].passengerName.isNullOrBlank()){
            holder.binding.checkImageView.visibility= View.GONE
            holder.binding.passengerName.text= "Passenger Name"
        }else{
            holder.binding.checkImageView.visibility= View.VISIBLE
            holder.binding.passengerName.text= seatData[position].passengerName
        }

        holder.itemView.setOnClickListener {
            selectedPosition = position
            listener.setonclicklistner(seatData[position],position)
            notifyDataSetChanged()
        }


    }

    interface OnClickListener {
        fun setonclicklistner(item:PassengerSelectedSeatDataModel,position: Int)

    }

    fun updateItem(position: Int, updatedModel: PassengerSelectedSeatDataModel) {
        seatData[position] = updatedModel
        notifyItemChanged(position)
    }

    fun updateCheckPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

}
