package com.example.twopayd2c.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.AirTicketSegmentsItem
import com.bos.bos.app.ui.view.travel.airfragment.CancelledRefundFlight
import com.bos.bos.app.ui.view.travel.airfragment.CompletedAir
import com.bos.bos.app.ui.view.travel.airfragment.UpcomingAir
import com.bumptech.glide.Glide
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketPaxDetailsForTicketCancelModel
import com.example.twopayd2c.databinding.FlightCancelTicketLayoutBinding
import com.example.twopayd2c.utils.ConstantClass.Companion.GetAirlineLogo
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateTotalFlightDuration
import com.example.twopayd2c.utils.ConstantClass.Companion.convertDurationToReadableFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AirTicketCompletedAdapter(private var context: Context, private var passangerList: MutableList<com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.DataItem>, var fragment: CompletedAir) :
    RecyclerView.Adapter<AirTicketCompletedAdapter.ViewHolder>() {

    var headers: Array<String> = arrayOf("Pax ID", "Pax Name", "Ticket No", "Status")
    var checkarrow :Boolean = false
    val selectedPositions : MutableList<Int> = mutableListOf()
    private var expandedPosition = -1
    var segmentList : MutableList<AirTicketSegmentsItem> = mutableListOf()

    companion object{
        var paxList : MutableList<AirTicketPaxDetailsForTicketCancelModel> = mutableListOf()
    }

    inner class ViewHolder(val binding: FlightCancelTicketLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var pnr_No = binding.pnrNo
        var booking_refNo = binding.bookingRefNo
        var fromtodestination = binding.fromtodestination
        var viewTicketLayout = binding.viewticketLayout
        var flightdetailslayout = binding.flightdetailslayout
        var airlineimage = binding.airlineimage
        var airlinenamecode = binding.airlinenamecode
        var origincity = binding.origincity
        var destinationcity = binding.destinationcity
        var startdate = binding.startdate
        var enddate = binding.enddate
        var startterminal = binding.startterminal
        var destinationterminal = binding.destinationterminal
        var durationtime = binding.durationtime
        var ticketstatus = binding.status
        var passangerListTable = binding.passangerList
        var viewtxt = binding.viewtxt


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = FlightCancelTicketLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }



    override fun getItemCount(): Int {
        return passangerList.size
    }



    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        segmentList.clear()
        paxList.clear()

        holder.viewtxt.text="VIEW TICKET"
        holder.pnr_No.text=(passangerList.get(position).airPNR)
        holder.booking_refNo.text=(passangerList.get(position).bookingRefNo)
        holder.ticketstatus.text = (passangerList.get(position).ticketStatusDesc)
        holder.fromtodestination.text = passangerList.get(position).origin . plus(" -> ") . plus(passangerList.get(position).destination)

        var airportIcon = GetAirlineLogo(passangerList[position].apiData.airPNRDetails!![0]!!.airlineCode)
        Glide.with(context).load(airportIcon).into(holder.airlineimage)
        holder.airlinenamecode.text = passangerList[position].apiData.airPNRDetails!![0].flights!![0].segments!![0].airlineName.plus(" ") .plus(passangerList[position].apiData.airPNRDetails!![0]!!.flights!![0].segments!![0].flightNumber)
        holder.origincity.text =  passangerList.get(position).origin
        holder.destinationcity.text = passangerList.get(position).destination

        passangerList[position].apiData.airPNRDetails!![0].flights!![0].segments?.let { segmentList.addAll(it) }

        if(segmentList.size==1){
            holder.startdate.text= segmentList[segmentList.size-1].departureDateTime
            holder.enddate.text= segmentList[segmentList.size-1].arrivalDateTime
            var duration= convertDurationToReadableFormat(segmentList[segmentList.size-1].duration, addHours = 1)
            holder.durationtime.text= duration
            holder.startterminal.text = "Terminal" . plus(segmentList[segmentList.size-1].originTerminal)
            holder.destinationterminal.text = "Terminal" . plus(segmentList[segmentList.size-1].destinationTerminal)
        }
        else{
            holder.startdate.text= segmentList[0].departureDateTime
            holder.enddate.text= segmentList[segmentList.size-1].arrivalDateTime
            val segments = listOf(mapOf("departure_DateTime" to segmentList[0].departureDateTime, "arrival_DateTime" to segmentList[0].arrivalDateTime), mapOf("departure_DateTime" to segmentList[segmentList.size-1].departureDateTime, "arrival_DateTime" to segmentList[segmentList.size-1].arrivalDateTime))
            holder.durationtime.text= calculateTotalFlightDuration(segments)
            holder.startterminal.text = "Terminal " . plus(segmentList[0].originTerminal)
            holder.destinationterminal.text = "Terminal " . plus(segmentList[segmentList.size-1].destinationTerminal)
        }


        holder.flightdetailslayout.visibility = if (passangerList[position].isCardVisible) View.VISIBLE else View.GONE

        holder.viewTicketLayout.setOnClickListener {
            passangerList[position].isCardVisible = !passangerList[position].isCardVisible
            holder.flightdetailslayout.visibility= View.VISIBLE
            notifyItemChanged(position)
        }

        var passangerDetails =  passangerList[position].apiData.airPNRDetails!![0].paxTicketDetails

        if(passangerDetails!=null)
        passangerDetails!!.forEach { data->
            var paxname = data.firstName . plus(" "). plus(data.lastName)
            try {
                var ticketnumber=""
                if(null!=data.ticketDetails!![0].ticketNumber){
                    ticketnumber = data.ticketDetails!![0].ticketNumber
                }
                paxList.add(AirTicketPaxDetailsForTicketCancelModel(data.paxId,paxname,ticketnumber,data.ticketStatus))
            }
            catch (e: Exception){
                e.message
            }

        }

        holder.passangerListTable.removeAllViews()

        val headerRow = TableRow(context)

        for (header in headers) {
            val textView = TextView(context)
            textView.text = header
            textView.setPadding(8, 8, 8, 8)
            textView.setBackgroundColor(Color.parseColor("#E0E0E0"))
            textView.setTextColor(Color.BLACK)
            textView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.dimen_12sp)
            )
            textView.setTypeface(textView.typeface, Typeface.BOLD)
            textView.gravity = Gravity.START
            textView.layoutParams =
                TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            headerRow.addView(textView)
        }

        holder.passangerListTable.addView(headerRow)

        paxList.forEachIndexed { index, row ->
            val tableRow = TableRow(context)

            // Remaining columns
            listOf(row.paxId, row.paxName, row.ticketNumber,row.ticketstatus).forEach { data ->
                val textView = TextView(context).apply {
                    text = data
                    setPadding(8, 8, 8, 8)
                    setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        context.resources.getDimension(R.dimen.dimen_12sp)
                    )
                    setTextColor(Color.BLACK)
                    gravity = Gravity.START
                    layoutParams =
                        TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                }
                tableRow.addView(textView)
            }

            holder.passangerListTable.addView(tableRow)

        }


    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateDuration(departure: String?, arrival: String?): String {

        val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

        val depTime = LocalTime.parse(departure!!.uppercase(), formatter)
        val arrTime = LocalTime.parse(arrival!!.uppercase(), formatter)

        // Use today's date for both times
        val today = LocalDate.now()
        var depDateTime = LocalDateTime.of(today, depTime)
        var arrDateTime = LocalDateTime.of(today, arrTime)

        // If arrival is before departure, it must be on the next day
        if (arrDateTime.isBefore(depDateTime)) {
            arrDateTime = arrDateTime.plusDays(1)
        }

        val duration = java.time.Duration.between(depDateTime, arrDateTime)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()

        return String.format("%02d h %02d m", hours, minutes)
    }




}