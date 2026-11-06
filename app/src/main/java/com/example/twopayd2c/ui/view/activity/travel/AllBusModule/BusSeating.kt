package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.BusSeatMapReq
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.BusSeatMapRes
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.SeatMap
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.SeatFair
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.SeattypeModel
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BoardingDetails
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BoardingDropingModel
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.DroppingDetails
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.PassengerSelectedSeatDataModel
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityBusSeatingBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.PickUpDropListAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.AddPassengerPage.Companion.seatDataList
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusConfirmBookingPage.Companion.rechargeAmount
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.ReviewBookingPage.Companion.forServiceChargeSeatType
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.getRatingColor
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson

class BusSeating : AppCompatActivity(), PickUpDropListAdapter.onClickListner {
    lateinit var binding : ActivityBusSeatingBinding
    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    private val selectedSeats = mutableListOf<SeatMap>()
    lateinit var selectedtypeface : Typeface
    lateinit var unselectedtypeface : Typeface
    var boardingPoint:MutableList<BoardingDropingModel> = mutableListOf()
    var droppingPoint:MutableList<BoardingDropingModel> = mutableListOf()


    companion object{
        var boardinglist:List<BoardingDetails> = arrayListOf()
        var dropinglist:List<DroppingDetails> = arrayListOf()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBusSeatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val secondaryBlue = ContextCompat.getColor(this, R.color.white)
        binding.main.setBackgroundColor(secondaryBlue)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom)

