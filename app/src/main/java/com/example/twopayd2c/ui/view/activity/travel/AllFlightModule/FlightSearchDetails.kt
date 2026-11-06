package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.FilterAirLine
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.data.model.travelmodel.flight.TripInfo
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityFlightSearchDetailsBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.FlightDetailsAdapter
import com.example.twopayd2c.ui.adapter.ShimmerAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSearchDetails.Companion.SearchBusesList
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.FlightFilterIndividualBottomSheet
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.FlightSearchBottomSheet
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.AllFlightList
import com.example.twopayd2c.utils.ConstantClass.Companion.CheckNonStop
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightDepartureDateAndTime
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightListForFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightList
import com.example.twopayd2c.utils.ConstantClass.Companion.SearchFlightList
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.TripDetailsList
import com.example.twopayd2c.utils.ConstantClass.Companion.adultCount
import com.example.twopayd2c.utils.ConstantClass.Companion.after12pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6amlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.airportNameList
import com.example.twopayd2c.utils.ConstantClass.Companion.before6layout
import com.example.twopayd2c.utils.ConstantClass.Companion.bookingType
import com.example.twopayd2c.utils.ConstantClass.Companion.childCount
import com.example.twopayd2c.utils.ConstantClass.Companion.className
import com.example.twopayd2c.utils.ConstantClass.Companion.classNumber
import com.example.twopayd2c.utils.ConstantClass.Companion.datepassangerandclassstring
import com.example.twopayd2c.utils.ConstantClass.Companion.defenceFare
import com.example.twopayd2c.utils.ConstantClass.Companion.departureFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightDepartureList
import com.example.twopayd2c.utils.ConstantClass.Companion.flightOperatorFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightTypeFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightTypeList
import com.example.twopayd2c.utils.ConstantClass.Companion.fromAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.fromCountryName
import com.example.twopayd2c.utils.ConstantClass.Companion.generateRandomNumber
import com.example.twopayd2c.utils.ConstantClass.Companion.getDeviceIpAddress
import com.example.twopayd2c.utils.ConstantClass.Companion.infantCount
import com.example.twopayd2c.utils.ConstantClass.Companion.priceFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.priceList
import com.example.twopayd2c.utils.ConstantClass.Companion.srCitizen
import com.example.twopayd2c.utils.ConstantClass.Companion.studentFare
import com.example.twopayd2c.utils.ConstantClass.Companion.toAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.toCountryName
import com.example.twopayd2c.utils.ConstantClass.Companion.totalCount
import com.example.twopayd2c.utils.ConstantClass.Companion.travelDateFormat
import com.example.twopayd2c.utils.ConstantClass.Companion.travelType
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson

class FlightSearchDetails : AppCompatActivity() {
    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    lateinit var binding: ActivityFlightSearchDetailsBinding
    lateinit var adapter: FlightDetailsAdapter
    private val chunkSize = 30
    private var currentIndex = 0
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var  bottomfrag : FlightFilterIndividualBottomSheet
    var FlightList: MutableList<FlightsItem> = mutableListOf()

    companion object{
        lateinit var context: Context
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFlightSearchDetailsBinding.inflate(layoutInflater)
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

        preference = SharedPreference.getInstance(this)!!
        bottomfrag = FlightFilterIndividualBottomSheet()

        viewModel = ViewModelProvider(this, TravelViewModelFactory(
                TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI))
        )[TravelViewModel::class.java]

        // Register result callback
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val code = result.data?.getStringExtra("code")
                if(code.equals("100")||code.equals("200")){
                    var searchList = preference.getFlightFilterList(ConstantClass.FlightFilterData).toMutableList()
                    if(searchList.isNotEmpty()){
                        FlightList.clear()
                        FlightList = searchList
                        setDataOnFilterView("100")
                    }

                }
                else{

                }

