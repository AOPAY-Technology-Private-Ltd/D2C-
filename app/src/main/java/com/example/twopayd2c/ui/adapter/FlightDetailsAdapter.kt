package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.media3.datasource.DataSource

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.FaresItem
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.data.model.travelmodel.flight.SegmentsItem
import com.example.twopayd2c.databinding.FligetdetailsItemlayoutBinding
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.AddDetailsPassangerActivity
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightSearchDetails
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.FlightDetailsBottomSheet
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightDetails
import com.example.twopayd2c.utils.ConstantClass.Companion.GetAirlineLogo
import com.example.twopayd2c.utils.ConstantClass.Companion.animateCloseToTripCard
import com.example.twopayd2c.utils.ConstantClass.Companion.animateOpenFromTripCard
import com.example.twopayd2c.utils.ConstantClass.Companion.cabinBag
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateTotalFlightDuration
import com.example.twopayd2c.utils.ConstantClass.Companion.checkinBag
import com.example.twopayd2c.utils.ConstantClass.Companion.toInrFormat
import com.example.twopayd2c.utils.SharedPreference
import java.text.NumberFormat

import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

class FlightDetailsAdapter (private val context: Context, private var flightList: MutableList<FlightsItem>): RecyclerView.Adapter<FlightDetailsAdapter.ViewHolder>() {

        var segmentList : MutableList<SegmentsItem> = mutableListOf()
        var fareList : MutableList<FaresItem> = mutableListOf()
        var selectionPosition= -1
        private var preference: SharedPreference? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = FligetdetailsItemlayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }



    override fun getItemCount(): Int {
       return flightList.size
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        segmentList.clear()
        fareList.clear()
        preference = SharedPreference.getInstance(context)

        flightList[position].segments?.let { segmentList.addAll(it) }

        flightList[position].fares?.let { fareList.addAll(it) }

        holder.flightname.text= segmentList!![0].airlineName

        var airportIcon = GetAirlineLogo(flightList[position].airlineCode)

        Log.d("IMAGE_URL", airportIcon)

        Glide.with(context)
            .load(airportIcon)
            .error(R.drawable.airplaceholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("GLIDE_ERROR", "Error: ${e?.rootCauses}")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }


            })
            .into(holder.flighticon)

        if(segmentList.size==1){
            holder.fromcityname.text= segmentList[segmentList.size-1].originCity
            holder.tocityname.text= segmentList[segmentList.size-1].destinationCity
            holder.fromTime.text= ConstantClass.splitDateTime(segmentList[segmentList.size-1].departureDateTime).second
            holder.toTime.text= ConstantClass.splitDateTime(segmentList[segmentList.size-1].arrivalDateTime).second
            holder.stopsType.text="Non stop"
           //var duration= convertDurationToReadableFormat(segmentList[segmentList.size-1].duration)
            val segments = listOf(mapOf("departure_DateTime" to segmentList[segmentList.size-1].departureDateTime, "arrival_DateTime" to segmentList[segmentList.size-1].arrivalDateTime))
            holder.durationTime.text=  calculateTotalFlightDuration(segments)
        }
        else{
            holder.fromcityname.text= segmentList[0].originCity
            holder.tocityname.text= segmentList[segmentList.size-1].destinationCity
            holder.fromTime.text= ConstantClass.splitDateTime(segmentList[0].departureDateTime).second
            holder.toTime.text= ConstantClass.splitDateTime(segmentList[segmentList.size-1].arrivalDateTime).second
            holder.stopsType.text= (segmentList.size-1).toString().plus("stop")
            val segments = listOf(mapOf("departure_DateTime" to segmentList[0].departureDateTime, "arrival_DateTime" to segmentList[0].arrivalDateTime), mapOf("departure_DateTime" to segmentList[segmentList.size-1].departureDateTime, "arrival_DateTime" to segmentList[segmentList.size-1].arrivalDateTime))
            holder.durationTime.text= calculateTotalFlightDuration(segments)
        }

        val amount = fareList[0].fareDetails?.get(0)?.totalAmount?.toDoubleOrNull()?:0.0

        holder.price.text= amount.toInrFormat()
        holder.totalprice.text= amount.toInrFormat()
        holder.flightnumber.text= flightList[position].segments!![0].flightNumber


        fareList[0].fareDetails!![0]?.let {
            holder.cabinbag.text=it.freeBaggage?.handBaggage?.plus(" / Adult")?:"NA"
            holder.checkin.text=it.freeBaggage?.checkInBaggage?.plus(" / Adult")?:"NA"

        }

        var meal= fareList[0].foodOnboard.toString()

        if(meal.contains("F")||meal.contains("Free")||meal.contains("free")){
            holder.meallayout.visibility=View.VISIBLE
        }else{
            holder.meallayout.visibility=View.GONE
        }


        if (selectionPosition == position) {

            holder.booknowcard.visibility = View.VISIBLE

            animateOpenFromTripCard(holder.booknowcard, holder.firstcard)

            holder.maincard.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.accent_purple)
            )

            holder.maincard.cardElevation = 10f

        }
        else {

            animateCloseToTripCard(holder.booknowcard, holder.firstcard)

            holder.booknowcard.visibility = View.GONE

            holder.maincard.cardElevation = 0f

            holder.maincard.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.transparent)
            )
        }


        holder.firstcard.setOnClickListener {

            val previousSelection = selectionPosition

            selectionPosition = if (selectionPosition == position) {
                -1   // close if same item clicked
            } else {
                position   // open new item
            }

            if (previousSelection != -1) notifyItemChanged(previousSelection)
            if (selectionPosition != -1) notifyItemChanged(selectionPosition)
        }


        holder.booknow.setOnClickListener {
            FlightDetails.clear()
            FlightDetails.add(flightList[position])

            val fareDetail = flightList[position]
                .fares
                ?.firstOrNull()
                ?.fareDetails
                ?.firstOrNull()

            cabinBag = fareDetail?.freeBaggage?.handBaggage?.plus(" / Adult") ?: "NA"
            checkinBag = fareDetail?.freeBaggage?.checkInBaggage?.plus(" / Adult") ?: "NA"

            preference!!.setStringValue(ConstantClass.airlinecode,flightList[position].airlineCode)
            val bottomfrag = FlightDetailsBottomSheet()
            (context as FlightSearchDetails).supportFragmentManager.let { bottomfrag.show(it, FlightDetailsBottomSheet.TAG) }
        }


    }



    class ViewHolder(bind: FligetdetailsItemlayoutBinding) : RecyclerView.ViewHolder(bind.root) {
        var flightname = bind.flightName
        var fromTime = bind.fromtime
        var fromcityname = bind.fromcityName
        var toTime = bind.totime
        var tocityname = bind.tocityName
        var price = bind.price
        var flighticon = bind.flighticon
        var flightnumber = bind.flightnumber
        var stopsType = bind.stopsType
        var durationTime = bind.durationTime
        var firstcard = bind.triprelateddetailscard
        var maincard = bind.maincard
        var booknowcard = bind.booknowcard
        var cabinbag= bind.cabinbagweight
        var checkin= bind.checkinbagweight
        var meallayout= bind.meallayout
        var totalprice= bind.totalprice
        var booknow = bind.booknow

    }



}