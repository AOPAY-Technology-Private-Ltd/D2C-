package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.flight.DataItem
import com.example.twopayd2c.databinding.AirportNameListLayoutBinding

class AirPortListAdapter (private val context: Context, private var offerList: MutableList<DataItem>, val btnlistener: setClickListner): RecyclerView.Adapter<AirPortListAdapter.ViewHolder>() {

    companion object {
        var mClickListener: setClickListner? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = AirportNameListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }


    fun updateList(newList: List<DataItem>) {
        offerList = newList as MutableList<DataItem>
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
       return offerList.size
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mClickListener = btnlistener
        holder.airportName.text= offerList.get(position).airportDescription
        holder.cityName.text=offerList.get(position).city
        holder.airportCode.text=offerList.get(position).airportCode

        holder.itemView.setOnClickListener {
            if(mClickListener!=null){
                mClickListener!!.itemclickListner(position,offerList.get(position))
            }
            notifyDataSetChanged()
        }

    }


    class ViewHolder(bind: AirportNameListLayoutBinding) : RecyclerView.ViewHolder(bind.root) {
        var cityName = bind.cityName
        var airportName = bind.airportName
        var airportCode = bind.airportCode
    }


   open interface setClickListner{
        fun itemclickListner(position:Int,item:DataItem)
    }


}