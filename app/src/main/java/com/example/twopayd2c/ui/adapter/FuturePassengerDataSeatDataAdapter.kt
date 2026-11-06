package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.FutureUseDataListModel
import com.example.twopayd2c.databinding.PassengerSavedItemLayoutBinding
import com.example.twopayd2c.utils.ConstantClass


class FuturePassengerDataSeatDataAdapter(private val context: Context, private val futurePassengerData: MutableList<FutureUseDataListModel>,
                                         private val listener: OnClickListener) : RecyclerView.Adapter<FuturePassengerDataSeatDataAdapter.CustomViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION
    private var selectedSeatIsFemale: Boolean = false

    inner class CustomViewHolder(val binding: PassengerSavedItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = PassengerSavedItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }


    override fun getItemCount(): Int = futurePassengerData.size



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val item = futurePassengerData[position]

        holder.binding.userName.text = item.userName
        holder.binding.gender.text = item.gender
        holder.binding.age.text = "${item.age} yr"

        val isSeatAssigned = item.seatNumber.isNotEmpty()
        val isFemalePassenger = item.gender == ConstantClass.FemaleGender

        // ✅ If seat already assigned → disable
        if (isSeatAssigned) {
            holder.binding.radiobutton.isEnabled = false
            holder.binding.radiobutton.isChecked = true
            setAlphaOnView(holder.binding, 0.5f)
        }
        else {
            // ✅ If selected seat is female → allow only female passengers
            if (selectedSeatIsFemale && !isFemalePassenger) {
                holder.binding.radiobutton.isEnabled = false
                holder.binding.radiobutton.isChecked = false
                setAlphaOnView(holder.binding, 0.3f)
            } else {
                holder.binding.radiobutton.isEnabled = true
                holder.binding.radiobutton.isChecked = false
                setAlphaOnView(holder.binding, 1f)
            }
        }

        // Seat visibility
        if (isSeatAssigned) {
            holder.binding.seatassigned.visibility = View.VISIBLE
            holder.binding.seat.visibility = View.VISIBLE
            holder.binding.seat.text = item.seatNumber
        } else {
            holder.binding.seatassigned.visibility = View.GONE
            holder.binding.seat.visibility = View.GONE
        }

        holder.binding.radiobutton.setOnClickListener {
            if (!isSeatAssigned) {
                listener.setRadioonclicklistner(item, position)
            }
        }

        holder.binding.edititem.setOnClickListener {
            listener.setForEditonclicklistner(item,position)
        }

        holder.binding.deleteitem.setOnClickListener {
            listener.setForDeleteonclicklistner(item,position)
        }
    }


    fun setAlphaOnView(binding: PassengerSavedItemLayoutBinding,alpha:Float){
        binding.radiobutton.alpha= alpha
        binding.userName.alpha= alpha
        binding.genderagelayout.alpha= alpha
    }

    fun updateItem(position: Int, updatedModel: FutureUseDataListModel) {
        futurePassengerData[position] = updatedModel
        notifyItemChanged(position)
    }


    interface OnClickListener {
        fun setForEditonclicklistner(item:FutureUseDataListModel,position: Int)
        fun setForDeleteonclicklistner(item:FutureUseDataListModel,position: Int)
        fun setRadioonclicklistner(item:FutureUseDataListModel,position: Int)
    }


    fun updateSelectedSeatGender(isFemale: Boolean) {
        selectedSeatIsFemale = isFemale
        notifyDataSetChanged()
    }


}