            // APPLY TOP PADDING: This prevents the overlap with the status bar
            view.setPadding(
                systemBars.left,
                systemBars.top,   // This fixes the overlap
                systemBars.right,
                bottomPadding
            )
            insets
        }

        preference = SharedPreference(this)
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]

        unselectedtypeface = ResourcesCompat.getFont(this, R.font.roboto_regular)!!
        selectedtypeface = ResourcesCompat.getFont(this, R.font.roboto_bold)!!

        setonCliskListner()
        setupUI()
        getAllMappingSeat()
        setImageAnimation()
        InitialStateForFeaturesOptions()
        binding.selectseattxt.typeface = selectedtypeface
        binding.seatselectedview.setBackgroundColor(resources.getColor(R.color.primary_blue))
        binding.seatlayout.visibility=View.VISIBLE
    }


    private fun setupUI(){
        binding.companyName.text = intent.getStringExtra(ConstantClass.travelCompanyName).orEmpty()
        binding.arrivalTime.text = intent.getStringExtra(ConstantClass.arrivalTime).orEmpty()
        binding.ratingcount.text = intent.getStringExtra(ConstantClass.ratingCount).orEmpty()
        binding.ratingcard.setCardBackgroundColor(getRatingColor(binding.ratingcount.text.toString().toDouble()))

        preference.setStringValue(ConstantClass.travelCompanyName,  binding.companyName.text.toString())
        preference.setStringValue(ConstantClass.busKey,intent.getStringExtra(ConstantClass.busKey))


        boardinglist.forEach { it->
            boardingPoint.add(BoardingDropingModel(it.boardingTime!!,it.boardingName!!,-1,it.boardingId.toString(),it.boardingTime!!))
        }


        dropinglist.forEach { it->
            droppingPoint.add(BoardingDropingModel(it.droppingTime!!,it.droppingName!!,-1,it.droppingId.toString(),it.droppingTime!!))
        }

    }

    fun setonCliskListner(){

        binding.backicon.setOnClickListener {
            finish()
        }

        binding.seattablayout.setOnClickListener {
            InitialStateForFeaturesOptions()
            binding.selectseattxt.typeface = selectedtypeface
            binding.seatselectedview.setBackgroundColor(resources.getColor(R.color.primary_blue))
            binding.seatlayout.visibility=View.VISIBLE
        }

        binding.pickuptablayout.setOnClickListener {
            InitialStateForFeaturesOptions()
            binding.pickuotxt.typeface = selectedtypeface
            binding.pickupview.setBackgroundColor(resources.getColor(R.color.primary_blue))
            binding.pickupcontainer.pickupLayout.visibility=View.VISIBLE
            setPickUpPoints()
        }

        binding.droptablayout.setOnClickListener {
            InitialStateForFeaturesOptions()
            binding.droptxt.typeface = selectedtypeface
            binding.dropview.setBackgroundColor(resources.getColor(R.color.primary_blue))
            binding.dropcontainer.droplayout.visibility=View.VISIBLE
            setDropPoints()

        }

        binding.nextbutton.setOnClickListener {
            val boardingName = preference.getStringValue(ConstantClass.boardingPoint, "")
            val dropingName = preference.getStringValue(ConstantClass.droppingPoint, "")

            if(binding.nextbutton.text.toString().equals(resources.getString(R.string.proceed))){
               if(!boardingName.isNullOrBlank()&&!dropingName.isNullOrBlank()&&!selectedSeats.isEmpty()) {
                   Log.d("boarding dropping id","${preference.getStringValue(ConstantClass.boarding_Id, "")},${preference.getStringValue(ConstantClass.dropping_Id, "")}")
                   startActivity(Intent(this,AddPassengerPage::class.java))
               }
                return@setOnClickListener
            }

            if (selectedSeats.isEmpty()) {
                InitialStateForFeaturesOptions()
                binding.selectseattxt.typeface = selectedtypeface
                binding.seatselectedview.setBackgroundColor(resources.getColor(R.color.primary_blue))
                binding.seatlayout.visibility = View.VISIBLE
                if(!boardingName.isNullOrBlank()&&!dropingName.isNullOrBlank()){
                    binding.nextbutton.text = resources.getString(R.string.proceed)
                }
                else{
                    binding.nextbutton.text = resources.getString(R.string.next) // Ensure it says Next here
                }

            } else {
                when {
                    boardingName.isNullOrBlank()&&dropingName.isNullOrBlank()->{
                        binding.nextbutton.text = resources.getString(R.string.next)
                        InitialStateForFeaturesOptions()
                        binding.pickuotxt.typeface = selectedtypeface
                        binding.pickupview.setBackgroundColor(resources.getColor(R.color.primary_blue))
                        binding.pickupcontainer.pickupLayout.visibility = View.VISIBLE
                        binding.nextbutton.text = resources.getString(R.string.next)
                        setPickUpPoints()
                    }

                    // 2. If Boarding is missing, show Pickup
                    boardingName.isNullOrBlank() -> {
                        InitialStateForFeaturesOptions()
                        binding.pickuotxt.typeface = selectedtypeface
                        binding.pickupview.setBackgroundColor(resources.getColor(R.color.primary_blue))
                        binding.pickupcontainer.pickupLayout.visibility = View.VISIBLE
                        binding.nextbutton.text = resources.getString(R.string.proceed)
                        setPickUpPoints()
                    }

                    // 3. If Boarding is there but Drop-off is missing, show Drop-off
                    dropingName.isNullOrBlank() -> {
                        InitialStateForFeaturesOptions()
                        binding.droptxt.typeface = selectedtypeface
                        binding.dropview.setBackgroundColor(resources.getColor(R.color.primary_blue))
                        binding.dropcontainer.droplayout.visibility = View.VISIBLE
                        binding.nextbutton.text = resources.getString(R.string.proceed)
                        setDropPoints()
                    }

                }
            }

        }

    }


    fun setPickUpPoints(){
        if(boardingPoint.size>0){
         var adapter = PickUpDropListAdapter(preference,this,boardingPoint,ConstantClass.boardingPoint,this)
         binding.pickupcontainer.pickupRecyclerview.adapter = adapter
            var position = -1
            var boardingName =  preference.getStringValue(ConstantClass.boardingPoint,"")
            boardingPoint.forEachIndexed { index, boardingDropingModel ->
                if (boardingDropingModel.boarddropname.equals(boardingName)) {
                    position = index
                }
            }
             adapter.updateCheckPosition(position)
        }
    }


    fun setDropPoints(){
        if(droppingPoint.size>0){
            var adapter = PickUpDropListAdapter(preference,this,droppingPoint,ConstantClass.droppingPoint,this)
            binding.dropcontainer.dropshowlist.adapter = adapter
            var position = -1
            var boardingName =  preference.getStringValue(ConstantClass.droppingPoint,"")
            droppingPoint.forEachIndexed { index, boardingDropingModel ->
                if (boardingDropingModel.boarddropname.equals(boardingName)) {
                    position = index
                }
            }
            adapter.updateCheckPosition(position)
        }
    }


    fun setImageAnimation(){
        binding.travelLoaderimage.post{
            val scaleAnim = ObjectAnimator.ofFloat(
                binding.travelLoaderimage,
                View.SCALE_Y,
                0f,
                1f
            ).apply {
                duration = 900
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                interpolator = OvershootInterpolator()
            }
            scaleAnim.start()
        }
    }


    private fun getAllMappingSeat() {

        val request = BusSeatMapReq(
            boardingId = intent.getStringExtra(ConstantClass.boarding_Id),
            droppingId = intent.getStringExtra(ConstantClass.dropping_Id),
            busKey = intent.getStringExtra(ConstantClass.busKey),
            searchKey = preference.getStringValue(ConstantClass.searchKey, ""),
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference?.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, "")
        )

        Log.d("busTempBookingReq", Gson().toJson(request))

        viewModel.getAllBusSeatMap(request).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    resource.data?.body()?.
                    let {
                        Log.d("SeatData", Gson().toJson(it))
                        seatDataList.clear()
                        setupSeatGrid(it)
                    }
                }

                ApiStatus.ERROR -> {
                    binding.seatlayout.visibility = View.GONE
                    binding.travelLoader.visibility = View.VISIBLE
                    binding.travelLoaderimage.setImageDrawable(resources.getDrawable(R.drawable.chairimagenotfound))
                    binding.seatfindmsg.text= resources.getString(R.string.not_found_seat)
                    Toast.makeText(this, "Failed to load seats", Toast.LENGTH_SHORT).show()
                }

                ApiStatus.LOADING ->{
                    binding.seatlayout.visibility = View.GONE
                    binding.travelLoader.visibility = View.VISIBLE
                    binding.travelLoaderimage.setImageDrawable(resources.getDrawable(R.drawable.chairimage))
                    binding.seatfindmsg.text= resources.getString(R.string.finding_your_seat)
                }
            }
        }

    }

    private fun setupSeatGrid(response: BusSeatMapRes) {
        if(response.responseHeader?.errorCode.equals("0002")) {
            binding.seatlayout.visibility = View.GONE
            binding.travelLoader.visibility = View.VISIBLE
            binding.travelLoaderimage.setImageDrawable(resources.getDrawable(R.drawable.chairimagenotfound))
            binding.seatfindmsg.text= resources.getString(R.string.not_found_seat)
            Toast.makeText(this, "No Seats Available", Toast.LENGTH_SHORT).show()

        }
        else{

            binding.seatlayout.visibility = View.VISIBLE
            binding.travelLoader.visibility = View.GONE

            val lowerGrid = binding.seatLayout.lowerBerthGrid
            val upperGrid = binding.seatLayout.upperBerthGrid
            var upperlayout = binding.seatLayout.upperlayout


            binding.seatBookingLayout.visibility = View.VISIBLE

            lowerGrid.removeAllViews()
            upperGrid.removeAllViews()

            preference.setStringValue(ConstantClass.seatMap_Key, response.seatMapKey.toString())

            Log.d("removeAllViews", preference.getStringValue(ConstantClass.seatMap_Key, "").toString())

            try {
                response.boardingDetails.forEach { boardingPoint ->
                    ConstantClass.boardingPointName?.add(boardingPoint.boardingAddress!!)
                }

                ConstantClass.getAllBoardingPointAdapter?.notifyDataSetChanged()

                response.droppingDetails.forEach { droppingPoint ->
                    ConstantClass.droppingPointName?.add(droppingPoint.droppingAddress!!)
                }

                ConstantClass.getAllDroppingPointAdapter?.notifyDataSetChanged()

            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

            // swapped
            val maxApiRow = response.seatMap.maxOf { it.row?.toIntOrNull() ?: 0 }
            val maxApiCol = response.seatMap.maxOf { it.column ?: 0 }

            val totalRows = maxApiCol + 1   // because API column → row
            val totalCols = maxApiRow + 1   // because API row → column

            lowerGrid.rowCount = totalRows
            lowerGrid.columnCount = totalCols

            upperGrid.rowCount = totalRows
            upperGrid.columnCount = totalCols


            response.seatMap.forEach { seat ->

                val apiRow = seat.row?.toIntOrNull() ?: 0
                val apiCol = seat.column ?: 0

                val row = apiCol     // <-- row takes column
                val col = apiRow     // <-- column takes row

                val zIndex = seat.zIndex?.toIntOrNull() ?: 0
                val length = seat.length?.toIntOrNull() ?: 1
                val amount = seat.fareMaster?.basicAmount ?: 0.0
                val otheramount = seat.fareMaster?.otherAmount ?: 0.0

                //val seatName = "₹" + String.format("%.2f", amount)
                //val seatamount = String.format("%.2f", amount)

                val seatView = createSeatView(amount, otheramount,this, row, col, length, seat)

                if (zIndex == 1) {
                    upperGrid.addView(seatView)
                }
                else {
                    lowerGrid.addView(seatView)
                }
                upperlayout.visibility = if (upperGrid.childCount > 0) View.VISIBLE else View.GONE
            }

        }


    }


    private fun createSeatView(seatAmountText: Double, otheramount: Double, context: Context, row: Int, column: Int, length: Int, seat: SeatMap): View {
        val seatView = LayoutInflater.from(context).inflate(R.layout.seat_item, null)

        val tvPrice = seatView.findViewById<TextView>(R.id.tvPrice)
        val pillow = seatView.findViewById<View>(R.id.pillowView)
        val lefthandle = seatView.findViewById<View>(R.id.lefthandle)
        val righthandle = seatView.findViewById<View>(R.id.righthandle)
        val bottomHandle = seatView.findViewById<View>(R.id.bottomHandle)
        val seatContainer = seatView.findViewById<FrameLayout>(R.id.seatContainer)

        try{
            // tvPrice.text = "₹$seatAmountText"
            tvPrice.text = "₹${seatAmountText.toInt()}"
        }
        catch (e:Exception){
            e.message
        }

        tvPrice.textSize = 8f

        val isSleeper = length == 2

        val widthh = if (isSleeper) 60 else 60
        val heightt = if (isSleeper) 150 else 100

        val params = GridLayout.LayoutParams().apply {
            width = widthh
            height = heightt
            setMargins(10, 10, 10, 10)

            // ⭐ dynamic row / column position from API ⭐
            rowSpec = GridLayout.spec(row)
            columnSpec = GridLayout.spec(column)
            setGravity(Gravity.CENTER)
        }

        if (isSleeper) {
            pillow.visibility = View.VISIBLE
            lefthandle.visibility= View.GONE
            righthandle.visibility= View.GONE
            bottomHandle.visibility= View.GONE
        }
        else {
            pillow.visibility = View.GONE
            lefthandle.visibility= View.VISIBLE
            righthandle.visibility= View.VISIBLE
            bottomHandle.visibility= View.VISIBLE
        }

        seatView.layoutParams = params

        // Set initial background
        when {
            seat.ladiesSeat == true && seat.bookable == true -> {seatContainer.setBackgroundResource(R.drawable.femaleseatforselected)}
            seat.ladiesSeat == true && seat.bookable == false -> {seatContainer.setBackgroundResource(R.drawable.bg_ladies_seat)}
            seat.bookable == true -> seatContainer.setBackgroundResource(R.drawable.bg_edittext)
            else -> seatContainer.setBackgroundResource(R.drawable.booked_seat)
        }


        seatView.setOnClickListener {
            if (seat.bookable != true) {
                Toast.makeText(context, "Seat not available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (selectedSeats.contains(seat)) {

                val index = seatDataList.indexOfFirst {
                    it.seatNumber == getSeatLabel(seat)
                }

                if (index != -1) {
                    seatDataList.removeAt(index)
                }

                selectedSeats.remove(seat)

                Log.d("listsize",Gson().toJson(seatDataList))
                // Set background after deselection
                if (seat.ladiesSeat == true) {
                    seatContainer.setBackgroundResource(R.drawable.femaleseatforselected)
                } else {
                    seatContainer.setBackgroundResource(R.drawable.bg_edittext)
                }

                if(selectedSeats.size==0){
                    setanimationForCloseFinalAmountLayout(binding.selectedseatLayout)
                }


            }
            else {
                if(selectedSeats.size==6){
                    Toast.makeText(context, "Selected seats cannot be greater than 6", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                selectedSeats.add(seat)
                if(binding.selectedseatLayout.visibility==View.INVISIBLE){
                    setanimationForOpenFinalAmountLayout(binding.selectedseatLayout)
                }

                val seatLabel = getSeatLabel(seat)
                val seatNo = seat.seatNumber?.toString() ?: ""

                if (seat.ladiesSeat == true) {
                    seatContainer.setBackgroundResource(R.drawable.forfemaleselectionseat)
                    seatDataList.add(PassengerSelectedSeatDataModel(seatLabel,true,"","",ConstantClass.FemaleGender,"",seatNo))
                }
                else {
                    seatContainer.setBackgroundResource(R.drawable.your_seat)
                    seatDataList.add(PassengerSelectedSeatDataModel(seatLabel,false,"","",ConstantClass.MaleGender,"",seatNo))
                }

                 Log.d("listsize",Gson().toJson(seatDataList))
            }

            val seatLabels = selectedSeats.map { getSeatLabel(it) }

            binding.selectedSeat.text = "Seat ${seatLabels.joinToString(",")}"

            val lengthCount = selectedSeats.groupingBy { it.length }.eachCount()

            val countLen1 = lengthCount["1"] ?: 0   // for seater length 1
            val countLen2 = lengthCount["2"] ?: 0  // for sleeper length 2

            Log.d("count1","$countLen1")
            Log.d("count2","$countLen2")
            forServiceChargeSeatType.clear()

            var busType: String

            if(intent.getStringExtra(ConstantClass.busName)!!.contains("NON A/C"))
            {
                busType="NON-AC"
            }
            else{
                busType="AC"
            }

            val totalAmount = selectedSeats.sumOf { it.fareMaster?.basicAmount ?: 0.0 }
            binding.finalAmount.text = "₹ $totalAmount"
            preference.setStringValue(ConstantClass.TotalSeatAmount,totalAmount.toString())

            val totalOtherAmount = selectedSeats.sumOf { it.fareMaster?.otherAmount ?: 0.0 }
            rechargeAmount = (totalAmount+totalOtherAmount).toString()

            val seatAmountList = selectedSeats.map { seat ->
                SeatFair(
                    seat.fareMaster?.basicAmount ?: 0.0,
                    seat.fareMaster?.otherAmount ?: 0.0,
                    seat.length)
            }

            forServiceChargeSeatType.add(SeattypeModel(countLen2,countLen1,busType.trim(),seatAmountList))

            Log.d("servicetypelist",Gson().toJson(forServiceChargeSeatType))
            Log.d("Total Amount", " $totalAmount")
            Log.d("rechargeamount", " $rechargeAmount")
        }

        return seatView
    }

    private fun getSeatLabel(seat: SeatMap): String {
        val seatNo = seat.seatNumber?.toString() ?: ""

        // If already starts with U or L, return as it is
        if (seatNo.endsWith("U") || seatNo.endsWith("L")) {
            return seatNo
        }

        val type = if ((seat.zIndex?.toIntOrNull() ?: 0) == 1) "U" else "L"

        return "$type$seatNo"
    }

    // selected unselected feature layout.........................................................

    fun InitialStateForFeaturesOptions(){
        binding.selectseattxt.typeface = unselectedtypeface
        binding.pickuotxt.typeface = unselectedtypeface
        binding.droptxt.typeface = unselectedtypeface

        binding.seatselectedview.setBackgroundColor(resources.getColor(R.color.footer_unselected))
        binding.pickupview.setBackgroundColor(resources.getColor(R.color.footer_unselected))
        binding.dropview.setBackgroundColor(resources.getColor(R.color.footer_unselected))

        binding.seatlayout.visibility=View.GONE
        binding.pickupcontainer.pickupLayout.visibility=View.GONE
        binding.dropcontainer.droplayout.visibility=View.GONE
    }


    fun setanimationForOpenFinalAmountLayout(view : View){
        // Start from below the screen

        binding.seatlayout.setPadding(0,0,0,130)
        view.translationY = view.height.toFloat()

        view.visibility = View.VISIBLE

        view.alpha = 0f


        view.animate()

            .translationY(0f)

            .alpha(1f)

            .setDuration(400)

            .setInterpolator(DecelerateInterpolator())

            .start()
    }


    fun setanimationForCloseFinalAmountLayout(view: View) {
        // 1. Reset the padding on your background layout (optional, based on your logic)
        binding.seatlayout.setPadding(0, 0, 0, 0)

        // 2. Start the reverse animation
        view.animate()
            .translationY(view.height.toFloat()) // Move back down
            .alpha(0f)                           // Fade out
            .setDuration(400)
            .setInterpolator(AccelerateInterpolator()) // Accelerate looks better for closing
            .withEndAction {
                // 3. IMPORTANT: Hide the view only after animation is done
                view.visibility = View.INVISIBLE
            }
            .start()
    }


    override fun onClick(item: BoardingDropingModel, type: String) {
        Log.d("boardingId",item.boardingId)
        Log.d("boardingName",item.boarddropname)
        Log.d("boardingName",item.boardingTime)


        if(type.equals(ConstantClass.droppingPoint)){
            preference.setStringValue(ConstantClass.droppingPoint, item.boarddropname)
            preference.setStringValue(ConstantClass.dropping_Id, item.boardingId)
            preference.setStringValue(ConstantClass.dropping_Time, item.boardingTime)
        }
        else{
            preference.setStringValue(ConstantClass.boardingPoint, item.boarddropname)
            preference.setStringValue(ConstantClass.boarding_Id, item.boardingId)
            preference.setStringValue(ConstantClass.boarding_Time, item.boardingTime)
        }

        if(binding.selectedseatLayout.visibility==View.INVISIBLE){
            setanimationForOpenFinalAmountLayout(binding.selectedseatLayout)
        }

    }


}