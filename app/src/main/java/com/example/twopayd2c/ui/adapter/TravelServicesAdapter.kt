package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.ServicesListModel
import com.example.twopayd2c.databinding.TravelServiceItemLayoutBinding
import com.example.twopayd2c.ui.view.activity.travel.TravelBookingHomePage
import com.example.twopayd2c.ui.view.activity.travel.fragment.BusSearchHomePageFragment
import com.example.twopayd2c.ui.view.activity.travel.fragment.FlightSearchHomePageFragment
import com.example.twopayd2c.ui.view.activity.travel.fragment.TrainSearchHomePageFragment


class TravelServicesAdapter(var servicesList:List<ServicesListModel>, var context: Context, private val activity: AppCompatActivity) : RecyclerView.Adapter<TravelServicesAdapter.ViewHolder>(){
    var selectionPosition = -1
    var default = 0
    class ViewHolder (var binding : TravelServiceItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = TravelServiceItemLayoutBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(bin)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.servicesImage.setImageResource(servicesList[position].image)
        holder.binding.servicename.text = servicesList[position].name

        if(position==selectionPosition){
            holder.binding.servicename.setTextColor(context.resources.getColor(R.color.black))
            holder.binding.servicesImage.setPadding(8,8,8,8)
            holder.binding.servicesImage.alpha = 1f
            holder.binding.cardview.visibility = View.VISIBLE
        }
        else {
            holder.binding.servicename.setTextColor(context.resources.getColor(R.color.disablebuttoncolor))
            holder.binding.servicesImage.setPadding(15,15,15,15)
            holder.binding.servicesImage.alpha = 0.7f
            holder.binding.cardview.visibility = View.INVISIBLE
        }

        holder.itemView.setOnClickListener {

            selectionPosition = position
            notifyDataSetChanged()


           if(servicesList[position].name.equals(context.getString(R.string.flight)))
           {
               if (activity is TravelBookingHomePage) {
                   activity.callFragment(FlightSearchHomePageFragment(), "fight",servicesList[position].featurecode,servicesList[position].activeYN,"FlightMainFragment")
               }
           }


            if(servicesList[position].name.equals(context.getString(R.string.train)))
            {
                if (activity is TravelBookingHomePage) {
                    activity.callFragment(TrainSearchHomePageFragment(), "fight",servicesList[position].featurecode,servicesList[position].activeYN,"FlightMainFragment")
                }
            }


           if(servicesList[position].name.equals(context.getString(R.string.bus))){

               if (activity is TravelBookingHomePage) {
                   activity.callFragment(BusSearchHomePageFragment(), "bus",servicesList[position].featurecode,servicesList[position].activeYN,"BusMainFragment")
               }
           }




       }





    }


    fun updateViewSelection(serviceName: String) {
        val index = servicesList.indexOfFirst {
            it.name.equals(serviceName, ignoreCase = true)
        }

        if (index != -1) {
            selectionPosition = index
            notifyDataSetChanged()
        }
    }




    override fun getItemCount(): Int {
      return  servicesList.size
    }





}