                // Use the code
            }

        }

        binding.fromtocityname.text = FromCityName.plus(" to ").plus(ToCityName)
        binding.dateAndTime.text = ConstantClass.datepassangerandclassstring

        hitApiForSearchFlight()
        setOnClickListner()

    }


    override fun onResume() {
        super.onResume()

        setFilterState(binding.pricelayout, priceFilter)
        setFilterState(binding.flighttypelayout, flightTypeFilter)
        setFilterState(binding.departurelayout, departureFilter)
        setFilterState(binding.flightoperatorlayout, flightOperatorFilter)
    }

    fun setFilterState(view: View, isSelected: Boolean) {
        if (isSelected) {
            view.setBackgroundResource(R.drawable.selectedbutton)
        }
        else {
            view.setBackgroundResource(R.drawable.editouterborder)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun hitApiForSearchFlight() {
        shimmerLayout()
        if (!fromCountryName.equals("India", ignoreCase = true) || !toCountryName.equals(
                "India",
                ignoreCase = true
            )
        ) {
            travelType = 1
        } else {
            travelType = 0
        }

        var searchDateForTravel = preference?.getStringValue(ConstantClass.dateAndTime, "")
        var travelDate = travelDateFormat(searchDateForTravel)   //06/20/2025
        FlightDepartureDateAndTime = travelDate

        val requestId = generateRandomNumber()
        preference.setStringValue(ConstantClass.requestId, requestId)

        val androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        preference?.setStringValue(ConstantClass.imeINumber, androidId)

        val ipAdress = getDeviceIpAddress()
        preference?.setStringValue(ConstantClass.deviceIPAddress, ipAdress)

        val req = FlightSearchReq(
            requestId = preference.getStringValue(ConstantClass.requestId, ""),
            imeInumber = preference?.getStringValue(ConstantClass.imeINumber, ""),
            travelType = travelType,
            bookingType = bookingType,
            adultCount = adultCount.toString(),
            childCount = childCount.toString(),
            infantCount = infantCount.toString(),
            classOfTravel = classNumber,
            inventoryType = 0,
            sourceType = 0,
            srCitizenSearch = srCitizen,
            studentFareSearch = studentFare,
            defenceFareSearch = defenceFare,
            registartionId = preference.getStringValue(ConstantClass.MerchantId, ""),
            ipAddress = ipAdress,
            filterAirlineList = getFilterAirLineList(),
            tripeInfoList = listOf(
                TripInfo(
                    origin = fromAirportCode,
                    destination = toAirportCode,
                    traveldate = travelDate,
                    tripId = 0
                )
            )
        )

        Log.d("FlightSearchRequest", Gson().toJson(req))

        viewModel.getFlightSearchRequest(req).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    Log.d("FlightSearchResponse", Gson().toJson(response))

                    if (response?.responseHeader?.errorCode == "0000") {

                         priceFilter  = false
                         flightTypeFilter  = false
                         departureFilter  = false
                         flightOperatorFilter  = false

                        before6layout = false
                        after6amlayout = false
                        after12pmlayout = false
                        after6pmlayout = false

                        if (childCount == 0 && infantCount == 0) {
                            datepassangerandclassstring = searchDateForTravel.plus("|").plus(adultCount).plus("Adult").plus("|").plus(className)
                        }
                        else {
                            datepassangerandclassstring = searchDateForTravel.plus("|").plus(totalCount).plus("Travellers").plus("|").plus(className)
                        }

                        preference.setStringValue(ConstantClass.FlightSearchKey, response.searchKey)
                        Log.d("SearchKey", response.searchKey)
                        var getData = response.tripDetails
                        preference.setFlightSearchData(ConstantClass.FlightSearchData, getData, this)

                        TripDetailsList.clear()
                        AllFlightList.clear()
                        airportNameList.clear()
                        priceList.clear()

                        flightDepartureList.clear()
                        FlightList.clear()

                        getData?.let { it1 -> TripDetailsList.addAll(it1) }

                        airportNameList = TripDetailsList[0]?.flights
                            ?.mapNotNull { it.segments?.getOrNull(0)?.airlineName }
                            ?.distinct()
                            ?.map { it to false }
                            ?.toMutableList() ?: mutableListOf()

                        Log.d("AirlineName", Gson().toJson(airportNameList))

                        TripDetailsList[0]!!.flights?.let { AllFlightList.addAll(it) }
                        FlightList= AllFlightList

                        // Sort by lowest fare
                        AllFlightList.sortBy { flight ->
                            flight.fares?.getOrNull(0)?.fareDetails?.getOrNull(0)?.totalAmount?.toDoubleOrNull()?:Double.MAX_VALUE
                        }

                        FlightList = FlightList
                            .filter { !CheckNonStop || it.segments?.size == 1 }
                            .sortedBy {
                                it.fares?.getOrNull(0)
                                    ?.fareDetails?.getOrNull(0)
                                    ?.totalAmount
                                    ?.toDoubleOrNull() ?: Double.MAX_VALUE
                            }
                            .toMutableList()

                        preference.saveFlightFilterlist(ConstantClass.FlightAllList, AllFlightList)


                        flightTypeList.clear()
                        flightTypeList.add(Pair(ConstantClass.nonstop,CheckNonStop))
                        flightTypeList.add(Pair(ConstantClass.stops,false))


                        
                        priceList.add(Pair(ConstantClass.FlightLowToHigh,true))
                        priceList.add(Pair(ConstantClass.FlightHighToLow,false))

                        flightDepartureList.add("${ConstantClass.before6am},$FlightDepartureDateAndTime"  to false)
                        flightDepartureList.add("${ConstantClass.after6am},$FlightDepartureDateAndTime" to false)
                        flightDepartureList.add("${ConstantClass.after12pm},$FlightDepartureDateAndTime" to false)
                        flightDepartureList.add("${ConstantClass.after6pm},$FlightDepartureDateAndTime" to false)


                        setdataonView()

                    }
                    else {
                        Toast.makeText(this, response?.responseHeader?.errorDesc ?: "Error", Toast.LENGTH_SHORT).show()
                    }

                }

                ApiStatus.ERROR -> {
                    binding.animationView.setAnimation("notflightfound.json")
                    binding.animationView.repeatCount = LottieDrawable.INFINITE
                    binding.animationView.playAnimation()
                    binding.textmsg.text = resources.getString(R.string.notsearchflightmsg)
                    binding.flightlayout.visibility = View.GONE
                    Log.d("busListError", resource.message.toString())
                }

                ApiStatus.LOADING -> {
                    binding.loaderlayout.visibility = View.VISIBLE
                    binding.flightlayout.visibility = View.GONE
                }
            }
        }

    }

    fun shimmerLayout(){
        var adapter = ShimmerAdapter(this)
        binding.rvShimmer.adapter = adapter
    }


    fun getFilterAirLineList(): List<FilterAirLine> {
        var airlinecodeList: MutableList<FilterAirLine> = mutableListOf()
        airlinecodeList.add(FilterAirLine(airlineCode = ""))
        return airlinecodeList
    }


    fun setdataonView() {
        FlightListForFilter.clear()
        loadNextChunkCommon(false,"")
    }

    fun setDataOnFilterView(code:String){
        onResume()
        FlightListForFilter.clear()
        loadNextChunkCommon(true,code)
    }


    private fun loadNextChunkCommon(isFilter: Boolean,code:String) {
      
        if (FlightList.isNotEmpty()) {

            binding.loaderlayout.visibility = View.GONE

            if (isFilter) {
                binding.showFlightList.visibility = View.VISIBLE
            } else {
                binding.flightlayout.visibility = View.VISIBLE
            }

            binding.animationView.setAnimation("flightloader.json")
            binding.animationView.repeatCount = LottieDrawable.INFINITE
            binding.textmsg.text = resources.getString(R.string.searchflightmsg)
            binding.animationView.playAnimation()

            Log.d("AllFlightList", Gson().toJson(FlightList))

                FlightListForFilter.addAll(FlightList)
                adapter = FlightDetailsAdapter(this, FlightList)
                binding.showFlightList.adapter = adapter
                adapter.notifyDataSetChanged()
                Log.d("FlightList",Gson().toJson(FlightList))
        }
        else {
            binding.animationView.setAnimation("notflightfound.json")
            binding.animationView.repeatCount = LottieDrawable.INFINITE
            binding.animationView.playAnimation()
            binding.textmsg.text = resources.getString(R.string.notsearchflightmsg)

            if (isFilter) {
                binding.loaderlayout.visibility = View.VISIBLE
                binding.showFlightList.visibility = View.GONE
            } else {
                binding.flightlayout.visibility = View.GONE
                binding.loaderlayout.visibility = View.VISIBLE
            }
        }
    }


    fun setOnClickListner() {

        binding.backicon.setOnClickListener {
            finish()
        }

        binding.edit.setOnClickListener {
            val bottomfrag = FlightSearchBottomSheet()
            supportFragmentManager.let {
                bottomfrag.show(it, FlightSearchBottomSheet.TAG)
            }
        }

        binding.filterlayout.setOnClickListener {
            Log.d("FilterList", FlightListForFilter.size.toString().plus( ""))
            val intent = Intent(this, FlightFilterActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.flighttypelayout.setOnClickListener {
            FlightFilterIndividualBottomSheet.filterType = ConstantClass.FlightType
            supportFragmentManager.let {
                bottomfrag.show(it, FlightFilterIndividualBottomSheet.TAG)
            }
        }

        binding.pricelayout.setOnClickListener {
            FlightFilterIndividualBottomSheet.filterType = ConstantClass.FlightPrice
            supportFragmentManager.let {
                bottomfrag.show(it, FlightFilterIndividualBottomSheet.TAG)
            }
        }

        binding.departurelayout.setOnClickListener {
            FlightFilterIndividualBottomSheet.filterType = ConstantClass.FlightDeparture
            supportFragmentManager.let {
                bottomfrag.show(it, FlightFilterIndividualBottomSheet.TAG)
            }
        }

        binding.flightoperatorlayout.setOnClickListener {
            FlightFilterIndividualBottomSheet.filterType = ConstantClass.FlightAirlines
            supportFragmentManager.let {
                bottomfrag.show(it, FlightFilterIndividualBottomSheet.TAG)
            }
        }

        bottomfrag.setOnCloseListener {
            var searchList = preference.getFlightFilterList(ConstantClass.FlightFilterData).toMutableList()
            if(searchList.isNotEmpty()){

            Log.d("FilterFlightList", Gson().toJson(searchList))
                FlightList.clear()
                FlightList = searchList
                setDataOnFilterView("")
            }

            onResume()

        }

    }


}