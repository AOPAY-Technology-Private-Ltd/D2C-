package com.example.twopayd2c.ui.view.activity.travel.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.R
import com.example.twopayd2c.adapter.CurrentWithNextDateAdapter
import com.example.twopayd2c.adapter.RecentBusSearchAdapter
import com.example.twopayd2c.data.model.travelmodel.RecentSearchListModel
import com.example.twopayd2c.data.model.travelmodel.flight.FilterAirLine
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchReq
import com.example.twopayd2c.data.model.travelmodel.flight.TripInfo
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.FragmentFlightSearchHomePageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.FlightSpecialOfferAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusCitySearchPage
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSearchDetails
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightBookingPage
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightCitySearchPage
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightSearchDetails
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.SelectTravellersClassBottomSheet
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.AllFlightList
import com.example.twopayd2c.utils.ConstantClass.Companion.BusDepartureDateAndTime
import com.example.twopayd2c.utils.ConstantClass.Companion.CheckNonStop
import com.example.twopayd2c.utils.ConstantClass.Companion.Currentwithnext5dateList
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightDepartureDateAndTime
import com.example.twopayd2c.utils.ConstantClass.Companion.FromBus
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityId
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.SearchFromToEditText
import com.example.twopayd2c.utils.ConstantClass.Companion.ToBus
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityId
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.TripDetailsList
import com.example.twopayd2c.utils.ConstantClass.Companion.adultCount
import com.example.twopayd2c.utils.ConstantClass.Companion.after12pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6amlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.airportNameList
import com.example.twopayd2c.utils.ConstantClass.Companion.before6layout
import com.example.twopayd2c.utils.ConstantClass.Companion.bookingType
import com.example.twopayd2c.utils.ConstantClass.Companion.checkReverse
import com.example.twopayd2c.utils.ConstantClass.Companion.childCount
import com.example.twopayd2c.utils.ConstantClass.Companion.className
import com.example.twopayd2c.utils.ConstantClass.Companion.classNumber
import com.example.twopayd2c.utils.ConstantClass.Companion.convertDate
import com.example.twopayd2c.utils.ConstantClass.Companion.datepassangerandclassstring
import com.example.twopayd2c.utils.ConstantClass.Companion.defenceFare
import com.example.twopayd2c.utils.ConstantClass.Companion.flightTypeList
import com.example.twopayd2c.utils.ConstantClass.Companion.fromAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.fromAirportName
import com.example.twopayd2c.utils.ConstantClass.Companion.fromCountryName
import com.example.twopayd2c.utils.ConstantClass.Companion.generateRandomNumber
import com.example.twopayd2c.utils.ConstantClass.Companion.getCurrentwithnext5date
import com.example.twopayd2c.utils.ConstantClass.Companion.infantCount
import com.example.twopayd2c.utils.ConstantClass.Companion.searchDateFormList
import com.example.twopayd2c.utils.ConstantClass.Companion.searchDateIfNotInList
import com.example.twopayd2c.utils.ConstantClass.Companion.srCitizen
import com.example.twopayd2c.utils.ConstantClass.Companion.studentFare
import com.example.twopayd2c.utils.ConstantClass.Companion.toAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.toAirportName
import com.example.twopayd2c.utils.ConstantClass.Companion.toCountryName
import com.example.twopayd2c.utils.ConstantClass.Companion.totalCount
import com.example.twopayd2c.utils.ConstantClass.Companion.travelDateFormat
import com.example.twopayd2c.utils.ConstantClass.Companion.travelType
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date


