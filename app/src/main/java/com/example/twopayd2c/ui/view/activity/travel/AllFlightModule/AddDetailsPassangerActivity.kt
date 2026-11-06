package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.BookingFlightDetails
import com.example.twopayd2c.data.model.travelmodel.flight.BookingSSRDetails
import com.example.twopayd2c.data.model.travelmodel.flight.FareDetailsFlightItem
import com.example.twopayd2c.data.model.travelmodel.flight.FlightPassengerDataModel
import com.example.twopayd2c.data.model.travelmodel.flight.FlightTempBookingReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.data.model.travelmodel.flight.PaXDetailsFlight
import com.example.twopayd2c.data.model.travelmodel.flight.PassangerDataList
import com.example.twopayd2c.data.model.travelmodel.flight.RequiredPAXDetailsItem
import com.example.twopayd2c.data.model.travelmodel.flight.SegmentsItem
import com.example.twopayd2c.data.model.travelmodel.flight.TempBookingPassangerDetails
import com.example.twopayd2c.data.model.travelmodel.flight.fareBreakup
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory

import com.example.twopayd2c.databinding.ActivityAddDetailsPassangerBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.FlightLayoverAdapter
import com.example.twopayd2c.ui.adapter.PassengerDataForFlightAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.AddContactAndMobileBottomSheet
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.FareBreakUpBottomSheet
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.ReviewTempBookingPage.Companion.passengerTicketList
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.BaseFare
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightDetails
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.GetAirlineLogo
import com.example.twopayd2c.utils.ConstantClass.Companion.GrossFare
import com.example.twopayd2c.utils.ConstantClass.Companion.TaxAndFees
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.adultCount
import com.example.twopayd2c.utils.ConstantClass.Companion.cabinBag
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateDOBFromAge
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateTotalFlightDuration
import com.example.twopayd2c.utils.ConstantClass.Companion.checkinBag
import com.example.twopayd2c.utils.ConstantClass.Companion.childCount
import com.example.twopayd2c.utils.ConstantClass.Companion.className
import com.example.twopayd2c.utils.ConstantClass.Companion.convertDate
import com.example.twopayd2c.utils.ConstantClass.Companion.formatDate1
import com.example.twopayd2c.utils.ConstantClass.Companion.formatDate2
import com.example.twopayd2c.utils.ConstantClass.Companion.infantCount
import com.example.twopayd2c.utils.ConstantClass.Companion.searchDateIfNotInList
import com.example.twopayd2c.utils.ConstantClass.Companion.splitDateTime
import com.example.twopayd2c.utils.ConstantClass.Companion.toInrFormat
import com.example.twopayd2c.utils.ConstantClass.Companion.totalDurationTime

import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


class AddDetailsPassangerActivity : AppCompatActivity(), PassengerDataForFlightAdapter.setOnClickListner {
    lateinit var binding: ActivityAddDetailsPassangerBinding
    lateinit var preference: SharedPreference
    var checkAdultOpenClose = true
    var checkChildOpenClose = false
    var checkInfantOpenClose = false
    var segmentList: MutableList<SegmentsItem?> = mutableListOf()
    var flightDetails: MutableList<FlightsItem?> = mutableListOf()

    lateinit var flightadapter: FlightLayoverAdapter
    private lateinit var viewModel: TravelViewModel

    var stops: String = ""
    var totalRechargeAmount: Double = 0.0

    // add passenger list........................................................................
    var GenderType : String = ""
    var adultFullName : String = ""
    var adultage : String = ""
    var adultPassportNo : String = ""
    var adultPassportIssueCountryName : String = ""

    var adultPassportExpiryDate : String = ""
    var childPassportExpiryDate : String = ""
    var infantPassportExpiryDate : String = ""

    var passengerList : MutableList<FlightPassengerDataModel> = mutableListOf()
    var filterPassengerList : MutableList<FlightPassengerDataModel> = mutableListOf()
    var saveInLoaclDataList : MutableList<FlightPassengerDataModel> = mutableListOf()
    var PassengerListForTicket : MutableList<FlightPassengerDataModel> = mutableListOf()
    lateinit var passengerDataAdapter : PassengerDataForFlightAdapter
    lateinit var passengerListForUpdate : FlightPassengerDataModel
    var updatePosition : Int = 0
    lateinit var dialog : Dialog
    var  totalAddNumber = 0
    var totalCount = 0

    var tempBookingPassangerDetails: MutableList<TempBookingPassangerDetails> = mutableListOf()
    var passangerDetailsList: MutableList<PassangerDataList> = mutableListOf()


    companion object {
        var segmentListPassangerDetail: MutableList<SegmentsItem?> = mutableListOf()
        var flightDetailsPassangerDetail: MutableList<FlightsItem?> = mutableListOf()

        /* var adultList: MutableList<PassangerDataList> = mutableListOf()
         var childList: MutableList<PassangerDataList> = mutableListOf()
         var infantList: MutableList<PassangerDataList> = mutableListOf()*/

        var farebreakupList: MutableList<fareBreakup> = mutableListOf()
        var fareList: MutableList<FareDetailsFlightItem> = mutableListOf()
        var paxDetailsListFromReprice: MutableList<RequiredPAXDetailsItem> = mutableListOf()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddDetailsPassangerBinding.inflate(layoutInflater)
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

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        preference = SharedPreference(this)

        setOnClickListner()
        setDataOnView()
        HitApiForFareBreakUp()
        showFlightDetailsLayout()
        setDataForContactDetails()
    }

    private fun setPassengerDataList(){
        if(passengerList.isNotEmpty()){

            val passengerMap = passengerList.groupBy { it.typeOfPassenger }

            val adults =  if(adultCount>0) passengerMap[ConstantClass.Adult] ?: emptyList() else emptyList()
            val children = if (childCount>0)passengerMap[ConstantClass.Child] ?: emptyList() else emptyList()
            val infants = if(infantCount>0)passengerMap[ConstantClass.Infant] ?: emptyList() else emptyList()

            filterPassengerList = when {
                    adults.isNotEmpty() && children.isEmpty() && infants.isEmpty() -> {
                        // Only Adults
                        adults
                    }

                    adults.isNotEmpty() && children.isNotEmpty() && infants.isEmpty() -> {
                        // Adults + Children
                        adults + children
                    }

                    adults.isNotEmpty() && infants.isNotEmpty() && children.isEmpty() -> {
                        // Adults + Infants
                        adults + infants
                    }

                    adults.isNotEmpty() && children.isNotEmpty() && infants.isNotEmpty() -> {
                        // All three
                        adults + children + infants
                    }

                    else -> emptyList()
                }.toMutableList()
            passengerDataAdapter= PassengerDataForFlightAdapter(this,filterPassengerList,this)
            binding.savedPassengerListShown.adapter = passengerDataAdapter

            var getCheckedData = filterPassengerList.filter { it.checked }
            getCheckedData.forEach {
              addPassengerToCorrectPosition(it)
           }

        }
        else {
            passengerList = preference.getFutureFlightDataList(ConstantClass.FlightPassengerFutureDate).toMutableList()
            Log.d("SavedData",Gson().toJson(passengerList))
            passengerList.forEach { it.checked=false }
            if (passengerList.isNotEmpty()) {

                val passengerMap = passengerList.groupBy { it.typeOfPassenger }

                val adults = if (adultCount > 0) passengerMap[ConstantClass.Adult] ?: emptyList() else emptyList()

                val children = if (childCount > 0) passengerMap[ConstantClass.Child] ?: emptyList() else emptyList()

                val infants = if (infantCount > 0) passengerMap[ConstantClass.Infant] ?: emptyList() else emptyList()

                filterPassengerList = when {
                    adults.isNotEmpty() && children.isEmpty() && infants.isEmpty() -> {
                        // Only Adults
                        adults
                    }

                    adults.isEmpty() && children.isNotEmpty()&& infants.isEmpty()->{
                        children
                    }

                    adults.isEmpty() && children.isEmpty()&& infants.isNotEmpty()->{
                        infants
                    }

                    adults.isNotEmpty() && children.isNotEmpty() && infants.isEmpty() -> {
                        // Adults + Children
                        adults + children
                    }

                    adults.isNotEmpty() && infants.isNotEmpty() && children.isEmpty() -> {
                        // Adults + Infants
                        adults + infants
                    }

                    adults.isEmpty()&& infants.isNotEmpty() && children.isNotEmpty()->{
                        infants + children
                    }

                    adults.isNotEmpty() && children.isNotEmpty() && infants.isNotEmpty() -> {
                        // All three
                        adults + children + infants
                    }

                    else -> emptyList()
                }.toMutableList()

                passengerDataAdapter = PassengerDataForFlightAdapter(this, filterPassengerList, this)
                binding.savedPassengerListShown.adapter = passengerDataAdapter

            }

        }


    }

