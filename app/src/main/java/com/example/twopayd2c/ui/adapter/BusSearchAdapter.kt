package com.example.twopayd2c.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BoardingDropingModel
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.Buses
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.BusItemLayoutBinding
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSeating
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSeating.Companion.boardinglist
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSeating.Companion.dropinglist
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.generateRandomRating
import com.example.twopayd2c.utils.ConstantClass.Companion.getRatingColor
import com.example.twopayd2c.utils.SharedPreference
import kotlin.math.roundToInt


class BusSearchAdapter(private val context: Context, private val busList: MutableList<Buses>) : RecyclerView.Adapter<BusSearchAdapter.CustomViewHolder>() {
    var boardingPoint:MutableList<BoardingDropingModel> = mutableListOf()
    var droppingPoint:MutableList<BoardingDropingModel> = mutableListOf()
    lateinit var dropboardadapter : BoardingDropingAdapter
    var preference = SharedPreference(context)



    inner class CustomViewHolder(val bin: BusItemLayoutBinding): RecyclerView.ViewHolder(bin.root) {
        val companyName: TextView = itemView.findViewById(R.id.companyName)
        val busName: TextView = itemView.findViewById(R.id.busName)
        val arrivalTime: TextView = itemView.findViewById(R.id.arrivalTime)
        val availableSeat: TextView = itemView.findViewById(R.id.availableSeat)
        val amount: TextView = itemView.findViewById(R.id.travelAmount)
        val ratingcard: CardView = itemView.findViewById(R.id.ratingcard)
        val ratingcount: TextView = itemView.findViewById(R.id.ratingcount)
        val boardingarrowicon1: ImageView = itemView.findViewById(R.id.arrowicon1)
        val droppingarrowicon1: ImageView = itemView.findViewById(R.id.arrowicon2)
        val chair: ImageView = itemView.findViewById(R.id.chair)
        var boardingId: String?= null
        var droppingId: String?= null
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val bin = BusItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(bin)
    }


