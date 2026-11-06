package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.model.travelmodel.bus.city.CityDetails
import com.example.twopayd2c.R
import com.example.twopayd2c.adapter.PopularCityNameAdapter
import com.example.twopayd2c.adapter.RecentBusSearchAdapter
import com.example.twopayd2c.data.model.travelmodel.PopularCityNameListModel
import com.example.twopayd2c.data.model.travelmodel.RecentSearchListModel
import com.example.twopayd2c.data.model.travelmodel.flight.AirportListReq
import com.example.twopayd2c.data.model.travelmodel.flight.DataItem
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityFlightCitySearchPageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.BusCityNameAdapter
import com.example.twopayd2c.ui.adapter.FlightCityNameAdapter
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.FromBus
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityId
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.SearchFromToEditText
import com.example.twopayd2c.utils.ConstantClass.Companion.ToBus
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityId
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.fromAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.fromAirportName
import com.example.twopayd2c.utils.ConstantClass.Companion.fromCountryName
import com.example.twopayd2c.utils.ConstantClass.Companion.toAirportCode
import com.example.twopayd2c.utils.ConstantClass.Companion.toAirportName
import com.example.twopayd2c.utils.ConstantClass.Companion.toCountryName
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson

 class FlightCitySearchPage : AppCompatActivity() , FlightCityNameAdapter.OnItemClickListener,RecentBusSearchAdapter.onItemClickListner{
    lateinit var binding: ActivityFlightCitySearchPageBinding
    var popularCityNameList: MutableList<PopularCityNameListModel> = mutableListOf()
    var filteredList : MutableList<DataItem> = mutableListOf()
    private val fullList = mutableListOf<DataItem>()
    private val displayList = mutableListOf<DataItem>()

    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    lateinit var searchCityAdapter : FlightCityNameAdapter
    var recentSearchList: MutableList<RecentSearchListModel> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFlightCitySearchPageBinding.inflate(layoutInflater)
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
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI,ApiClient.apiBusAddRequestlAPI))
        )[TravelViewModel::class.java]

        if(SearchFromToEditText.equals(FromBus)){
            binding.fromcityname.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.fromcityname, InputMethodManager.SHOW_IMPLICIT)
        }
        else{
            binding.tocityname.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.tocityname, InputMethodManager.SHOW_IMPLICIT)
        }

        hitApiForGetAirportList("")
        setOnClickListner()
    }


    override fun onResume() {
        super.onResume()
        binding.fromcityname.setText(FromCityName)
        binding.tocityname.setText (ToCityName)
        setRecentData()

    }


    fun setRecentData(){
        recentSearchList = preference.getList(ConstantClass.RecentSavedFlightData).toMutableList()
        if(recentSearchList.isNotEmpty()){
            binding.recentSearchLayout.visibility=View.VISIBLE
            binding.recentsearchlayout.adapter = RecentBusSearchAdapter(recentSearchList,this)
        }else{
            binding.recentSearchLayout.visibility=View.GONE
        }

    }


    fun  setOnClickListner(){

        binding.backicon.setOnClickListener {
            finish()
        }

        binding.fromcityname.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                binding.fromcityname.setSelection( binding.fromcityname.text.length)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()

                if (query.isEmpty()) {
                    setSearchCityNameInView(fullList)
                }
                else {
                    filteredList = fullList.filter {
                        it.city!!.contains(query, ignoreCase = true)
                    }.toMutableList()

                    binding.recentSearchLayout.visibility = View.GONE
                    Log.d("filteredList", Gson().toJson(filteredList))
                    filter(filteredList)
                }
            }

        })

        binding.tocityname.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                binding.tocityname.setSelection( binding.tocityname.text.length)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isEmpty()) {
                    setSearchCityNameInView(fullList)
                }
                else {
                    filteredList = fullList.filter {
                        it.city!!.contains(query, ignoreCase = true)
                    }.toMutableList()

                    binding.recentSearchLayout.visibility = View.GONE
                    Log.d("filteredList", Gson().toJson(filteredList))
                    filter(filteredList)
                }


            }

        })



    }


    private fun hitApiForGetAirportList(airportFilterString: String) {
        val airportListReq = AirportListReq(searchTerm = airportFilterString)

        Log.d("AirportListRequest", Gson().toJson(airportListReq))
        viewModel.getAirportListRequet(airportListReq).observe(this) { resource ->

            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                        ConstantClass.dialog.dismiss()
                    }
                    val response = resource.data?.body()
                    Log.d("AirportListResponse", Gson().toJson(response))
                    response?.data?.let { list ->
                        fullList?.addAll(list)
                        setSearchCityNameInView(fullList)
                    }

                }

                ApiStatus.ERROR -> if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }

                ApiStatus.LOADING -> ConstantClass.loading(this)

            }
        }
    }


    fun filter(item: MutableList<DataItem>) {
        displayList.clear()
        displayList.addAll(item)

        searchCityAdapter = FlightCityNameAdapter(this,displayList,this)
        binding.searchCityList.adapter = searchCityAdapter
        searchCityAdapter.notifyDataSetChanged()
        Log.d("FlightList","${displayList.size}")


    }


    fun setSearchCityNameInView(SearchCityNameList:List<DataItem>){
        displayList.clear()
        displayList.addAll(SearchCityNameList.take(10)) // show only top 10

        searchCityAdapter = FlightCityNameAdapter(this,displayList,this)
        binding.searchCityList.adapter = searchCityAdapter
        searchCityAdapter.notifyDataSetChanged()
    }


    override fun onClickSearchCityItemClick(position: Int) {
        var cityName =  displayList[position].city
        var airportCode =  displayList[position].airportCode
        var countryName =  displayList[position].country
        var airportName =  displayList[position].airportDescription

        if(SearchFromToEditText.equals(FromBus)){
            binding.fromcityname.setText(cityName)
            FromCityName= cityName!!
            fromAirportCode = airportCode.toString()
            fromCountryName = countryName
            fromAirportName= airportName
            if(binding.fromcityname.text.toString().isNotEmpty() ){
                finish()
            }
        }
        else{
            binding.tocityname.setText(cityName)
            ToCityName= cityName!!
            toAirportCode = airportCode.toString()
            toCountryName = countryName
            toAirportName = airportName
            if(binding.tocityname.text.toString().isNotEmpty()){
                finish()
            }
        }



    }


     override fun recentItemClick(recentsearchdata: RecentSearchListModel) {
         binding.fromcityname.setText(recentsearchdata.fromcity)
         FromCityName= recentsearchdata.fromcity!!
         fromAirportCode = recentsearchdata.fromcityId.toString()
         fromCountryName = recentsearchdata.fromCountryName!!
         toAirportCode = recentsearchdata.tocityId.toString()
         binding.tocityname.setText( recentsearchdata.tocity)
         ToCityName =  recentsearchdata.tocity!!
         toCountryName = recentsearchdata.toCountryName!!
         if(binding.fromcityname.text.toString().isNotEmpty() && binding.tocityname.text.toString().isNotEmpty()){
             finish()
         }
     }


 }