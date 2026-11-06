package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListReq
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListRes
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.AirportListReq
import com.example.twopayd2c.data.model.travelmodel.flight.DataItem
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.BusSearchListPopupBinding
import com.example.twopayd2c.databinding.FlightSearchListPopupBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSearchDetails
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightSearchDetails
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.SearchFromToEditText
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.adultCount
import com.example.twopayd2c.utils.ConstantClass.Companion.checkFromBus
import com.example.twopayd2c.utils.ConstantClass.Companion.childCount
import com.example.twopayd2c.utils.ConstantClass.Companion.className
import com.example.twopayd2c.utils.ConstantClass.Companion.convertFlightEditDateFormat
import com.example.twopayd2c.utils.ConstantClass.Companion.datepassangerandclassstring
import com.example.twopayd2c.utils.ConstantClass.Companion.fromAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.fromAirportName
import com.example.twopayd2c.utils.ConstantClass.Companion.fromCountryName
import com.example.twopayd2c.utils.ConstantClass.Companion.generateRandomNumber
import com.example.twopayd2c.utils.ConstantClass.Companion.infantCount
import com.example.twopayd2c.utils.ConstantClass.Companion.toAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.toAirportName
import com.example.twopayd2c.utils.ConstantClass.Companion.toCountryName
import com.example.twopayd2c.utils.ConstantClass.Companion.totalCount
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FlightSearchBottomSheet:BottomSheetDialogFragment() {
    private lateinit var bin : FlightSearchListPopupBinding
    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    private var checkReverse = false
    private val myCalender = Calendar.getInstance()
    private var fromDesignation: String? = null
    private var fromDesignationName: String? = null
    private var toDesignation: String? = null
    private var toDesignationName: String? = null
   // private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
   private var AirportDataList: MutableList<DataItem> = mutableListOf()


    private val bookingDate = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

        myCalender.set(Calendar.YEAR, year)
        myCalender.set(Calendar.MONTH, monthOfYear)
        myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        // For API / storage
        val apiSdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val selectedDate = apiSdf.format(myCalender.time)

        // For UI display
        val displaySdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = displaySdf.format(myCalender.time)

        bin.datemonthdeparture.text = formatDate1(formattedDate)
        bin.dayyear.text = formatDate2(formattedDate)

        preference?.setStringValue(ConstantClass.dateAndTime, selectedDate)

        Log.d("SelectedDate", selectedDate)
    }



    companion object {
        const val TAG = "FlightSearchSheet"
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bin = FlightSearchListPopupBinding.inflate(inflater, container, false)
        preference = SharedPreference(requireContext())

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI,ApiClient.apiBusAddRequestlAPI))
        )[TravelViewModel::class.java]

        setData()
        setonclicklistner()
        setCurrentDate()
        hitApiForGetAirportList("")
        setSpinnerData()
        init()
        return bin.root
    }


    public fun setData() {

        bin.fromlayout.visibility = View.VISIBLE
        bin.fromDestinationSp.visibility = View.GONE

        bin.toDestinationSp.visibility = View.GONE
        bin.tolayout.visibility = View.VISIBLE


        if (checkFromBus) {
            bin.fromcityname.text = FromCityName
            bin.fromcityLayout.background = resources.getDrawable(R.drawable.fromtoback)
        }
        else {
            bin.tocityname.text = ToCityName
            bin.tocityLayout.background = resources.getDrawable(R.drawable.fromtoback)
        }

    }




    private fun hitApiForGetAirportList(airportFilterString: String) {
        val airportListReq = AirportListReq(searchTerm = airportFilterString)

        Log.d("AirportListRequest", Gson().toJson(airportListReq))
        viewModel.getAirportListRequet(airportListReq).observe(viewLifecycleOwner) { resource ->

            when (resource.apiStatus) {

                ApiStatus.SUCCESS -> {

                    val response = resource.data?.body()
                    Log.d("AirportListResponse", Gson().toJson(response))


                    response?.data?.let { list ->
                        AirportDataList.clear()
                        ConstantClass.FlightList!!.clear()
                        AirportDataList.addAll(list)
                        ConstantClass.FlightList!!.addAll(AirportDataList)
                        setSpinnerData()
                    }
                }

                ApiStatus.ERROR -> {}

                ApiStatus.LOADING -> {}

            }
        }
    }


    private fun setSpinnerData() {
        ConstantClass.FlightHint!!.clear()
        ConstantClass.FlightHint!!.add(DataItem("", "Select City", "", ""))

        ConstantClass.getAllFlightListAdapter = ArrayAdapter(requireContext(), R.layout.spinner_right_aligned, ConstantClass.FlightHint!!)
        ConstantClass.getAllFlightListAdapter!!.setDropDownViewResource(R.layout.spinner_right_aligned)

        bin.fromDestinationSp.adapter = ConstantClass.getAllFlightListAdapter
        bin.toDestinationSp.adapter = ConstantClass.getAllFlightListAdapter
    }


    private fun setCurrentDate() {
        val calendar = Calendar.getInstance()

        // For UI display
        val displaySdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = displaySdf.format(myCalender.time)

        bin.datemonthdeparture.text = formatDate1(formattedDate)
        bin.dayyear.text = formatDate2(formattedDate)

        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val selectedDate = sdf.format(calendar.time)
        preference?.setStringValue(ConstantClass.dateAndTime, selectedDate)


    }


    fun init(){
        setSelectionTodayTomorrow()
        bin.todaylayout.background = resources.getDrawable(R.drawable.selectedbutton)
        bin.todaytxt.setTextColor(resources.getColor(R.color.primary_blue))
        bin.todaylayout.backgroundTintList=null

        FromCityName =  preference?.getStringValue(ConstantClass.fromDesignationName, "")!!
        ToCityName =    preference?.getStringValue(ConstantClass.toDesignationName, "")!!
        bin.fromcityname.text = FromCityName
        bin.tocityname.text   = ToCityName

        fromDesignation = ConstantClass.toLocationNameMap?.get(FromCityName).toString()
        preference?.setStringValue(ConstantClass.fromDesignationId, fromDesignation)
        preference?.setStringValue(ConstantClass.fromDesignationName, FromCityName)

        toDesignation = ConstantClass.toLocationNameMap?.get(ToCityName).toString()
        preference?.setStringValue(ConstantClass.toDesignationId, toDesignation)
        preference?.setStringValue(ConstantClass.toDesignationName, ToCityName)

    }


    private fun setonclicklistner(){
        bin.cross.setOnClickListener {
            dialog!!.dismiss()
        }

        bin.todaylayout.setOnClickListener {
           val calendar = Calendar.getInstance()

           val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
           val selectedDate = sdf.format(calendar.time)
           preference?.setStringValue(ConstantClass.dateAndTime, selectedDate)
           setSelectionTodayTomorrow()
           bin.todaylayout.background = resources.getDrawable(R.drawable.selectedbutton)
           bin.todaytxt.setTextColor(resources.getColor(R.color.primary_blue))
           bin.todaylayout.backgroundTintList=null
       }


        bin.tomorrowlayout.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, 1) // ➜ tomorrow

            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val tomorrowDate = sdf.format(calendar.time)

            preference?.setStringValue(ConstantClass.dateAndTime, tomorrowDate)

            setSelectionTodayTomorrow()

            bin.tomorrowlayout.background = resources.getDrawable(R.drawable.selectedbutton)
            bin.tomorrowtxt.setTextColor(resources.getColor(R.color.primary_blue))
            bin.tomorrowlayout.backgroundTintList=null

        }


        bin.fromcityLayout.setOnClickListener {
            checkFromBus = true
            bin.fromlayout.visibility = View.GONE
            bin.fromDestinationSp.visibility = View.VISIBLE
            bin.fromcityLayout.background = resources.getDrawable(R.drawable.selectedbutton)
            bin.fromDestinationSp.openDialog()
        }


        bin.tocityLayout.setOnClickListener {
            checkFromBus = false
            bin.tolayout.visibility = View.GONE
            bin.toDestinationSp.visibility = View.VISIBLE
            bin.tocityLayout.background = resources.getDrawable(R.drawable.selectedbutton)
            bin.toDestinationSp.openDialog()
        }


        bin.reversetrip.setOnClickListener {

           /* FromCityName = bin.fromcityname.text.toString()
            ToCityName = bin.tocityname.text.toString()

            if (checkReverse) {
                bin.fromcityname.text = ToCityName
                bin.tocityname.text = FromCityName
                checkReverse = false
            }
            else {
                bin.tocityname.text = FromCityName
                bin.fromcityname.text = ToCityName
                checkReverse = true
            }*/

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
            bin.fromcityname.text = FromCityName
            bin.tocityname.text = ToCityName

        }

        bin.departureDatelayout.setOnClickListener {
            ConstantClass.hideKeyboard(requireActivity())
            DatePickerDialog(
                requireActivity(),
                bookingDate,
                myCalender[Calendar.YEAR],
                myCalender[Calendar.MONTH],
                myCalender[Calendar.DAY_OF_MONTH]
            ).show()
        }


        bin.searchFlightlayout.setOnClickListener {
            validationCity()
        }


    }


    private fun validationCity() {
        val errorMessage: String? = when{
            FromCityName.isNullOrBlank() -> "Please select from location"
            ToCityName.isNullOrBlank() -> "Please select to location"
            else -> null
        }
        if (errorMessage != null){
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
        else {
            FromCityName = bin.fromcityname.text.toString()
            ToCityName = bin.tocityname.text.toString()

            var searchDateForTravel = convertFlightEditDateFormat(preference?.getStringValue(ConstantClass.dateAndTime, "")!!)

            if (childCount == 0 && infantCount == 0) {

                datepassangerandclassstring = searchDateForTravel.toString().plus("|").plus(adultCount).plus("Adult").plus("|").plus(className)
            }
            else {
                datepassangerandclassstring = searchDateForTravel.toString().plus("|").plus(totalCount).plus("Travellers").plus("|").plus(className)
            }

            preference?.setStringValue(ConstantClass.fromDesignationId, fromAirportCode)
            preference?.setStringValue(ConstantClass.fromDesignationName, bin.fromcityname.text.toString())
            preference?.setStringValue(ConstantClass.toDesignationId, toAirportCode)
            preference?.setStringValue(ConstantClass.toDesignationName, bin.tocityname.text.toString())
            preference?.setStringValue(ConstantClass.dateAndTime, preference?.getStringValue(ConstantClass.dateAndTime, ""))
            

            (requireActivity() as? FlightSearchDetails)?.let {
                it.finish()
                it.startActivity(it.intent)
            }
            dismiss()

        }
    }


    fun setSelectionTodayTomorrow(){
        bin.tomorrowlayout.background = resources.getDrawable(R.drawable.fromtoback)
        bin.tomorrowlayout.backgroundTintList =   ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
        bin.tomorrowtxt.setTextColor(resources.getColor(R.color.edtsubblack))

        bin.todaylayout.background = resources.getDrawable(R.drawable.fromtoback)
        bin.todaylayout.backgroundTintList =   ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
        bin.todaytxt.setTextColor(resources.getColor(R.color.edtsubblack))

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // used to show the bottom sheet dialog
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                    sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED

                val layoutParams = sheet.layoutParams
                val windowHeight = Resources.getSystem().displayMetrics.heightPixels
                layoutParams.height = windowHeight
                sheet.layoutParams = layoutParams
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

    }


    private fun formatDate1(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM ", Locale.getDefault())
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }


    private fun formatDate2(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEE, yyyy", Locale.getDefault())
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }


   /* fun hitApiForCheckActiveStatus() {
        var statusreq = CheckActiveStatusReq(
            userID =  preference!!.getStringValue(ConstantClass.RegistrationId, "").toString()
        )

        Log.d("LoginReq", Gson().toJson(statusreq))

        getAllApiServiceViewModel.GetRetailerActiveReq(statusreq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("LoginResponse", Gson().toJson(response))
                                var activeStatus = response.data!!.isActive
                                ConstantClass.checkActiveStatusAndLogout(requireContext(), activeStatus, preference!!)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {

                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }*/



}