    override fun getItemCount(): Int {
        return busList.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val model = busList[position]
        val calculateTotalTime = ConstantClass.calculateTimeDifference(model.departureTime.toString(), model.arrivalTime.toString())

        val rating = generateRandomRating()
        holder.ratingcount.text = rating.toString()
        holder.ratingcard.setCardBackgroundColor(getRatingColor(rating))
        holder.companyName.text = model.operatorName.toString()
        holder.busName.text = model.busType.toString()
        val seats = model.availableSeats
        holder.availableSeat.text = "$seats Seat Left"
        when {
            seats!! <= 2 -> holder.availableSeat.setTextColor(ContextCompat.getColor(context, R.color.seat_red))
            seats!! <= 15 -> holder.availableSeat.setTextColor(ContextCompat.getColor(context, R.color.seat_orange))
            else -> holder.availableSeat.setTextColor(ContextCompat.getColor(context, R.color.seat_green))
        }

        val colorRes = when {
            seats <= 2 -> R.color.seat_red
            seats <= 15 -> R.color.seat_orange
            else -> R.color.seat_green
        }

        holder.chair.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))

        holder.arrivalTime.text = model.departureTime.toString() + " - ${calculateTotalTime} - " + model.arrivalTime.toString()

        val minAmount = model?.fareMasters
            ?.mapNotNull { it.basicAmount } // Convert to a list of non-null amounts
            ?.minOrNull() // Get the minimum value

        if (minAmount != null) {
            holder.amount.text = "₹${minAmount.roundToInt()}"
        }

        else {
            holder.amount.text = "₹0"
        }

        model.boardingDetails.forEach { boardingDetails ->
            holder.boardingId = boardingDetails.boardingId.toString()
        }

        model.droppingDetails.forEach { droppingId ->
            holder.droppingId = droppingId.droppingId.toString()
        }

        var click= false
        var amentclick= false

        holder.bin.selecteddropview.visibility=View.GONE
        holder.bin.pickupdroplayout.visibility= View.VISIBLE

        holder.bin.boardingarrowlayout.setOnClickListener {
            val model = busList[position]
            var boardinglist = model.boardingDetails
            var dropinglist = model.droppingDetails
            boardingPoint.clear()
            droppingPoint.clear()

            boardinglist.forEach { it->
                boardingPoint.add(BoardingDropingModel(it.boardingTime!!,it.boardingName!!,-1,it.boardingId.toString(),it.boardingTime!!))
            }


            dropinglist.forEach { it->
                droppingPoint.add(BoardingDropingModel(it.droppingTime!!,it.droppingName!!,-1,it.droppingId.toString(),it.droppingTime!!))
            }


            if(boardingPoint.size>0){
                setRecyclerviewData(holder.bin,boardingPoint)
            }


            holder.bin.boardtxt.setTextColor(context.getColor(R.color.primary_blue))
            holder.bin.droptxt.setTextColor(context.getColor(R.color.edtsubblack))
            holder.bin.selectedboardview.visibility=View.VISIBLE
            holder.bin.selecteddropview.visibility=View.GONE
            holder.bin.pickupdroplayout.visibility= View.VISIBLE

            holder.bin.arrowicon2.setImageResource(R.drawable.arrowdown)
            amentclick= false

            if(click){
                holder.bin.boardinglayout.visibility= View.GONE
                holder.bin.arrowicon1.setImageResource(R.drawable.arrowdown)
                click= false
            }
            else{
                holder.bin.boardinglayout.visibility= View.VISIBLE
                holder.bin.arrowicon1.setImageResource(R.drawable.arrowup)
                click= true
            }

        }

        holder.bin.amentiteslayout.setOnClickListener {
           var dataList = listOfAmentites()
            boardingPoint.clear()
            droppingPoint.clear()
            holder.bin.pickupdroplayout.visibility= View.GONE
            holder.bin.arrowicon1.setImageResource(R.drawable.arrowdown)
            click= false

            if(amentclick){
                holder.bin.boardinglayout.visibility= View.GONE
                holder.bin.arrowicon2.setImageResource(R.drawable.arrowdown)
                amentclick= false
            }
            else{
                holder.bin.boardinglayout.visibility= View.VISIBLE
                holder.bin.arrowicon2.setImageResource(R.drawable.arrowup)
                amentclick= true
            }

            if(dataList.size>0){
                setRecyclerviewData(holder.bin,dataList)
            }

        }

        holder.bin.boardtxt.setOnClickListener {
            if(boardingPoint.size>0){
                setRecyclerviewData(holder.bin,boardingPoint)
            }
            holder.bin.boardtxt.setTextColor(context.getColor(R.color.primary_blue))
            holder.bin.droptxt.setTextColor(context.getColor(R.color.edtsubblack))
            holder.bin.selectedboardview.visibility=View.VISIBLE
            holder.bin.selecteddropview.visibility=View.GONE
        }

        holder.bin.droptxt.setOnClickListener {
            if(droppingPoint.size>0){
                setRecyclerviewData(holder.bin,droppingPoint)
            }

            holder.bin.selectedboardview.visibility=View.GONE
            holder.bin.selecteddropview.visibility=View.VISIBLE
            holder.bin.boardtxt.setTextColor(context.getColor(R.color.edtsubblack))
            holder.bin.droptxt.setTextColor(context.getColor(R.color.primary_blue))
        }

        holder.itemView.setOnClickListener {
            try {
                preference.setStringValue(ConstantClass.droppingPoint, "")
                preference.setStringValue(ConstantClass.boardingPoint,"")

            val intent = Intent(context, BusSeating::class.java).apply {
                putExtra(ConstantClass.busKey, model.busKey.toString())
                putExtra(ConstantClass.boarding_Id, holder.boardingId.toString())
                putExtra(ConstantClass.dropping_Id, holder.droppingId.toString())
                putExtra(ConstantClass.travelCompanyName, model.operatorName.toString())
                putExtra(ConstantClass.travelTime, calculateTotalTime)
                putExtra(ConstantClass.busName, model.busType.toString())
                putExtra(ConstantClass.travelAmount, minAmount.toString())
                putExtra(ConstantClass.ratingCount,  holder.ratingcount.text.toString())
                putExtra(ConstantClass.arrivalTime, model.departureTime.toString() + " - " +calculateTotalTime+ " - " + model.arrivalTime.toString())

                preference.setStringValue(ConstantClass.boarding_Id,holder.boardingId.toString())
                preference.setStringValue(ConstantClass.dropping_Id,holder.droppingId.toString())

                Log.d("BoardPointSearch",holder.boardingId.toString())
                Log.d("DropPointSearch",holder.droppingId.toString())

                val model = busList[position]
                boardinglist = model.boardingDetails
                dropinglist = model.droppingDetails

            }
              context.startActivity(intent)

            }
            catch (e: IndexOutOfBoundsException){
                e.printStackTrace()
            }
        }

    }


    fun listOfAmentites():MutableList<BoardingDropingModel>{
        var datalist : MutableList<BoardingDropingModel> = mutableListOf()
        datalist.add(BoardingDropingModel("","Blanket",R.drawable.blancketicon,"",""))
        datalist.add(BoardingDropingModel("","Charging Point",R.drawable.chargepoints,"",""))
        datalist.add(BoardingDropingModel("","Emergency Exit",R.drawable.emergency_exit,"",""))
        datalist.add(BoardingDropingModel("","Fire Extinguisher",R.drawable.fire,"",""))
        datalist.add(BoardingDropingModel("","Hammer",R.drawable.hammer,"",""))
        datalist.add(BoardingDropingModel("","GPS",R.drawable.gpslocation,"",""))
        datalist.add(BoardingDropingModel("","CCTV",R.drawable.cctv,"",""))
        datalist.add(BoardingDropingModel("","First Aid Box",R.drawable.firstaidbox,"",""))

        return datalist
    }


    fun setRecyclerviewData(bin : BusItemLayoutBinding, list:MutableList<BoardingDropingModel>){
        dropboardadapter= BoardingDropingAdapter(context,list)
        bin.showpickupdroppoint.adapter = dropboardadapter
    }


}