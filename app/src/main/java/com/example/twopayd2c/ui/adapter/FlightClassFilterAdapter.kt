package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.flight.FlightClassSelectionModel
import com.example.twopayd2c.databinding.BusFilterIndividualItemLayoutBinding
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.BusFilterIndividualBottomSheet.BusIndividualFilterType
import com.example.twopayd2c.utils.ConstantClass
import java.util.Calendar
import java.util.Date


class FlightClassFilterAdapter(private val context: Context, private val flightClassNameList: MutableList<FlightClassSelectionModel>, private val listener: OnClickListener) : RecyclerView.Adapter<FlightClassFilterAdapter.CustomViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class CustomViewHolder(val binding: BusFilterIndividualItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = BusFilterIndividualItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }


    override fun getItemCount(): Int = flightClassNameList.size



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val item = flightClassNameList[position]

        holder.binding.checkbox.apply {

            text = item.className

            // Remove old listener
            setOnCheckedChangeListener(null)

            // Set checked state based on selectedPosition
            isChecked = selectedPosition == position

            setOnCheckedChangeListener { _, checked ->
                if (checked) {

                    val previousPosition = selectedPosition
                    selectedPosition = holder.adapterPosition

                    listener.setonclicklistnerFlightClass(item)

                    // Update only changed items
                    if (previousPosition != -1) notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }


    fun upDateCheck(className: String) {
        val previousPosition = selectedPosition

        flightClassNameList.forEachIndexed { index, item ->
            if (item.className.equals(className, true)) {
                selectedPosition = index

                if (previousPosition != -1) notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                return
            }
        }
    }


    interface OnClickListener {
        fun setonclicklistnerFlightClass(item : FlightClassSelectionModel)
    }


}
