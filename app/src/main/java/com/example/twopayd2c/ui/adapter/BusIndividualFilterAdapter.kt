package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.databinding.BusFilterIndividualItemLayoutBinding
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.BusFilterIndividualBottomSheet.BusIndividualFilterType
import com.example.twopayd2c.utils.ConstantClass.Companion.after12pm
import com.example.twopayd2c.utils.ConstantClass.Companion.after6am
import com.example.twopayd2c.utils.ConstantClass.Companion.after6pm
import com.example.twopayd2c.utils.ConstantClass.Companion.before6am
import java.util.Calendar
import java.util.Date


class BusIndividualFilterAdapter(private val context: Context, private val busFilterList: MutableList<Pair<String, Boolean>>, private val filterType: BusIndividualFilterType, private val listener: OnClickListener) : RecyclerView.Adapter<BusIndividualFilterAdapter.CustomViewHolder>() {


    inner class CustomViewHolder(val binding: BusFilterIndividualItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = BusFilterIndividualItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }


    override fun getItemCount(): Int = busFilterList.size


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val (name, isChecked) = busFilterList[position]


        holder.binding.checkbox.apply {
            if(filterType == BusIndividualFilterType.DEPARTURE) {
                val parts = name.split(",")
                val busDepartureDate = parts.getOrNull(1)
                text = parts[0]
                // Prevent unwanted callbacks
                setOnCheckedChangeListener(null)
                this.isChecked = isChecked

                val currentCal = Calendar.getInstance()
                val departureCal = Calendar.getInstance().apply {
                    time = Date(busDepartureDate)
                }
                val isSameDay = currentCal.get(Calendar.YEAR) == departureCal.get(Calendar.YEAR) && currentCal.get(Calendar.DAY_OF_YEAR) == departureCal.get(Calendar.DAY_OF_YEAR)
                val currentHour = currentCal.get(Calendar.HOUR_OF_DAY)


                 // ✅ Apply Time Disable Logic (Only for Departure Filter)

                 if (busDepartureDate != null) {
                     try {
                             val shouldEnable = if (isSameDay) {

                                 val slotStartHour = when (parts[0]) {
                                     before6am -> 6
                                     after6am -> 12
                                     after12pm -> 18
                                     after6pm -> 24
                                     else -> 0
                                 }
                                 currentHour < slotStartHour

                             } else {
                                 true
                             }

                             isEnabled = shouldEnable
                             alpha = if (shouldEnable) 1f else 0.5f

                     } catch (e: Exception) {
                         isEnabled = false
                         alpha = 0.5f
                     }
                 }

                setOnCheckedChangeListener { _, checked ->
                    if (!isEnabled) return@setOnCheckedChangeListener

                        if (checked) {
                            // 2. Uncheck all other items in the data list
                            for (i in busFilterList.indices) {
                                busFilterList[i] = busFilterList[i].first to (i == position)
                            }
                            // 3. Refresh the UI to reflect the single selection
                            notifyDataSetChanged()
                            // 4. Send the single selected item back to the activity
                            listener.setonclicklistner(listOf(parts[0]), filterType)
                        } else {
                            // Optional: If you want to allow "None" selected,
                            // set the value to false and update.
                            busFilterList[position] = name to false
                            listener.setonclicklistner(emptyList(), filterType)
                        }

                }

            }
            else{
                text = name
                // Prevent unwanted callbacks
                setOnCheckedChangeListener(null)
                this.isChecked = isChecked

                setOnCheckedChangeListener { _, checked ->
                    if(filterType== BusIndividualFilterType.BUSTYPE){
                        if (checked) {
                            // 2. Uncheck all other items in the data list
                            for (i in busFilterList.indices) {
                                busFilterList[i] = busFilterList[i].first to (i == position)
                            }
                            // 3. Refresh the UI to reflect the single selection
                            notifyDataSetChanged()
                            // 4. Send the single selected item back to the activity
                            listener.setonclicklistner(listOf(name), filterType)
                        } else {
                            // Optional: If you want to allow "None" selected,
                            // set the value to false and update.
                            busFilterList[position] = name to false
                            listener.setonclicklistner(listOf(), filterType)
                        }


                    }else{
                        busFilterList[position] = name to checked

                        // Send selected list only on user action
                        listener.setonclicklistner(busFilterList.filter { it.second }.map { it.first }, filterType)
                    }

                }
            }


        }

    }


    interface OnClickListener {
        fun setonclicklistner(selectedNames: List<String>,filterType: BusIndividualFilterType)
    }


}
