package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListReq
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListRes
import com.example.twopayd2c.data.model.travelmodel.bus.city.CityDetails
import com.example.twopayd2c.R
import com.example.twopayd2c.adapter.PopularCityNameAdapter
import com.example.twopayd2c.data.model.travelmodel.PopularCityNameListModel
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityBusCitySearchPageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.BusCityNameAdapter
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
import com.example.twopayd2c.utils.ConstantClass.Companion.getDeviceIpAddress
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson


class BusCitySearchPage : AppCompatActivity() , PopularCityNameAdapter.OnItemClickListener, BusCityNameAdapter.OnItemClickListener{
    lateinit var binding: ActivityBusCitySearchPageBinding
    var popularCityNameList: MutableList<PopularCityNameListModel> = mutableListOf()
    var CityNameList : MutableList<CityDetails> = mutableListOf()
    var filteredList : MutableList<CityDetails> = mutableListOf()
    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    lateinit var searchCityAdapter : BusCityNameAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBusCitySearchPageBinding.inflate(layoutInflater)
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



        setPopularCities()
        getAllTravelBusList()
        setOnClickListner()
    }


    override fun onResume() {
        super.onResume()
        binding.fromcityname.setText(FromCityName)
        binding.tocityname.setText (ToCityName)
    }


    fun  setOnClickListner(){
        Log.d("cityNameList", Gson().toJson(CityNameList))

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

                if (query.isNotEmpty()) {
                    filteredList = CityNameList.filter {
                        it.cityName!!.contains(query, ignoreCase = true)
                    }.toMutableList()

                    binding.searchCityList.visibility = View.VISIBLE
                    Log.d("filteredList", Gson().toJson(filteredList))
                    setSearchCityNameInView(filteredList)
                } else {
                    binding.searchCityList.visibility = View.GONE
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

                if (query.isNotEmpty()) {
                    filteredList = CityNameList.filter {
                        it.cityName!!.contains(query, ignoreCase = true)
                    }.toMutableList()

                    binding.searchCityList.visibility = View.VISIBLE
                    Log.d("filteredList", Gson().toJson(filteredList))

                    setSearchCityNameInView(filteredList)
                } else {
                    binding.searchCityList.visibility = View.GONE
                }
            }
        })


    }


    private fun getAllTravelBusList() {
        val requestId = ConstantClass.generateRandomNumber()
        preference?.setStringValue(ConstantClass.requestId, requestId)
        val androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        preference?.setStringValue(ConstantClass.imeINumber, androidId)
        val ipAdress= getDeviceIpAddress()
        preference?.setStringValue(ConstantClass.deviceIPAddress, ipAdress)

        val busCityListReq = BusCityListReq(
            iPAddress = getDeviceIpAddress(),
            requestId =  requestId,
            imeINumber = androidId,
            registrationID = preference!!.getStringValue(ConstantClass.MerchantId, "")
        )
        Log.d("busCityListReq", Gson().toJson(busCityListReq))

        viewModel.getAllBusCityList(busCityListReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("getAllTravelBusListRes", Gson().toJson(response))
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
            CityNameList.clear()
            response.cityDetails.forEach { busListData ->

                busListData?.let {
                    CityNameList.add(it)
                }

            }

        }
        else {
            Toast.makeText(this, response?.responseHeader?.errorDesc.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun setSearchCityNameInView(SearchCityNameList:List<CityDetails>){
        searchCityAdapter = BusCityNameAdapter(this,SearchCityNameList,this)
        binding.searchCityList.adapter = searchCityAdapter
        searchCityAdapter.notifyDataSetChanged()
    }


    fun setPopularCities(){
        popularCityNameList.clear()
        popularCityNameList.add(PopularCityNameListModel("Bangalore",R.drawable.banglore,7))
        popularCityNameList.add(PopularCityNameListModel("Chennai",R.drawable.chennai,6))
        popularCityNameList.add(PopularCityNameListModel("Hyderabad",R.drawable.hedrabaadicon,3))
        popularCityNameList.add(PopularCityNameListModel("Coimbatore",R.drawable.coimbatore,794))
        popularCityNameList.add(PopularCityNameListModel("Mumbai",R.drawable.mumbai,4))
        popularCityNameList.add(PopularCityNameListModel("Pune",R.drawable.pune,51))
        popularCityNameList.add(PopularCityNameListModel("Delhi",R.drawable.delhi,344))
        popularCityNameList.add(PopularCityNameListModel("Goa",R.drawable.goa,102))
        popularCityNameList.add(PopularCityNameListModel("Jaipur",R.drawable.jaipur,241))
        popularCityNameList.add(PopularCityNameListModel("Madurai",R.drawable.madurai,1016))
        popularCityNameList.add(PopularCityNameListModel("Nagpur",R.drawable.nagpur,152))
        popularCityNameList.add(PopularCityNameListModel("Indore",R.drawable.indore,100))

        var adapter = PopularCityNameAdapter(this@BusCitySearchPage, popularCityNameList,this)
        binding.popularcities.adapter = adapter
        adapter.notifyDataSetChanged()

    }


    override fun onItemClick(position: Int) {
        var cityName =   popularCityNameList[position].tocity
        var cityId =  popularCityNameList[position].cityID

       if(SearchFromToEditText.equals(FromBus)){
           binding.fromcityname.setText(cityName)
           FromCityName= cityName!!
           FromCityId= cityId.toString()
           SearchFromToEditText= ToBus
           binding.tocityname.requestFocus()
       }else{
           binding.tocityname.setText(cityName)
           ToCityName= cityName!!
           ToCityId= cityId.toString()
           SearchFromToEditText= FromBus
           binding.fromcityname.requestFocus()
       }

       if(binding.fromcityname.text.toString().isNotEmpty() && binding.tocityname.text.toString().isNotEmpty()){
           finish()
       }

    }


    override fun onClickSearchCityItemClick(position: Int) {
        var cityName =   filteredList[position].cityName
        var cityId =  filteredList[position].cityID
        if(SearchFromToEditText.equals(FromBus)){
            binding.fromcityname.setText(cityName)
            FromCityName= cityName!!
            FromCityId= cityId.toString()
            SearchFromToEditText= ToBus
            binding.tocityname.requestFocus()
        }
        else{
            binding.tocityname.setText(cityName)
            ToCityName= cityName!!
            ToCityId= cityId.toString()
            SearchFromToEditText= FromBus
            binding.fromcityname.requestFocus()
        }

        if(binding.fromcityname.text.toString().isNotEmpty() && binding.tocityname.text.toString().isNotEmpty()){
            finish()
        }
    }


}