    fun setDataForContactDetails(){
        var conatactNo = preference.getStringValue(ConstantClass.ContactNumber,"")
        var emailID = preference.getStringValue(ConstantClass.EmailID,"")

        if(conatactNo!!.isNotEmpty()&& emailID!!.isNotEmpty()){
            binding.adddetailsbutton.visibility = View.GONE
            binding.contactDetailslayout.visibility = View.VISIBLE
            binding.edittxt.visibility = View.VISIBLE
            binding.mobilenumber.setText("Mobile : ${conatactNo}")
            binding.mailid.setText("Email ID:${emailID}")
        }else{
            binding.adddetailsbutton.visibility = View.VISIBLE
            binding.contactDetailslayout.visibility = View.GONE
            binding.edittxt.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDataOnView() {
        val segments = listOf(
            mapOf(
                "departure_DateTime" to segmentListPassangerDetail[0]!!.departureDateTime,
                "arrival_DateTime" to segmentListPassangerDetail[0]!!.arrivalDateTime
            ),
            mapOf(
                "departure_DateTime" to segmentListPassangerDetail[segmentListPassangerDetail.size - 1]!!.departureDateTime,
                "arrival_DateTime" to segmentListPassangerDetail[segmentListPassangerDetail.size - 1]!!.arrivalDateTime
            )
        )

        totalDurationTime = calculateTotalFlightDuration(segments)
        var originDateTime = splitDateTime(segmentListPassangerDetail[0]!!.departureDateTime)

        var originDate = formatDate1(originDateTime.first).plus(",").plus(formatDate2(originDateTime.first))

        var originTime = originDateTime.second

        var destinationDateTime = splitDateTime(segmentListPassangerDetail[segmentListPassangerDetail.size - 1]!!.arrivalDateTime)
        var DestinationTime = destinationDateTime.second


        if (segmentListPassangerDetail.size == 1) {
            stops = "Non stops"
        } else {
            stops = (segmentListPassangerDetail.size - 1).toString().plus(" stop")
        }


        // "Fri,4 Jul| 19:10 - 01:05|5h 55m |1 Stop|Economy Class"
        var traveldetails = originDate.plus(" | ").plus(originTime).plus(" - ").plus(DestinationTime).plus(" | ")
                .plus(totalDurationTime).plus(" | ").plus(stops).plus(" | ").plus(className)


        binding.fromAirportCode.text = flightDetailsPassangerDetail[0]!!.origin
        binding.toAirportCode.text = flightDetailsPassangerDetail[0]!!.destination
        binding.travelprimedetails.text = traveldetails
        binding.checkinbagweight.text = checkinBag
        binding.cabinbagweight.text = cabinBag

        var airportIcon = GetAirlineLogo(flightDetailsPassangerDetail[0]!!.airlineCode)
        Glide.with(this).load(airportIcon).error(R.drawable.airplaceholder).into(binding.airlineicon)



        totalCount = (adultCount.toInt() + childCount.toInt() + infantCount.toInt())
        totalAddNumber = 0
        binding.numberOfPassemger.text = "${totalAddNumber}/${totalCount}"
        PassengerListForTicket.clear()

        addPassengers(adultCount.toInt(), ConstantClass.Adult)
        addPassengers(childCount.toInt(), ConstantClass.Child)
        addPassengers(infantCount.toInt(), ConstantClass.Infant)


        binding.childlayout.visibility = if(childCount>0)  View.VISIBLE else View.GONE
        binding.infantlayout.visibility =  if(infantCount>0) View.VISIBLE else View.GONE

        binding.adultcount.text="Adults : 0/${adultCount}"
        binding.childcount.text="Child : 0/${childCount}"
        binding.infantcount.text="Infant : 0/${infantCount}"



        Log.d("ListOfTicketEmpty",Gson().toJson(PassengerListForTicket))

    }

    fun addPassengers(count: Int, type: String) {
        repeat(count) {
            PassengerListForTicket.add(
                FlightPassengerDataModel(
                    "", "", "", "", "", "", "", "",
                    typeOfPassenger = type,
                    false
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickListner() {

        binding.backicon.setOnClickListener { finish() }

        binding.detailstxt.setOnClickListener { openBottomSheetForShowingDetailsPickupDropUp() }

        binding.adddetailsbutton.setOnClickListener {
            val bottomfrag = AddContactAndMobileBottomSheet()
            supportFragmentManager.let {
                bottomfrag.show(it, AddContactAndMobileBottomSheet.TAG)
            }
        }

        binding.edittxt.setOnClickListener {
            val bottomfrag = AddContactAndMobileBottomSheet()
            supportFragmentManager.let {
                bottomfrag.show(it, AddContactAndMobileBottomSheet.TAG)
            }
        }

        binding.addnewPassenger.setOnClickListener {
            binding.travellertypecontainer.nextbutton.text= getString(R.string.add)
            binding.travellertypecontainer.childnextbutton.text= getString(R.string.add)
            binding.travellertypecontainer.infantnextbutton.text= getString(R.string.add)
            showAddPassengerLayout()
            showAndHideView()
            clearAdultField()
            clearChieldField()
            clearInfantField()
        }

        binding.addpassengerBottomselection.setOnClickListener {
            showFlightDetailsLayout()
        }

        binding.travellertypecontainer.adultarrow.setOnClickListener {
            if (checkAdultOpenClose) {
                binding.travellertypecontainer.adultsubdetails.visibility = View.GONE
                binding.travellertypecontainer.adultarrow.setImageResource(R.drawable.arrowdown)
                checkAdultOpenClose = false
            } else {
                binding.travellertypecontainer.adultsubdetails.visibility = View.VISIBLE
                binding.travellertypecontainer.adultarrow.setImageResource(R.drawable.arrowup)
                checkAdultOpenClose = true
            }

        }

        binding.travellertypecontainer.childarrow.setOnClickListener {
            if (checkChildOpenClose) {
                binding.travellertypecontainer.childContainer.visibility = View.GONE
                binding.travellertypecontainer.childarrow.setImageResource(R.drawable.arrowdown)
                checkChildOpenClose = false
            } else {
                binding.travellertypecontainer.childContainer.visibility = View.VISIBLE
                binding.travellertypecontainer.childarrow.setImageResource(R.drawable.arrowup)
                checkChildOpenClose = true
            }

        }

        binding.travellertypecontainer.infantarrow.setOnClickListener {
            if (checkInfantOpenClose) {
                binding.travellertypecontainer.infantContainer.visibility = View.GONE
                binding.travellertypecontainer.infantarrow.setImageResource(R.drawable.arrowdown)
                checkInfantOpenClose = false
            } else {
                binding.travellertypecontainer.infantContainer.visibility = View.VISIBLE
                binding.travellertypecontainer.infantarrow.setImageResource(R.drawable.arrowup)
                checkInfantOpenClose = true
            }

        }

        binding.farebreakup.setOnClickListener {
            showPaymentDetailsLayout()
            val bottomfrag = FareBreakUpBottomSheet()
            supportFragmentManager.let {
                bottomfrag.show(it, FareBreakUpBottomSheet.TAG)
            }
        }

        // add passenger layout items .................................................................................................

        binding.travellertypecontainer.passengerAge.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val ageText = s.toString()

                if (ageText.isNotEmpty()) {
                    val age = ageText.toInt()

                    if (age < 12) {
                        binding.travellertypecontainer.passengerAge.error = "Age must be 12 or above"
                    } else {
                        binding.travellertypecontainer.passengerAge.error = null
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        binding.travellertypecontainer.childpassengerAge.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val ageText = s.toString()

                if (ageText.isNotEmpty()) {
                    val age = ageText.toInt()

                    if (age <2 || age >12) {
                        binding.travellertypecontainer.childpassengerAge.error = "Age must be 2 - 12 yrs"
                    } else {
                        binding.travellertypecontainer.childpassengerAge.error = null
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        binding.travellertypecontainer.infantpassengerAge.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val ageText = s.toString()

                if (ageText.isNotEmpty()) {
                    val age = ageText.toInt()

                    if (age > 2) {
                        binding.travellertypecontainer.infantpassengerAge.error = "Age under  2 yrs "
                    } else {
                        binding.travellertypecontainer.infantpassengerAge.error = null
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        binding.travellertypecontainer.passportExpireDateCalander.setOnClickListener {
            openCalendar(ConstantClass.Adult)
        }

        binding.travellertypecontainer.childPassportExpireDateCalander.setOnClickListener {
            openCalendar(ConstantClass.Child)
        }

        binding.travellertypecontainer.infantPassportExpireDateCalander.setOnClickListener {
            openCalendar(ConstantClass.Infant)
        }

        binding.travellertypecontainer.adultradiogroup.setOnCheckedChangeListener { group, checkedId ->

            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)

            GenderType = selectedRadioButton.text.toString()

            Log.d("gender", GenderType)

        }
        
        binding.travellertypecontainer.childradiogroup.setOnCheckedChangeListener { group, checkedId ->

            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)

            GenderType = selectedRadioButton.text.toString()

            Log.d("gender", GenderType)

        }

        binding.travellertypecontainer.infantradiogroup.setOnCheckedChangeListener { group, checkedId ->

            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)

            GenderType = selectedRadioButton.text.toString()

            Log.d("gender", GenderType)

        }

        binding.travellertypecontainer.adultAddBtn.setOnClickListener {
            var passengerName = binding.travellertypecontainer.passengerName.text.toString()
            var passengerLName = binding.travellertypecontainer.passengerLName.text.toString()
            var passengerAge = binding.travellertypecontainer.passengerAge.text.toString()
            var PassportNo =  binding.travellertypecontainer.passportNo.text.toString()
            var PassengerPassportCountry =  binding.travellertypecontainer.passportIssuingCountry.text.toString()
            var PassengerExpireDate = binding.travellertypecontainer.passportExpireDate.text.toString()
            var gender = ""
            when(GenderType){
                ConstantClass.Mr -> {
                    gender = "Male"}
                 ConstantClass.Ms ->{
                     gender = "Female"
                    }
                ConstantClass.Mrs->{
                     gender = "Female"
                 }
            }
            if(binding.travellertypecontainer.nextbutton.text == getString(R.string.add)){
                if (validateAdult()) {
                    if(passengerList.isEmpty()){
                        passengerList = preference.getFutureFlightDataList(ConstantClass.FlightPassengerFutureDate).toMutableList()
                    }
                    if (isPassengerAlreadyAdded(passengerName, passengerLName, PassportNo, ConstantClass.Adult)) {
                        Toast.makeText(this, "Passenger already added", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    updateButtonState(true, binding.travellertypecontainer.adultAddBtn)

                    // 1️⃣ Uncheck previous passengers of same type
                    passengerList.forEach {
                        if (it.typeOfPassenger == ConstantClass.Adult) {
                            it.checked = false
                        }
                    }

                    val passenger = FlightPassengerDataModel(
                        gender = gender,
                        genderType = GenderType,
                        passengerName = passengerName,
                        passengerLName = passengerLName,
                        age = passengerAge,
                        passportNo = PassportNo,
                        passportIssueCountryName = PassengerPassportCountry,
                        passportExpiryDate = PassengerExpireDate,
                        typeOfPassenger = ConstantClass.Adult,
                        true
                    )

                    passengerList.add(passenger)
                    preference.saveForFutureFlightData(ConstantClass.FlightPassengerFutureDate,passengerList)
                    clearAdultField()
                    Toast.makeText(this, "Passenger Added Successfully", Toast.LENGTH_SHORT).show()
                }
                else{
                    updateButtonState(false, binding.travellertypecontainer.adultAddBtn)
                }
            }
            else{
                val updatedModel = passengerListForUpdate.copy(
                    gender = gender,
                    genderType = GenderType,
                    passengerName = passengerName,
                    passengerLName = passengerLName,
                    age = passengerAge,
                    passportNo = PassportNo,
                    passportIssueCountryName = PassengerPassportCountry,
                    passportExpiryDate = PassengerExpireDate,
                    typeOfPassenger = ConstantClass.Adult,
                )
                passengerList[updatePosition] = updatedModel
                saveInLoaclDataList = preference.getFutureFlightDataList(ConstantClass.FlightPassengerFutureDate).toMutableList()
                saveInLoaclDataList.forEachIndexed{index,unit->
                    if(updatePosition==index){
                        val updatedModel = unit.copy(
                            gender = gender,
                            genderType = GenderType,
                            passengerName = passengerName,
                            passengerLName = passengerLName,
                            age = passengerAge,
                            passportNo = PassportNo,
                            passportIssueCountryName = PassengerPassportCountry,
                            passportExpiryDate = PassengerExpireDate,
                            typeOfPassenger = ConstantClass.Adult,
                        )
                        saveInLoaclDataList[updatePosition] = updatedModel
                        preference.saveForFutureFlightData(ConstantClass.FlightPassengerFutureDate,saveInLoaclDataList)
                        return@forEachIndexed
                    }

                }
                Toast.makeText(this, "Passenger Data Updated", Toast.LENGTH_SHORT).show()

            }

        }

        binding.travellertypecontainer.childAddBtn.setOnClickListener {
            var passengerName = binding.travellertypecontainer.childpassengerName.text.toString()
            var passengerLName = binding.travellertypecontainer.childPassengerLName.text.toString()
            var passengerAge = binding.travellertypecontainer.childpassengerAge.text.toString()
            var PassportNo =  binding.travellertypecontainer.childPassportNo.text.toString()
            var PassengerPassportCountry =  binding.travellertypecontainer.childPassportIssuingCountry.text.toString()
            var PassengerExpireDate = binding.travellertypecontainer.childPassportExpireDate.text.toString()
            var gender = ""
            when(GenderType){
                ConstantClass.Ms ->{
                    gender = "Female"
                }
                ConstantClass.Mstr->{
                    gender = "Male"
                }
            }

            if(validateChild()){
                if(passengerList.isEmpty()){
                    passengerList = preference.getFutureFlightDataList(ConstantClass.FlightPassengerFutureDate).toMutableList()
                }
                if (isPassengerAlreadyAdded(passengerName, passengerLName, PassportNo, ConstantClass.Child)) {
                    Toast.makeText(this, "Passenger already added", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                updateButtonState(true, binding.travellertypecontainer.childAddBtn)

                passengerList.forEach {
                    if (it.typeOfPassenger == ConstantClass.Child) {
                        it.checked = false
                    }
                }

                val passenger = FlightPassengerDataModel(
                    gender = gender,
                    genderType = GenderType,
                    passengerName = passengerName,
                    passengerLName,
                    age = passengerAge,
                    passportNo = PassportNo,
                    passportIssueCountryName = PassengerPassportCountry,
                    passportExpiryDate = PassengerExpireDate,
                    typeOfPassenger = ConstantClass.Child,
                    true
                )

                passengerList.add(passenger)
                preference.saveForFutureFlightData(ConstantClass.FlightPassengerFutureDate,passengerList)
                clearChieldField()
                Toast.makeText(this, "Passenger Added", Toast.LENGTH_SHORT).show()
            }else{
                updateButtonState(false, binding.travellertypecontainer.childAddBtn)
            }

        }

        binding.travellertypecontainer.infantAddBtn.setOnClickListener {
            var passengerName = binding.travellertypecontainer.infantpassengerName.text.toString()
            var passengerLName = binding.travellertypecontainer.infantPassengerLName.text.toString()
            var passengerAge = binding.travellertypecontainer.infantpassengerAge.text.toString()
            var PassportNo =  binding.travellertypecontainer.infantPassportNo.text.toString()
            var PassengerPassportCountry =  binding.travellertypecontainer.infantPassportIssuingCountry.text.toString()
            var PassengerExpireDate = binding.travellertypecontainer.infantPassportExpireDate.text.toString()
            var gender = ""

            when(GenderType){
                ConstantClass.Ms ->{
                    gender = "Female"
                }
                ConstantClass.Mstr->{
                    gender = "Male"
                }
            }

           if(validateInfant()){
               if(passengerList.isEmpty()){
                   passengerList = preference.getFutureFlightDataList(ConstantClass.FlightPassengerFutureDate).toMutableList()
               }
               if (isPassengerAlreadyAdded(passengerName, passengerLName, PassportNo, ConstantClass.Infant)) {
                   Toast.makeText(this, "Passenger already added", Toast.LENGTH_SHORT).show()
                   return@setOnClickListener
               }
               updateButtonState(true, binding.travellertypecontainer.infantAddBtn)

               passengerList.forEach {
                   if (it.typeOfPassenger == ConstantClass.Infant) {
                       it.checked = false
                   }
               }

               val passenger = FlightPassengerDataModel(
                   gender = gender,
                   genderType = GenderType,
                   passengerName = passengerName,
                   passengerLName,
                   age = passengerAge,
                   passportNo = PassportNo,
                   passportIssueCountryName = PassengerPassportCountry,
                   passportExpiryDate = PassengerExpireDate,
                   typeOfPassenger = ConstantClass.Infant,
                   true
               )

               passengerList.add(passenger)
               preference.saveForFutureFlightData(ConstantClass.FlightPassengerFutureDate,passengerList)
               clearInfantField()
               Toast.makeText(this, "Passenger Added", Toast.LENGTH_SHORT).show()
           }
           else{
               updateButtonState(false, binding.travellertypecontainer.infantAddBtn)
           }

        }

        binding.proceedBtn.setOnClickListener {
           /* showPaymentDetailsLayout()
            OpenForPaymentOption()*/

            //tempbooking .................................
            validationPassenger()

        }

    }

    fun isPassengerAlreadyAdded(name: String, lname: String, passportNo: String, type: String): Boolean {

        return passengerList.any {
            (it.passengerName.equals(name, true) &&
                    it.passengerLName.equals(lname, true) &&
                    it.typeOfPassenger == type)
                    ||
                    (passportNo.isNotEmpty() && it.passportNo == passportNo)
        }
    }

    fun updateButtonState(enable: Boolean,view:View) {

        val color = if (enable) R.color.primary_blue else R.color.disablebuttoncolor

        view.backgroundTintList = ContextCompat.getColorStateList(this, color)

    }

    fun clearAdultField(){
        binding.travellertypecontainer.passengerName.setText("")
        binding.travellertypecontainer.passengerLName.setText("")
        binding.travellertypecontainer.passengerAge.setText("")
        binding.travellertypecontainer.passportNo.setText("")
        binding.travellertypecontainer.passportIssuingCountry.setText("")
        binding.travellertypecontainer.passportExpireDate.setText("")
    }

    fun clearChieldField(){
        binding.travellertypecontainer.childpassengerName.setText("")
        binding.travellertypecontainer.childPassengerLName.setText("")
        binding.travellertypecontainer.childpassengerAge.setText("")
        binding.travellertypecontainer.childPassportNo.setText("")
        binding.travellertypecontainer.childPassportIssuingCountry.setText("")
        binding.travellertypecontainer.childPassportExpireDate.setText("")

    }

    fun clearInfantField(){
        binding.travellertypecontainer.infantpassengerName.setText("")
        binding.travellertypecontainer.infantPassengerLName.setText("")
        binding.travellertypecontainer.infantpassengerAge.setText("")
        binding.travellertypecontainer.infantPassportNo.setText("")
        binding.travellertypecontainer.infantPassportIssuingCountry.setText("")
        binding.travellertypecontainer.infantPassportExpireDate.setText("")
    }

    fun validateAdult(): Boolean {

        if (binding.travellertypecontainer.adultradiogroup.visibility == View.VISIBLE &&
            binding.travellertypecontainer.adultradiogroup.checkedRadioButtonId == -1
        ) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.travellertypecontainer.firstnamelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.passengerName.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.passengerName.error = "Enter name"
            binding.travellertypecontainer.passengerName.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.lastnamelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.passengerLName.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.passengerLName.error = "Enter last name"
            binding.travellertypecontainer.passengerLName.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.agelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.passengerAge.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.passengerAge.error = "Enter age"
            binding.travellertypecontainer.passengerAge.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.passportnolayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.passportNo.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.passportNo.error = "Enter passport number"
            return false
        }

        if (binding.travellertypecontainer.passportIssuingCountryLayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.passportIssuingCountry.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.passportIssuingCountry.error = "Enter country"
            return false
        }

        if (binding.travellertypecontainer.passportExpireDateLayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.passportExpireDate.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(this, "Select passport expiry date", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun validateChild(): Boolean {

        if (binding.travellertypecontainer.childradiogroup.visibility == View.VISIBLE &&
            binding.travellertypecontainer.childradiogroup.checkedRadioButtonId == -1
        ) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.travellertypecontainer.childNamelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.childpassengerName.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.childpassengerName.error = "Enter name"
            binding.travellertypecontainer.childpassengerName.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.childLNamelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.childPassengerLName.text.toString().trim().isEmpty()) {
            binding.travellertypecontainer.childPassengerLName.error = "Enter last name"
            binding.travellertypecontainer.childPassengerLName.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.childagelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.childpassengerAge.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.childpassengerAge.error = "Enter age"
            binding.travellertypecontainer.childpassengerAge.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.childpassportnolayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.childPassportNo.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.childPassportNo.error = "Enter passport number"
            return false
        }

        if (binding.travellertypecontainer.childPassportIssuingCountryLayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.childPassportIssuingCountry.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.childPassportIssuingCountry.error = "Enter country"
            return false
        }

        if (binding.travellertypecontainer.childPassportExpireDateLayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.childPassportExpireDate.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(this, "Select passport expiry date", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun validateInfant(): Boolean {

        if (binding.travellertypecontainer.infantradiogroup.visibility == View.VISIBLE &&
            binding.travellertypecontainer.infantradiogroup.checkedRadioButtonId == -1
        ) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.travellertypecontainer.infantfirstnamelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.infantpassengerName.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.infantpassengerName.error = "Enter name"
            binding.travellertypecontainer.infantpassengerName.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.infantLNamelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.infantPassengerLName.text.toString().trim().isEmpty()) {
            binding.travellertypecontainer.infantPassengerLName.error = "Enter last name"
            binding.travellertypecontainer.infantPassengerLName.requestFocus()
            return false
        }


        if (binding.travellertypecontainer.infantagelayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.infantpassengerAge.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.infantpassengerAge.error = "Enter age"
            binding.travellertypecontainer.infantpassengerAge.requestFocus()
            return false
        }

        if (binding.travellertypecontainer.infantpassportnolayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.infantPassportNo.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.infantPassportNo.error = "Enter passport number"
            return false
        }

        if (binding.travellertypecontainer.infantPassportIssuingCountryLayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.infantPassportIssuingCountry.text.toString().trim().isEmpty()
        ) {
            binding.travellertypecontainer.infantPassportIssuingCountry.error = "Enter country"
            return false
        }

        if (binding.travellertypecontainer.infantPassportExpireDateLayout.visibility == View.VISIBLE &&
            binding.travellertypecontainer.infantPassportExpireDate.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(this, "Select passport expiry date", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun showAddPassengerLayout() {
        binding.flightDetailsContainer.visibility = View.GONE
        binding.AddPassengerLayout.visibility = View.VISIBLE
        binding.addpassengerBottomselection.visibility = View.VISIBLE
        binding.secondstep.setBackgroundResource(R.drawable.selectedcircle)
        binding.firststep.setBackgroundResource(R.drawable.unselctedcircle)
        binding.thirdstep.setBackgroundResource(R.drawable.unselctedcircle)
        binding.firststeptext.setTextColor(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.secondsteptext.setTextColor(ContextCompat.getColor(this, R.color.edtblack))
        binding.thirdsteptext.setTextColor(ContextCompat.getColor(this, R.color.footer_unselected))

        binding.travellertypecontainer.apply {
            adultCardlayout.visibility = if (adultCount > 0) View.VISIBLE else View.GONE
            childCardlayout.visibility = if (childCount > 0) View.VISIBLE else View.GONE
            infantCardlayout.visibility = if (infantCount > 0) View.VISIBLE else View.GONE
        }

    }

    private fun showFlightDetailsLayout() {
        binding.flightDetailsContainer.visibility = View.VISIBLE
        binding.AddPassengerLayout.visibility = View.GONE
        binding.addpassengerBottomselection.visibility = View.GONE
        binding.secondstep.setBackgroundResource(R.drawable.unselctedcircle)
        binding.firststep.setBackgroundResource(R.drawable.selectedcircle)
        binding.thirdstep.setBackgroundResource(R.drawable.unselctedcircle)
        binding.firststeptext.setTextColor(ContextCompat.getColor(this, R.color.edtblack))
        binding.secondsteptext.setTextColor(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.thirdsteptext.setTextColor(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.title.text= getString(R.string.flightdetails)
        setPassengerDataList()
    }

    private fun showPaymentDetailsLayout(){
        binding.secondstep.setBackgroundResource(R.drawable.unselctedcircle)
        binding.firststep.setBackgroundResource(R.drawable.unselctedcircle)
        binding.thirdstep.setBackgroundResource(R.drawable.selectedcircle)
        binding.firststeptext.setTextColor(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.secondsteptext.setTextColor(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.thirdsteptext.setTextColor(ContextCompat.getColor(this, R.color.edtblack))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun openBottomSheetForShowingDetailsPickupDropUp() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.flight_pickup_dropup_pointlayout, null)
        bottomSheetDialog.setContentView(view)

        var travelDate = view.findViewById<TextView>(R.id.travelDate)
        var fromCityName = view.findViewById<TextView>(R.id.fromCityName)
        var toCityName = view.findViewById<TextView>(R.id.toCityName)
        var showterminallist = view.findViewById<RecyclerView>(R.id.showterminallist)
        var cancel = view.findViewById<ImageView>(R.id.cancelIcon)

        var travelDatetime = preference?.getStringValue(ConstantClass.dateAndTime, "")
        fromCityName.text = FromCityName
        toCityName.text = ToCityName
        travelDate.text = travelDatetime
        Log.d("travelDate", "${travelDatetime}")

        if (FlightDetails.size > 0) {
            flightDetails.clear()
            flightDetails.addAll(FlightDetails)
        }

        if (flightDetails.size > 0) {
            segmentList.clear()
            flightDetails[flightDetails.size - 1]!!.segments?.let { segmentList.addAll(it) }
        }

        flightadapter = FlightLayoverAdapter(this, flightDetails, segmentList)
        showterminallist.adapter = flightadapter
        val segments = listOf(
            mapOf(
                "departure_DateTime" to segmentList[0]!!.departureDateTime,
                "arrival_DateTime" to segmentList[0]!!.arrivalDateTime
            ),
            mapOf(
                "departure_DateTime" to segmentList[segmentList.size - 1]!!.departureDateTime,
                "arrival_DateTime" to segmentList[segmentList.size - 1]!!.arrivalDateTime
            )
        )
        totalDurationTime = calculateTotalFlightDuration(segments)

        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    fun HitApiForFareBreakUp() {
        farebreakupList.clear()
        if (fareList!!.size > 0) {
            fareList.forEach { it ->
                if (it.paXType.equals("0")) {
                    farebreakupList.add(
                        fareBreakup(
                            it.paXType,
                            it.airportTaxAmount,
                            it.basicAmount,
                            adultCount
                        )
                    )
                }

                if (it.paXType.equals("1")) {
                    farebreakupList.add(
                        fareBreakup(
                            it.paXType,
                            it.airportTaxAmount,
                            it.basicAmount,
                            childCount
                        )
                    )
                }

                if (it.paXType.equals("2")) {
                    farebreakupList.add(
                        fareBreakup(
                            it.paXType,
                            it.airportTaxAmount,
                            it.basicAmount,
                            infantCount
                        )
                    )
                }

            }

            Log.d("FareList", "" + farebreakupList)


            val totals = calculateFareTotals(farebreakupList)
            val basicTotal = totals.totalBasic
            val taxTotal = totals.totalTax
            val grandTotal = basicTotal + taxTotal
            totalRechargeAmount = grandTotal

            preference.setStringValue(ConstantClass.AirTotalTicketPrice, grandTotal.toString())
            preference.setStringValue(ConstantClass.AirTotalOperatorPrice, taxTotal.toString())
            preference.setStringValue(ConstantClass.AirTotalBasicPrice, basicTotal.toString())

            val amount = grandTotal?.roundToInt() ?: 0

            val formattedAmount = NumberFormat.getNumberInstance(Locale("en", "IN"))
                .format(amount)

            val finalAmount = "₹ $formattedAmount"
           // binding.finalAmount.text = " ₹ %.2f  ".format(finalAmount.toDouble())
            binding.finalAmount.text = finalAmount


            addValueForAirTicket(farebreakupList)



        }

    }

    fun calculateFareTotals(fareList: List<fareBreakup>): FareTotal {
        var totalBasic = 0.0
        var totalTax = 0.0

        fareList.forEach { item ->
            val basic = item.basic_amount.toDoubleOrNull() ?: 0.0
            val tax = item.airportTax_amount.toDoubleOrNull() ?: 0.0
            val quantity = item.quantity

            totalBasic += basic * quantity
            totalTax += tax * quantity
        }

        return FareTotal(
            totalBasic = totalBasic,
            totalTax = totalTax
        )
    }

    data class FareTotal(
        val totalBasic: Double,
        val totalTax: Double
    )

    fun addValueForAirTicket(farebreakupList: MutableList<fareBreakup>) {

        val adult = farebreakupList.find { it.paxType == "0" }
        val child = farebreakupList.find { it.paxType == "1" }
        val infant = farebreakupList.find { it.paxType == "2" }

        val adultAmount = (adult?.quantity ?: 0) * (adult?.basic_amount?.toDoubleOrNull() ?: 0.0)
        val childAmount = (child?.quantity ?: 0) * (child?.basic_amount?.toDoubleOrNull() ?: 0.0)
        val infantAmount = (infant?.quantity ?: 0) * (infant?.basic_amount?.toDoubleOrNull() ?: 0.0)


        val airportTaxadultAmount = (adult?.quantity ?: 0) * (adult?.airportTax_amount?.toDoubleOrNull() ?: 0.0)
        val airportTaxchildAmount = (child?.quantity ?: 0) * (child?.airportTax_amount?.toDoubleOrNull() ?: 0.0)
        val airportTaxinfantAmount = (infant?.quantity ?: 0) * (infant?.airportTax_amount?.toDoubleOrNull() ?: 0.0)


        // Ensure all are Double or Int before addition
        val totalBaseFare = adultAmount + childAmount + infantAmount
        val totalTaxFare = airportTaxadultAmount + airportTaxchildAmount + airportTaxinfantAmount
        val overallTotalAmount = totalBaseFare + totalTaxFare


        BaseFare = "₹ ".plus(totalBaseFare.toInrFormat())
        TaxAndFees = "₹ ".plus(totalTaxFare.toInrFormat())
        GrossFare = "₹ ".plus(overallTotalAmount.toInrFormat())

    }

    fun openCalendar(type: String) {
        val constraints = CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build()
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date") .setCalendarConstraints(constraints).build()
        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = Date(selection)
            val date = convertDate(selectedDate.toString())
            val searchDate = searchDateIfNotInList(selectedDate.toString())
            when(type){
                ConstantClass.Adult -> {
                    adultPassportExpiryDate = searchDate
                }
                ConstantClass.Child-> {
                    childPassportExpiryDate = searchDate
                }
                ConstantClass.Infant-> {
                    infantPassportExpiryDate = searchDate
                    }
            }
            adultPassportExpiryDate = searchDate
            binding.travellertypecontainer.passportExpireDate.setText(searchDate)

            Log.d("date", "openCalendar: $date")
            Toast.makeText(this, selectedDate.toString(), Toast.LENGTH_SHORT).show()
        }


    }

    fun showAndHideView(){
        paxDetailsListFromReprice.forEach { pax ->
            Log.d("PaxList",Gson().toJson(pax))

            when (pax.paxType) {

                // Adult
                0 -> {
                    binding.travellertypecontainer.adultradiogroup.visibility =
                        if (pax.title) View.VISIBLE else View.INVISIBLE

                    binding.travellertypecontainer.firstnamelayout.visibility =
                        if (pax.firstName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.lastnamelayout.visibility =
                        if (pax.lastName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.agelayout.visibility =
                        if (pax.dob) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.passportnolayout.visibility =
                        if (pax.passportNumber) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.passportIssuingCountryLayout.visibility =
                        if (pax.passportIssuingCountry) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.passportExpireDateLayout.visibility =
                        if (pax.passportExpiry) View.VISIBLE else View.GONE
                }

                // Child
                1 -> {
                    binding.travellertypecontainer.childradiogroup.visibility =
                        if (pax.title) View.VISIBLE else View.INVISIBLE

                    binding.travellertypecontainer.childNamelayout.visibility =
                        if (pax.firstName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childLNamelayout.visibility =
                        if (pax.lastName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childagelayout.visibility =
                        if (pax.dob) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childpassportnolayout.visibility =
                        if (pax.passportNumber) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childPassportIssuingCountryLayout.visibility =
                        if (pax.passportIssuingCountry) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childPassportExpireDateLayout.visibility =
                        if (pax.passportExpiry) View.VISIBLE else View.GONE
                }

                // Infant
                2 -> {
                    binding.travellertypecontainer.infantradiogroup.visibility =
                        if (pax.title) View.VISIBLE else View.INVISIBLE

                    binding.travellertypecontainer.infantfirstnamelayout.visibility =
                        if (pax.firstName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantLNamelayout.visibility =
                        if (pax.lastName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantagelayout.visibility =
                        if (pax.dob) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantpassportnolayout.visibility =
                        if (pax.passportNumber) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantPassportIssuingCountryLayout.visibility =
                        if (pax.passportIssuingCountry) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantPassportExpireDateLayout.visibility =
                        if (pax.passportExpiry) View.VISIBLE else View.GONE
                }
            }
        }

    }


    override fun clickOnEdit(position: Int, item: FlightPassengerDataModel) {
        var passengerType = item.typeOfPassenger
        passengerListForUpdate = item
        updatePosition = position
        showAddPassengerLayout()
        showAndHideViewForEdit(passengerType, item)
    }


    override fun clickOnDelete(position: Int, item: FlightPassengerDataModel) {
        openDialogForDeletePassengerInDataList(item,position)
    }


    override fun onPassengerCheckChanged(isChecked: Boolean, passenger: FlightPassengerDataModel) {
        if (isChecked) {
            if (isPassengerLimitReached(passenger.typeOfPassenger)) {
                showLimitAlert(passenger.typeOfPassenger)
                passenger.checked = false
                passengerDataAdapter.notifyDataSetChanged()
                return
            }

            addPassengerToCorrectPosition(passenger)
        }
        else {
            removePassenger(passenger)
            if (!isPassengerLimitReached(passenger.typeOfPassenger)) {
                setanimationForCloseFinalAmountLayout(binding.showingAlert)
            }
        }

        Log.d("PassengerTicketList",Gson().toJson(PassengerListForTicket))

    }


    fun showAndHideViewForEdit(passengerType:String,item: FlightPassengerDataModel){
        paxDetailsListFromReprice.forEach { pax ->
            Log.d("passengerType",passengerType)
            Log.d("passengerItem",Gson().toJson(item))
            binding.title.text= getString(R.string.editData)

            when {

                // Adult
                passengerType== ConstantClass.Adult && pax.paxType==0 -> {
                    // setDataOnUi
                        when (item.genderType) {
                            ConstantClass.Mr -> binding.travellertypecontainer.adultradiogroup.check(R.id.adultmale)
                            ConstantClass.Ms -> binding.travellertypecontainer.adultradiogroup.check(R.id.adultfemale)
                            ConstantClass.Mrs -> binding.travellertypecontainer.adultradiogroup.check(R.id.adultfemalemaried)
                        }

                     binding.travellertypecontainer.passengerAge.setText(item.age)
                     binding.travellertypecontainer.passengerLName.setText(item.passengerLName)
                     binding.travellertypecontainer.passengerName.setText(item.passengerName)
                     binding.travellertypecontainer.passportNo.setText(item.passportNo)
                     binding.travellertypecontainer.passportIssuingCountry.setText(item.passportIssueCountryName)
                     binding.travellertypecontainer.passportExpireDate.setText(item.passportExpiryDate)



                    binding.travellertypecontainer.adultCardlayout.visibility = View.VISIBLE
                    binding.travellertypecontainer.childCardlayout.visibility = View.GONE
                    binding.travellertypecontainer.infantCardlayout.visibility = View.GONE

                    binding.travellertypecontainer.nextbutton.text= getString(R.string.update)
                    binding.travellertypecontainer.childnextbutton.text= getString(R.string.add)
                    binding.travellertypecontainer.infantnextbutton.text= getString(R.string.add)


                    binding.travellertypecontainer.adultradiogroup.visibility =
                        if (pax.title) View.VISIBLE else View.INVISIBLE

                    binding.travellertypecontainer.firstnamelayout.visibility =
                        if (pax.firstName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.lastnamelayout.visibility =
                        if (pax.lastName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.agelayout.visibility =
                        if (pax.dob) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.passportnolayout.visibility =
                        if (pax.passportNumber) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.passportIssuingCountryLayout.visibility =
                        if (pax.passportIssuingCountry) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.passportExpireDateLayout.visibility =
                        if (pax.passportExpiry) View.VISIBLE else View.GONE
                }

                // Child
                passengerType== ConstantClass.Child && pax.paxType==1  -> {

                    // setDataOnUi
                    when (item.genderType) {
                        ConstantClass.Mstr -> binding.travellertypecontainer.childradiogroup.check(R.id.childmale)
                        ConstantClass.Ms -> binding.travellertypecontainer.childradiogroup.check(R.id.childfemale)

                    }

                    binding.travellertypecontainer.childpassengerAge.setText(item.age)
                    binding.travellertypecontainer.childPassengerLName.setText(item.passengerLName)
                    binding.travellertypecontainer.childpassengerName.setText(item.passengerName)
                    binding.travellertypecontainer.childPassportNo.setText(item.passportNo)
                    binding.travellertypecontainer.childPassportIssuingCountry.setText(item.passportIssueCountryName)
                    binding.travellertypecontainer.childPassportExpireDate.setText(item.passportExpiryDate)



                    binding.travellertypecontainer.adultCardlayout.visibility = View.GONE
                    binding.travellertypecontainer.childCardlayout.visibility = View.VISIBLE
                    binding.travellertypecontainer.infantCardlayout.visibility = View.GONE

                    binding.travellertypecontainer.nextbutton.text= getString(R.string.add)
                    binding.travellertypecontainer.childnextbutton.text= getString(R.string.update)
                    binding.travellertypecontainer.infantnextbutton.text= getString(R.string.add)

                    binding.travellertypecontainer.childradiogroup.visibility =
                        if (pax.title) View.VISIBLE else View.INVISIBLE

                    binding.travellertypecontainer.childNamelayout.visibility =
                        if (pax.firstName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childLNamelayout.visibility =
                        if (pax.lastName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childagelayout.visibility =
                        if (pax.dob) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childpassportnolayout.visibility =
                        if (pax.passportNumber) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childPassportIssuingCountryLayout.visibility =
                        if (pax.passportIssuingCountry) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.childPassportExpireDateLayout.visibility =
                        if (pax.passportExpiry) View.VISIBLE else View.GONE
                }

                // Infant
                passengerType==  ConstantClass.Infant && pax.paxType==2 -> {

                    // setDataOnUi
                    when (item.genderType) {
                        ConstantClass.Mstr -> binding.travellertypecontainer.infantradiogroup.check(R.id.infantmale)
                        ConstantClass.Ms -> binding.travellertypecontainer.infantradiogroup.check(R.id.infantfemale)

                    }

                    binding.travellertypecontainer.infantpassengerAge.setText(item.age)
                    binding.travellertypecontainer.infantPassengerLName.setText(item.passengerLName)
                    binding.travellertypecontainer.infantpassengerName.setText(item.passengerName)
                    binding.travellertypecontainer.infantPassportNo.setText(item.passportNo)
                    binding.travellertypecontainer.infantPassportIssuingCountry.setText(item.passportIssueCountryName)
                    binding.travellertypecontainer.infantPassportExpireDate.setText(item.passportExpiryDate)

                    binding.travellertypecontainer.adultCardlayout.visibility = View.GONE
                    binding.travellertypecontainer.childCardlayout.visibility = View.GONE
                    binding.travellertypecontainer.infantCardlayout.visibility = View.VISIBLE

                    binding.travellertypecontainer.nextbutton.text= getString(R.string.add)
                    binding.travellertypecontainer.childnextbutton.text= getString(R.string.add)
                    binding.travellertypecontainer.infantnextbutton.text= getString(R.string.update)

                    binding.travellertypecontainer.infantradiogroup.visibility =
                        if (pax.title) View.VISIBLE else View.INVISIBLE

                    binding.travellertypecontainer.infantfirstnamelayout.visibility =
                        if (pax.firstName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantLNamelayout.visibility =
                        if (pax.lastName) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantagelayout.visibility =
                        if (pax.dob) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantpassportnolayout.visibility =
                        if (pax.passportNumber) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantPassportIssuingCountryLayout.visibility =
                        if (pax.passportIssuingCountry) View.VISIBLE else View.GONE

                    binding.travellertypecontainer.infantPassportExpireDateLayout.visibility =
                        if (pax.passportExpiry) View.VISIBLE else View.GONE
                }
            }
        }


    }


    fun openDialogForDeletePassengerInDataList(item: FlightPassengerDataModel, position: Int){
        dialog = Dialog(this)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.passengrer_removedata_layout)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        dialog.setCanceledOnTouchOutside(false)

        var cancel = dialog.findViewById<ImageView>(R.id.cancelimage)
        var removeBtn = dialog.findViewById<LinearLayout>(R.id.removeBtn)
        var title = dialog.findViewById<TextView>(R.id.title)

        title.text= "Remove ${item.passengerName} from saved list"

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        removeBtn.setOnClickListener {
            // 1️⃣ Remove from futureUsedDataList
            filterPassengerList.removeAt(position)
            passengerDataAdapter?.notifyItemRemoved(position)
            saveInLoaclDataList = preference.getFutureFlightDataList(ConstantClass.FlightPassengerFutureDate).toMutableList()

            if(item!in saveInLoaclDataList) return@setOnClickListener
            // Remove same passenger from database list
            saveInLoaclDataList.remove(item)
            preference.saveForFutureFlightData(ConstantClass.FlightPassengerFutureDate, saveInLoaclDataList)

            // 4️⃣ ALSO remove from PassengerListForTicket if checked
            val index = PassengerListForTicket.indexOfFirst {
                it.passengerName == item.passengerName &&
                        it.typeOfPassenger == item.typeOfPassenger
            }

            if (index != -1) {
                PassengerListForTicket[index] = PassengerListForTicket[index].copy(passengerName = "")
            }
            dialog.dismiss()
            updatePassengerCount()

        }

        dialog.show()

    }


    //Find the first index of that passenger type and insert there.
    fun addPassengerToCorrectPosition(passenger: FlightPassengerDataModel) {

        val alreadyAdded = PassengerListForTicket.any {
            it.passengerName == passenger.passengerName &&
                    it.typeOfPassenger == passenger.typeOfPassenger
        }

        if (alreadyAdded) return

        val index = PassengerListForTicket.indexOfFirst {
            it.typeOfPassenger == passenger.typeOfPassenger &&
                    it.passengerName.isEmpty()
        }
        if (index != -1) {
            PassengerListForTicket[index] = passenger
        } else {
            showLimitAlert(passenger.typeOfPassenger)
            return
        }

        binding.savedPassengerListShown.post {
            passengerDataAdapter.notifyDataSetChanged()
        }

        updatePassengerCount()
    }


    fun updatePassengerCount() {

        var adultCount = 0
        var childCount = 0
        var infantCount = 0

        PassengerListForTicket.forEach {
            when  {
                it.typeOfPassenger.equals(ConstantClass.Adult)  && it.passengerName.isNotEmpty() -> adultCount++
                it.typeOfPassenger.equals(ConstantClass.Child) && it.passengerName.isNotEmpty() -> childCount++
                it.typeOfPassenger.equals(ConstantClass.Infant) && it.passengerName.isNotEmpty() -> infantCount++
            }
        }

        totalAddNumber = adultCount + childCount + infantCount

        binding.numberOfPassemger.text = "$totalAddNumber/${PassengerListForTicket.size}"

        binding.adultcount.text="Adults : ${adultCount}/${ConstantClass.adultCount}"
        binding.childcount.text="Child : ${childCount}/${ConstantClass.childCount}"
        binding.infantcount.text="Infant : ${infantCount}/${ConstantClass.infantCount}"

    }


    fun removePassenger(passenger: FlightPassengerDataModel) {

        val index = PassengerListForTicket.indexOfFirst {
            it.passengerName == passenger.passengerName &&
                    it.typeOfPassenger == passenger.typeOfPassenger
        }

        if (index != -1) {

            PassengerListForTicket[index] = PassengerListForTicket[index].copy(
                passengerName = ""
            )
        }

        binding.savedPassengerListShown.post {
            passengerDataAdapter.notifyDataSetChanged()
        }

        updatePassengerCount()
    }


    fun isPassengerLimitReached(type: String): Boolean {

        val count = PassengerListForTicket.count {
            it.typeOfPassenger == type && it.passengerName.isNotEmpty()
        }

        val limit = PassengerListForTicket.count {
            it.typeOfPassenger == type
        }

        return count >= limit
    }


    fun showLimitAlert(type: String) {
        binding.alertmsg.text="Passenger Limit Reached , maximum $type passengers already selected. Uncheck one passenger first."
        setanimationForOpenFinalAmountLayout(binding.showingAlert)
    }


    fun setanimationForOpenFinalAmountLayout(view : View){
        view.alpha = 0f
        view.visibility = View.VISIBLE

        view.animate()
            .alpha(1f)
            .setDuration(300)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }


    fun setanimationForCloseFinalAmountLayout(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(300)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                view.visibility = View.GONE
            }
            .start()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validationPassenger() {
        val mailId = preference.getStringValue(ConstantClass.EmailID,"")
        val mobileNumber = preference.getStringValue(ConstantClass.ContactNumber,"")

        when {
            mailId!!.isBlank() -> {
                binding.contactdetailsvalidationlayout.visibility=View.VISIBLE
            }

            !Patterns.EMAIL_ADDRESS.matcher(mailId).matches() -> {
                binding.contactdetailsvalidationlayout.visibility=View.VISIBLE
            }

            mobileNumber!!.isBlank() -> {
                binding.contactdetailsvalidationlayout.visibility=View.VISIBLE
            }

            !mobileNumber.matches(Regex("^[6-9]\\d{9}$")) -> {
                binding.contactdetailsvalidationlayout.visibility=View.VISIBLE
            }
            totalAddNumber<totalCount ->{
                binding.customervalidationlayout.visibility = View.VISIBLE
            }

            else -> {
                // All valid ✅  TEMP BOOKING
                binding.contactdetailsvalidationlayout.visibility=View.GONE
                binding.customervalidationlayout.visibility = View.GONE
                var tempdata = TempBookingPassangerDetails(mobileNumber,mobileNumber,mailId,false,"","","")
                tempBookingPassangerDetails.add(tempdata)
                hitApiForFlightTempBooking()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun hitApiForFlightTempBooking(){
        var customMob =  preference!!.getStringValue(ConstantClass.MobileNumber, "")!!
        var passangermob = tempBookingPassangerDetails[0].passengerMobile
        var passemail = tempBookingPassangerDetails[0].passengerEmail
        var passgst = tempBookingPassangerDetails[0].gst
        var passgstnumber = tempBookingPassangerDetails[0].gsT_Number
        var passgstholdername = tempBookingPassangerDetails[0].gsTHolderName
        var passgstaddress = tempBookingPassangerDetails[0].gsTAddress

        var flightTempBookingreq = FlightTempBookingReq(
            customerMobile = customMob , // for testing purpose
            passengerMobile =passangermob ,
            whatsAPPMobile = passangermob,
            passengerEmail = passemail,
            gst = passgst,
            gsT_Number = passgstnumber,
            gsTHolderName =passgstholdername ,
            gsTAddress = passgstaddress,
            costCenterId = 0,
            projectId = 0,
            bookingRemark = "",
            corporateStatus = 0,
            corporatePaymentMode = 0,
            missedSavingReason = "",
            corpTripType = "",
            corpTripSubType = "",
            tripRequestId = "",
            bookingAlertIds = "",
            iPAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId =  preference?.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber,"").toString(),
            registrationID = preference?.getStringValue(ConstantClass.MerchantId, ""),
            bookingFlightDetails = getBookingFlightDetails(),
            paX_Details = getpaxDetails()
        )

        Log.d("FlightTempBookingReq", Gson().toJson(flightTempBookingreq))

        viewModel.getFlightTempBookingRequest(flightTempBookingreq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                    ConstantClass.dialog.dismiss()
                                }
                                Log.d("tempresp",Gson().toJson(response))

                                Log.d("FlightTempResponse", response.responseHeader.errorCode)

                                preference!!.setStringValue(ConstantClass.BookingRefNo,response.bookingRefNo)

                                if(response.responseHeader.errorCode.equals("0000")){
                                    passengerTicketList.clear()
                                    passengerTicketList.addAll(getpaxDetails())
                                    startActivity(Intent(this, ReviewTempBookingPage::class.java))
                                }

                                if(response.responseHeader.errorCode.equals("0004")){

                                    Toast.makeText(this,response.responseHeader.errorDesc,Toast.LENGTH_SHORT).show()
                                }

                                if(response.responseHeader.errorCode.equals("0006")){

                                    Toast.makeText(this,response.responseHeader.errorDesc,Toast.LENGTH_SHORT).show()
                                }

                                Log.d("FlightRePriseResponse", Gson().toJson(response))

                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.loading(this)
                    }
                }
            }
        }

    }


    fun getBookingFlightDetails() : MutableList<BookingFlightDetails>{
        var flightsearchkey = preference!!.getStringValue(ConstantClass.FlightSearchKey,"")
        var flightkey = preference?.getStringValue(ConstantClass.FlightKey,"")
        var bookingFlightDetails: MutableList<BookingFlightDetails> = mutableListOf()
        var bookingSSRLits : MutableList<BookingSSRDetails> = mutableListOf()
        bookingFlightDetails.clear()

        bookingFlightDetails.add(
            BookingFlightDetails(searchKey = flightsearchkey.toString(),
                flightKey = flightkey,
                bookingSSRDetails = bookingSSRLits,
            )
        )

        return bookingFlightDetails
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getpaxDetails(): MutableList<PaXDetailsFlight> {

        val paxDetailsList = mutableListOf<PaXDetailsFlight>()

        PassengerListForTicket.forEachIndexed { index, passenger ->

            val paxType = when (passenger.typeOfPassenger.uppercase()) {
                "ADULT" -> 0
                "CHILD" -> 1
                "INFANT" -> 2
                else -> 0 // fallback default
            }

            val genderCode = when (passenger.gender.trim().lowercase()) {
                "male" -> 0
                "female" -> 1
                else -> 0 // default to Male
            }

            // Basic validation (adjust as needed)
            if (passenger.passengerName.isNullOrBlank() || passenger.passengerLName.isNullOrBlank()) {
                // Skip invalid entries
                return@forEachIndexed
            }

            var dateOfBirth =""
            if(passenger.age.isNotEmpty()){
                 dateOfBirth = calculateDOBFromAge(passenger.age)
            }else{
                 dateOfBirth = ""
            }


            val pax = PaXDetailsFlight(
                pax_Id = index + 1, // 1-based index
                paxtype = paxType,
                title = passenger.gender ?: "",
                firstName = passenger.passengerName ?: "",
                lastName = passenger.passengerLName ?: "",
                gender = genderCode,
                age = passenger.age, // optional, can calculate if needed
                dob = dateOfBirth ?: "",
                passportNumber = passenger.passportNo ?: "",
                passportIssuingCountry = passenger.passportIssueCountryName ?: "",
                passportExpiry = passenger.passportExpiryDate ?: "",
                nationality = "",
                frequentFlyerDetails = ""
            )

            paxDetailsList.add(pax)
        }

        return paxDetailsList
    }

}