class FlightSearchHomePageFragment : Fragment(), FlightSpecialOfferAdapter.setClickListner , CurrentWithNextDateAdapter.onItemClickListner {
    private var specialFareList: MutableList<Pair<String, Boolean>> = mutableListOf()
    var recentSearchList: MutableList<RecentSearchListModel> = mutableListOf()
    lateinit var binding : FragmentFlightSearchHomePageBinding
    lateinit var adapter : CurrentWithNextDateAdapter
    lateinit var logtag : String
    var searchDateForTravel : String=""
    lateinit var selectedtypeface : Typeface
    lateinit var unselectedtypeface : Typeface
    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    var CheckOneWay: Boolean = true
    var CheckStopType: Boolean = false


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFlightSearchHomePageBinding.inflate(layoutInflater, container, false)
        logtag = "FlightSearchHomePageFragment"
        unselectedtypeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)!!
        selectedtypeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)!!
        preference = SharedPreference.getInstance(requireContext())!!
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]


        getCurrentwithnext5date()
        withoutSelectionTripType()
        addDataInList()
        setDataInView()
        setOnClickListner()
        binding.onewaySelection.setBackgroundResource(R.drawable.tripselectionbg)
        binding.onewaySelection.typeface = selectedtypeface
        return binding.root
    }



    override fun onResume() {
            super.onResume()
            setData()
            if(FromCityName.isNotEmpty() || ToCityName.isNotEmpty()){
                binding.fromcityname.text = FromCityName
                binding.tocityname.text = ToCityName
            }
    }



    public fun setData() {

        binding.fromlayout.visibility = View.VISIBLE

        binding.tolayout.visibility = View.VISIBLE

        totalCount = (adultCount + childCount + infantCount).toString()

        binding.cabinname.text = "${totalCount}(${className})"

       Log.d("Classname", className)


        if (checkReverse) {
            binding.fromcityname.text = ToCityName
            binding.tocityname.text = FromCityName
        }
        else {
            binding.tocityname.text = FromCityName
            binding.fromcityname.text = ToCityName
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDataInView(){
        var df =  SimpleDateFormat("MMM,yyyy")
        var now = df.format( Date())
        binding.monthyear.text= now

        if(Currentwithnext5dateList.isNotEmpty()){
            adapter= CurrentWithNextDateAdapter(Currentwithnext5dateList,requireContext(),this)
            binding.currentDaydate.adapter = adapter
            adapter.selectedPosition=0
            searchDateForTravel= searchDateFormList(Currentwithnext5dateList[0])
        }
        Log.d(logtag, "setDataInView: "+now)

    }

    private fun addDataInList() {
        specialFareList.apply {
            clear()
            add(Pair("Defence Forces", false))
            add(Pair("Students", false))
            add(Pair("Senior Citizens", false))
        }

        val adapter = FlightSpecialOfferAdapter(requireContext(), specialFareList, this)
        binding.extraofferList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickListner(){

        binding.fromcityLayout.setOnClickListener {
            SearchFromToEditText = FromBus
            startActivity(Intent(requireContext(), FlightCitySearchPage::class.java))
        }

        binding.tocityLayout.setOnClickListener {
            SearchFromToEditText = ToBus
            startActivity(Intent(requireContext(), FlightCitySearchPage::class.java))
        }

        binding.reversetrip.setOnClickListener {

            // Swap City Names
            val tempCity = FromCityName
            FromCityName = ToCityName
            ToCityName = tempCity

            // Swap Airport Codes
            val tempCode = fromAirportCode
            fromAirportCode = toAirportCode
            toAirportCode = tempCode

            // Swap Airport Names
            val tempAirportName = fromAirportName
            fromAirportName = toAirportName
            toAirportName = tempAirportName

            // Swap Country
            val tempCountry = fromCountryName
            fromCountryName = toCountryName
            toCountryName = tempCountry

            // Update UI
            binding.fromcityname.text = FromCityName
            binding.tocityname.text = ToCityName

        }

        binding.calendarLayout.setOnClickListener {
            openCalendar()
        }

        binding.onewaySelection.setOnClickListener {
            CheckOneWay
            withoutSelectionTripType()
            binding.onewaySelection.setBackgroundResource(R.drawable.tripselectionbg)
            binding.onewaySelection.typeface = selectedtypeface
            moveTowardsEachOther(binding.onewaySelection, binding.roundTripSelection)
            binding.addlayout.visibility = View.VISIBLE
            binding.returncalendarLayout.visibility = View.GONE
        }

        binding.roundTripSelection.setOnClickListener {
            withoutSelectionTripType()
            binding.roundTripSelection.setBackgroundResource(R.drawable.tripselectionbg)
            binding.roundTripSelection.typeface = selectedtypeface
            moveTowardsEachOther(binding.roundTripSelection, binding.onewaySelection)
            var df =  SimpleDateFormat("dd MMM yyyy")
            var now = df.format( Date())
            binding.returnDate.text = now
            binding.addlayout.visibility = View.GONE
            binding.returncalendarLayout.visibility = View.VISIBLE
        }

        binding.addreturn.setOnClickListener {
            CheckOneWay=false
            withoutSelectionTripType()
            binding.roundTripSelection.setBackgroundResource(R.drawable.tripselectionbg)
            binding.roundTripSelection.typeface = selectedtypeface
            moveTowardsEachOther(binding.roundTripSelection, binding.onewaySelection)
            openReturnCalendar()
        }

        binding.cancel.setOnClickListener {
            withoutSelectionTripType()
            binding.onewaySelection.setBackgroundResource(R.drawable.tripselectionbg)
            binding.onewaySelection.typeface = selectedtypeface
            binding.addlayout.visibility = View.VISIBLE
            binding.returncalendarLayout.visibility = View.GONE
        }

        binding.cabinclass.setOnClickListener {
            val bottomSheet = SelectTravellersClassBottomSheet()
            bottomSheet.show(childFragmentManager, SelectTravellersClassBottomSheet.TAG)

        }

        childFragmentManager.setFragmentResultListener(SelectTravellersClassBottomSheet.TAG,viewLifecycleOwner){ _, _ ->
            // This runs when bottom sheet is dismissed
            setData()
        }

        binding.flightSearchButton.setOnClickListener {
            validationCity()
        }

        binding.infofarelayout.setOnClickListener {
            OpenSpecialFareOfferLayout()
        }

        binding.checkbox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {

            override fun onCheckedChanged(p0: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    CheckNonStop = isChecked
                } else {
                    CheckNonStop = isChecked
                }
            }
        })

       binding.flightbookinglayout.setOnClickListener {
           startActivity(Intent(requireContext(), FlightBookingPage::class.java))
       }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validationCity() {
        val errorMessage: String? = when{
            binding.fromcityname.text.toString().isNullOrBlank() -> "Please select leaving city name"
            binding.tocityname.text.toString().isNullOrBlank() -> "Please select going city name"
            searchDateForTravel.isNullOrBlank() -> "Please select travel date"
            else -> null
        }
        if (errorMessage != null){
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
        else {
            val newItem = RecentSearchListModel(
                binding.fromcityname.text.toString(),
                fromcityId = fromAirportCode,
                fromCountryName,
                searchDateForTravel,
                binding.tocityname.text.toString(),
                toCountryName,
                tocityId = toAirportCode
            )

            val savedDataList = preference.getList(ConstantClass.RecentSavedFlightData).toMutableList()

            // 1️⃣ Remove duplicate (same from, to, date)
            savedDataList.removeAll { it.fromcity == newItem.fromcity && it.tocity == newItem.tocity && it.searchdate == newItem.searchdate && it.fromcityId == newItem.fromcityId && it.tocityId == newItem.tocityId }

            // 2️⃣ Add newest item at top
            savedDataList.add(0, newItem)

            if (savedDataList.size > 5) {
                savedDataList.subList(5, savedDataList.size).clear()
            }

            if (childCount == 0 && infantCount == 0) {
                datepassangerandclassstring = searchDateForTravel.toString().plus("|").plus(adultCount).plus("Adult").plus("|").plus(className)
            }
            else {
                datepassangerandclassstring = searchDateForTravel.toString().plus("|").plus(totalCount).plus("Travellers").plus("|").plus(className)
            }

            FromCityName = binding.fromcityname.text.toString()
            ToCityName = binding.tocityname.text.toString()
            preference.savelist(ConstantClass.RecentSavedFlightData, savedDataList)
            preference?.setStringValue(ConstantClass.fromDesignationId, fromAirportCode)
            preference?.setStringValue(ConstantClass.fromDesignationName, binding.fromcityname.text.toString())
            preference?.setStringValue(ConstantClass.toDesignationId, toAirportCode)
            preference?.setStringValue(ConstantClass.toDesignationName, binding.tocityname.text.toString())
            preference?.setStringValue(ConstantClass.dateAndTime, searchDateForTravel)
            preference!!.setStringValue(ConstantClass.MobileNumber, preference.getStringValue(ConstantClass.CustomerMobileNumber,""))
            startActivity(Intent(requireContext(), FlightSearchDetails::class.java))
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun openCalendar() {
        val constraints = CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build()
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date") .setCalendarConstraints(constraints).build()
        datePicker.show(requireFragmentManager(), "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = Date(selection)
            val date = convertDate(selectedDate.toString())
            val searchDate = searchDateIfNotInList(selectedDate.toString())
            binding.searchDate.text = searchDate
            searchDateForTravel = searchDate

            // Find matching index
            val index = Currentwithnext5dateList.indexOf(date)

            if (index != -1) {
                // Date found in next 5 days list
                adapter.updateViewSelection(index)
                binding.currentDaydate.smoothScrollToPosition(index)
                binding.searchDate.visibility = View.GONE
                binding.monthyear.visibility = View.VISIBLE
                binding.currentDaydate.visibility = View.VISIBLE
            } else {
                // Date not in list
                binding.searchDate.visibility = View.VISIBLE
                binding.monthyear.visibility = View.GONE
                binding.currentDaydate.visibility = View.GONE
            }
            if(CheckOneWay){
                val bottomSheet = SelectTravellersClassBottomSheet()
                bottomSheet.show(childFragmentManager, SelectTravellersClassBottomSheet.TAG)
            }

            Log.d(logtag, "openCalendar: $date")
            Toast.makeText(requireContext(), selectedDate.toString(), Toast.LENGTH_SHORT).show()
        }


    }


    fun openReturnCalendar() {

        // Convert onward date to millis
        val onwardDateMillis = ConstantClass.convertToMillis(searchDateForTravel)

        val constraints = CalendarConstraints.Builder()
            .setStart(onwardDateMillis)   // cannot select before onward date
            .setValidator(DateValidatorPointForward.from(onwardDateMillis))
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Return Date")
            .setSelection(onwardDateMillis)
            .setCalendarConstraints(constraints)
            .build()

        datePicker.show(requireFragmentManager(), "RETURN_DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->

            val selectedDate = Date(selection)
            val formattedDate = searchDateIfNotInList(selectedDate.toString())

            binding.addlayout.visibility = View.GONE
            binding.returncalendarLayout.visibility = View.VISIBLE

           // bookingType = 1   // Round trip

            binding.returnDate.text = formattedDate

           // returnDateForTravel = formattedDate

            val bottomSheet = SelectTravellersClassBottomSheet()
            bottomSheet.show(childFragmentManager, SelectTravellersClassBottomSheet.TAG)

            Log.d(logtag, "Return date selected: $formattedDate")
        }

    }



    override fun itemclickListner(position: Int) {
        when (specialFareList[position].first) {
            ConstantClass.seniorCitizen -> {
                srCitizen = true
                studentFare = false
                defenceFare = false
            }
            ConstantClass.students -> {
                srCitizen = false
                studentFare = true
                defenceFare = false
            }
            ConstantClass.defenceforces -> {
                srCitizen = false
                studentFare = false
                defenceFare = true
            }
            else -> {
                srCitizen = false
                studentFare = false
                defenceFare = false
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int) {
        searchDateForTravel=searchDateFormList(Currentwithnext5dateList[position])
        if(CheckOneWay){
            val bottomSheet = SelectTravellersClassBottomSheet()
            bottomSheet.show(childFragmentManager, SelectTravellersClassBottomSheet.TAG)
        }
        Log.d(logtag, searchDateForTravel)
    }


    fun withoutSelectionTripType(){
        binding.onewaySelection.setBackgroundResource(android.R.color.transparent)
        binding.roundTripSelection.setBackgroundResource(android.R.color.transparent)
        binding.onewaySelection.typeface = unselectedtypeface
        binding.roundTripSelection.typeface = unselectedtypeface
    }


    private fun moveTowardsEachOther(selected: View, other: View) {
        val distance = 10f  // adjust slightly

        selected.animate()
            .translationX(if (selected.id == binding.onewaySelection.id) distance else -distance)
            .setDuration(300)
            .withEndAction {
                selected.animate()
                    .translationX(0f)
                    .setDuration(300)
                    .start()
            }
            .start()

        other.animate()
            .translationX(if (selected.id == binding.onewaySelection.id) -distance else distance)
            .setDuration(300)
            .withEndAction {
                other.animate()
                    .translationX(0f)
                    .setDuration(300)
                    .start()
            }
            .start()
    }


    private fun OpenSpecialFareOfferLayout(){
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.special_fares_details_layout, null)
        bottomSheetDialog.setContentView(view)

        var cancel = view.findViewById<ImageView>(R.id.cancelIcon)


        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }


}