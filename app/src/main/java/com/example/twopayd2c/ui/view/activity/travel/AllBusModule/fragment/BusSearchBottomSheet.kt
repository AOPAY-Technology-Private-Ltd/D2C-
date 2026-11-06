package com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment

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
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.BusSearchListPopupBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSearchDetails
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.checkFromBus
import com.example.twopayd2c.utils.ConstantClass.Companion.generateRandomNumber
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BusSearchBottomSheet:BottomSheetDialogFragment() {
    private lateinit var bin : BusSearchListPopupBinding
    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    private var checkReverse = false
    private val myCalender = Calendar.getInstance()
    private var fromDesignation: String? = null
    private var fromDesignationName: String? = null
    private var toDesignation: String? = null
    private var toDesignationName: String? = null
   // private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel


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
        const val TAG = "BusSearchSheet"
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bin = BusSearchListPopupBinding.inflate(inflater, container, false)
        preference = SharedPreference(requireContext())

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI,ApiClient.apiBusAddRequestlAPI))
        )[TravelViewModel::class.java]

      /*  getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(
            GetAllAPIServiceRepository(RetrofitClient.apiAllInterface)
        )
        )[GetAllApiServiceViewModel::class.java]*/


        setonclicklistner()
        setCurrentDate()
        getAllTravelBusList()
        setDropDown()
        init()
        return bin.root
    }


    override fun onResume() {
        super.onResume()

       // hitApiForCheckActiveStatus()
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


    private fun getAllTravelBusList() {
        val requestId = generateRandomNumber()
        preference?.setStringValue(ConstantClass.requestId, requestId)
        val busCityListReq = BusCityListReq(
            iPAddress = preference!!.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId =  preference?.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber, ""),
            registrationID = preference!!.getStringValue(ConstantClass.MerchantId, "")
        )
        Log.d("busCityListReq", Gson().toJson(busCityListReq))
        viewModel.getAllBusCityList(busCityListReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                getAllTravelBusListRes(response)
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
    }


    private fun getAllTravelBusListRes(response: BusCityListRes?) {
        if (response?.responseHeader?.errorCode == "0000"){
            ConstantClass.busListName?.clear()
            ConstantClass.toLocationName?.clear()
            ConstantClass.busListName?.add("Select from location")
            ConstantClass.toLocationName?.add("Select to location")
            response.cityDetails.forEach { busListData ->
                ConstantClass.busListName?.add(busListData.cityName!!)
                ConstantClass.toLocationName?.add(busListData.cityName!!)
                ConstantClass.busListNameMap?.put(busListData.cityName!!, busListData.cityID!!)
                ConstantClass.toLocationNameMap?.put(busListData.cityName!!, busListData.cityID!!)
            }
            ConstantClass.getAllBusListAdapter?.notifyDataSetChanged()
        }
        else {
            Toast.makeText(requireContext(), response?.responseHeader?.errorDesc.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun setDropDown() {

        ConstantClass.getAllBusListAdapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_right_aligned, ConstantClass.busListName!!)
        ConstantClass.getAllBusListAdapter!!.setDropDownViewResource(R.layout.spinner_right_aligned)
        bin.fromDestinationSp.adapter = ConstantClass.getAllBusListAdapter

        bin.fromDestinationSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (pos > 0) {
                    try {
                        fromDesignation = ConstantClass.busListNameMap?.get(parent?.getItemAtPosition(pos)).toString()
                        fromDesignationName = parent?.getItemAtPosition(pos).toString()
                        preference?.setStringValue(ConstantClass.fromDesignationName, fromDesignationName)
                        preference?.setStringValue(ConstantClass.fromDesignationId, fromDesignation)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    fromDesignation = ""
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        ConstantClass.getAllBusListAdapter!!.notifyDataSetChanged()


        ConstantClass.getAllBusListAdapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_right_aligned, ConstantClass.toLocationName!!)

        ConstantClass.getAllBusListAdapter!!.setDropDownViewResource(R.layout.spinner_right_aligned)

        bin.toDestinationSp.adapter = ConstantClass.getAllBusListAdapter

        ConstantClass.getAllBusListAdapter!!.notifyDataSetChanged()

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
            bin.fromDestinationSp.showDialog()
        }


        bin.tocityLayout.setOnClickListener {
            checkFromBus = false
            bin.tolayout.visibility = View.GONE
            bin.toDestinationSp.visibility = View.VISIBLE
            bin.tocityLayout.background = resources.getDrawable(R.drawable.selectedbutton)
            bin.toDestinationSp.showDialog()
        }


        bin.fromDestinationSp.setBusCitySelectListener { item, _ ->
            FromCityName = item as String // your bus model
            setData()
        }

        bin.toDestinationSp.setBusCitySelectListener { item, _ ->
            ToCityName = item as String // your bus model
            setData()


        }

        bin.reversetrip.setOnClickListener {

            FromCityName = bin.fromcityname.text.toString()

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
            }


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

        bin.searchbuseslayout.setOnClickListener {
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
            fromDesignation = ConstantClass.toLocationNameMap?.get(bin.fromcityname.text.toString()).toString()
            toDesignation = ConstantClass.toLocationNameMap?.get(bin.tocityname.text.toString()).toString()

            preference?.setStringValue(ConstantClass.fromDesignationId, fromDesignation)
            preference?.setStringValue(ConstantClass.fromDesignationName, bin.fromcityname.text.toString())
            preference?.setStringValue(ConstantClass.toDesignationId, toDesignation)
            preference?.setStringValue(ConstantClass.toDesignationName, bin.tocityname.text.toString())
            preference?.setStringValue(ConstantClass.dateAndTime, preference?.getStringValue(ConstantClass.dateAndTime, ""))
            

            (requireActivity() as? BusSearchDetails)?.let {
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