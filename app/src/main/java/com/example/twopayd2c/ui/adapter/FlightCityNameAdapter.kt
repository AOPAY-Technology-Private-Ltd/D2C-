package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.bus.city.CityDetails
import com.example.twopayd2c.data.model.travelmodel.flight.DataItem
import com.example.twopayd2c.databinding.SearchFlightCityItemLayoutBinding
import com.example.twopayd2c.databinding.SearchcityItemLayoutBinding

class FlightCityNameAdapter(var context: Context, var citylist: List<DataItem>, private val listener: OnItemClickListener):RecyclerView.Adapter<FlightCityNameAdapter.ViewHolder>() {


    inner class ViewHolder (val binding: SearchFlightCityItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClickSearchCityItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = SearchFlightCityItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return citylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.cityname.text = "${citylist[position].city} (${citylist[position].airportCode})"
        holder.binding.citydescription.text = citylist[position].airportDescription
    }


    interface OnItemClickListener {
        fun onClickSearchCityItemClick(position: Int)
    }

}