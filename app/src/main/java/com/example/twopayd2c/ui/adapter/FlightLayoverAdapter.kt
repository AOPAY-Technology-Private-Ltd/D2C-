package com.example.twopayd2c.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.data.model.travelmodel.flight.SegmentsItem
import com.example.twopayd2c.databinding.FlightPickupDropupPointlayoutBinding
import com.example.twopayd2c.databinding.FlightPicupDropItemBinding
import com.example.twopayd2c.databinding.FlightdetailsitemlayoutBinding
import com.example.twopayd2c.utils.ConstantClass.Companion.GetAirlineLogo
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateLayoverTime
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateTotalFlightDuration
import com.example.twopayd2c.utils.ConstantClass.Companion.className
import com.example.twopayd2c.utils.ConstantClass.Companion.formatDate1
import com.example.twopayd2c.utils.ConstantClass.Companion.formatDate2
import com.example.twopayd2c.utils.ConstantClass.Companion.splitDateTime
import com.example.twopayd2c.utils.ConstantClass.Companion.totalDurationTime

class FlightLayoverAdapter (private val context: Context, private var flightDetails :MutableList<FlightsItem?>, private var segmentDataList :MutableList<SegmentsItem?>):


    RecyclerView.Adapter<FlightLayoverAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = FlightPicupDropItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }



    override fun getItemCount(): Int {
       return segmentDataList.size
    }


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val segment = segmentDataList[position]

        if(segmentDataList.size==1){
            holder.planechangeLayoverlayout.visibility = View.GONE
            holder.layovericon.visibility = View.GONE
            holder.droppointicon.visibility = View.VISIBLE


            var airportIcon = GetAirlineLogo(segmentDataList[position]!!.airlineCode)
            Glide.with(context).load(airportIcon).error(R.drawable.airplaceholder).into(holder.airlineicon)

            holder.airlinename.text=segmentDataList[position]!!.airlineName.plus(" | ")
            holder.flightnumberwithcode.text= segmentDataList[position]!!.airlineCode.plus(" ").plus(segmentDataList[position]!!.flightNumber)

            var originTime = splitDateTime(segmentDataList[position]!!.departureDateTime)
            holder.origintime.text = originTime.second

            var originDate = formatDate1(originTime.first).plus(",") .plus(formatDate2(originTime.first))
            holder.origindate.text = originDate

            var destinationTime = splitDateTime(segmentDataList[position]!!.arrivalDateTime)
            holder.destinationtime.text = destinationTime.second

            var destinationDate = formatDate1(destinationTime.first).plus(",") .plus(formatDate2(destinationTime.first))
            holder.destinationdate.text = destinationDate

            holder.origincityname.text= segmentDataList[position]!!.originCity
            holder.destinationcityname.text=segmentDataList[position]!!.destinationCity

            holder.originterminal.text = "Terminal ".plus(segmentDataList[position]!!.originTerminal)
            holder.destinationterminal.text = "Terminal ".plus(segmentDataList[position]!!.destinationTerminal)



            val segments = listOf(mapOf("departure_DateTime" to segmentDataList[position]!!.departureDateTime, "arrival_DateTime" to segmentDataList[position]!!.arrivalDateTime))
            var detailsName = calculateTotalFlightDuration(segments)
             holder.durationtime.text = detailsName
            
        }
        else{

            // Show layover for all except the last item
            if (position < segmentDataList.size - 1) {
                val layover = calculateLayoverTime(segment!!.arrivalDateTime, segmentDataList[position + 1]!!.departureDateTime)
                holder.layoverPlace.text= segmentDataList[position + 1]!!.originCity
                holder.layovertime.text= layover
                holder.layovericon.visibility = View.VISIBLE
                holder.planechangeLayoverlayout.visibility = View.VISIBLE
                holder.droppointicon.visibility = View.GONE
            }
            else {
                holder.layovericon.visibility = View.GONE
                holder.droppointicon.visibility = View.VISIBLE
                holder.planechangeLayoverlayout.visibility = View.GONE
            }


            var airportIcon = GetAirlineLogo(segmentDataList[position]!!.airlineCode)
            Glide.with(context).load(airportIcon).error(R.drawable.airplaceholder).into(holder.airlineicon)

            holder.airlinename.text=segmentDataList[position]!!.airlineName.plus(" | ")
            holder.flightnumberwithcode.text= segmentDataList[position]!!.airlineCode.plus(" ").plus(segmentDataList[position]!!.flightNumber)

            var originTime = splitDateTime(segmentDataList[position]!!.departureDateTime)
            holder.origintime.text = originTime.second

            var originDate = formatDate1(originTime.first).plus(",") .plus(formatDate2(originTime.first))
            holder.origindate.text = originDate


            var destinationTime = splitDateTime(segmentDataList[position]!!.arrivalDateTime)
            holder.destinationtime.text = destinationTime.second

            var destinationDate = formatDate1(destinationTime.first).plus(",") .plus(formatDate2(destinationTime.first))
            holder.destinationdate.text = destinationDate

            holder.origincityname.text= segmentDataList[position]!!.originCity
            holder.destinationcityname.text=segmentDataList[position]!!.destinationCity

            holder.originterminal.text = "Terminal ".plus(segmentDataList[position]!!.originTerminal)
            holder.destinationterminal.text = "Terminal ".plus(segmentDataList[position]!!.destinationTerminal)



            val segments = listOf(mapOf("departure_DateTime" to segmentDataList[position]!!.departureDateTime, "arrival_DateTime" to segmentDataList[position]!!.arrivalDateTime))
            holder.durationtime.text= calculateTotalFlightDuration(segments)



        }


    }



    class ViewHolder(bind: FlightPicupDropItemBinding) : RecyclerView.ViewHolder(bind.root) {
        var airlineicon = bind.airlineicon
        var airlinename = bind.airlinename
        var flightnumberwithcode = bind.flightnumberwithcode
        var origintime = bind.origintime
        var origindate = bind.origindate
        var origincityname = bind.origincityname
        var originterminal = bind.originterminal
        var destinationtime = bind.destinationtime
        var destinationdate = bind.destinationdate
        var destinationcityname = bind.destinationcityname
        var destinationterminal = bind.destinationterminal
        var planechangeLayoverlayout = bind.planechangeLayoverlayout
        var layoverPlace = bind.layoverPlace
        var layovertime = bind.layovertime
        var durationtime = bind.durationtime

        var layovericon = bind.layovericon
        var droppointicon = bind.droppointicon

    }



}