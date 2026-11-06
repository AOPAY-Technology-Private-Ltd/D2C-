package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.airbnb.lottie.LottieDrawable
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BusSearchReq
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BusSearchRes
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.Buses
import com.example.twopayd2c.R
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityBusSearchDetailsBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.BusSearchAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.BusFilterIndividualBottomSheet
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.BusFilterIndividualBottomSheet.Companion.filterType
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.BusSearchBottomSheet
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.AllBusList
import com.example.twopayd2c.utils.ConstantClass.Companion.BusDepartureDateAndTime
import com.example.twopayd2c.utils.ConstantClass.Companion.BusListForFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.after12pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6amlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.before6layout
import com.example.twopayd2c.utils.ConstantClass.Companion.busBoardingPointList
import com.example.twopayd2c.utils.ConstantClass.Companion.busDepartureList
import com.example.twopayd2c.utils.ConstantClass.Companion.busDropingPointList
import com.example.twopayd2c.utils.ConstantClass.Companion.busOperatorNameList
import com.example.twopayd2c.utils.ConstantClass.Companion.busTypeList
import com.example.twopayd2c.utils.ConstantClass.Companion.travelDateFormat
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson

class BusSearchDetails : AppCompatActivity() {
    lateinit var binding: ActivityBusSearchDetailsBinding
    lateinit var preference: SharedPreference
    private lateinit var viewModel: TravelViewModel
    private lateinit var busDataList: MutableList<Buses>
    private lateinit var busSearchAdapter: BusSearchAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var  bottomfrag : BusFilterIndividualBottomSheet

    companion object{
        var SearchBusesList: MutableList<Buses> = mutableListOf()
        lateinit var context: Context
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBusSearchDetailsBinding.inflate(layoutInflater)
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

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val code = result.data?.getStringExtra("code")
                if (code.equals("101") || code.equals("200")) {
                    setdataonView()
                } else {

                }
                // Use the code
            }
        }
        bottomfrag = BusFilterIndividualBottomSheet()
        intiView()
        setDataForToolbar()
        setOnClickListner()
        getAllBusSearchList()

    }


    fun setdataonView(){
        busDataList.clear()
        busDataList.addAll(SearchBusesList)
        if(busDataList.isNotEmpty()){
            binding.loaderlayout.visibility = View.GONE
            binding.buslayout.visibility = View.VISIBLE
            binding.showBusList.visibility = View.VISIBLE
        }
        else {
            binding.loaderlayout.visibility = View.VISIBLE
            binding.animationView.setAnimation("notfoundbus.json")
            binding.animationView.repeatCount = LottieDrawable.INFINITE
            binding.animationView.playAnimation()
            binding.textmsg.text= resources.getString(R.string.notsearchbusmsg)
            binding.showBusList.visibility = View.GONE
        }
        busSearchAdapter.notifyDataSetChanged()

    }


    fun setDataForToolbar(){
        binding.fromtocityname.text = "${preference?.getStringValue(ConstantClass.fromDesignationName, "")} to ${preference?.getStringValue(ConstantClass.toDesignationName, "")}"
        binding.dateAndTime.text =  preference?.getStringValue(ConstantClass.dateAndTime, "")
    }


    fun setOnClickListner(){

        binding.backicon.setOnClickListener {
            finish()
        }


        binding.filterlayout.setOnClickListener {
            val intent = Intent(this, BusFilterActivity::class.java)
            resultLauncher.launch(intent)
        }


       binding.bustypelayout.setOnClickListener {
           filterType = ConstantClass.BusType
           supportFragmentManager.let {
               bottomfrag.show(it, BusFilterIndividualBottomSheet.TAG)
           }
       }

        binding.departurelayout.setOnClickListener {
            filterType = ConstantClass.BusDeparture
            supportFragmentManager.let {
                bottomfrag.show(it, BusFilterIndividualBottomSheet.TAG)
            }
        }

        binding.busoperatorlayout.setOnClickListener {
            filterType = ConstantClass.BusOperator
            supportFragmentManager.let {
                bottomfrag.show(it, BusFilterIndividualBottomSheet.TAG)
            }
        }

        bottomfrag.setOnCloseListener {
            setdataonView()
        }

        binding.edit.setOnClickListener {
            val bottomfrag = BusSearchBottomSheet()
            supportFragmentManager.let {
                bottomfrag.show(it, BusSearchBottomSheet.TAG)
            }
        }

    }


    private fun intiView() {
        preference = SharedPreference(this)
        busDataList = ArrayList()

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI,ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        busSearchAdapter = BusSearchAdapter(this, busDataList)
        binding.showBusList.adapter = busSearchAdapter

    }


    @RequiresApi(Build.VERSION_CODES.O)
    public fun getAllBusSearchList() {
        var travelDate = travelDateFormat(preference?.getStringValue(ConstantClass.dateAndTime, ""))   //06/20/2025
        BusDepartureDateAndTime = travelDate

        val busSearchReq = BusSearchReq(
            fromCity = preference?.getStringValue(ConstantClass.fromDesignationId,""),
            toCity = preference?.getStringValue(ConstantClass.toDesignationId, ""),
            travelDate = travelDate,
            iPAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference?.getStringValue(ConstantClass.requestId,""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber,""),
            registrationID = preference?.getStringValue(ConstantClass.MerchantId, "")
        )

        Log.d("busSearchReq", Gson().toJson(busSearchReq))

        viewModel.getAllBusSearchList(busSearchReq).observe(this) { resource ->
            resource?.let {
                when(it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("BusSearchData", Gson().toJson(response))
                                getAllBusSearchListRes(response)
                            }
                        }
                    }
                    ApiStatus.ERROR -> {
                        binding.animationView.setAnimation("notfoundbus.json")
                        binding.animationView.repeatCount = LottieDrawable.INFINITE
                        binding.animationView.playAnimation()
                        binding.textmsg.text= resources.getString(R.string.notsearchbusmsg)
                        binding.buslayout.visibility=View.GONE
                        Log.d("busListError",it.message.toString())
                    }
                    ApiStatus.LOADING -> {
                        binding.loaderlayout.visibility=View.VISIBLE
                        binding.buslayout.visibility=View.GONE
                    }
                }
            }
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getAllBusSearchListRes(response: BusSearchRes) {
        busDataList.clear()
        AllBusList.clear()
        BusListForFilter.clear()
        busOperatorNameList.clear()
        busBoardingPointList.clear()
        busDropingPointList.clear()


        if (response.responseHeader?.errorCode == "0000"){
            binding.loaderlayout.visibility=View.GONE
            binding.buslayout.visibility=View.VISIBLE
            Log.d("BussearchList",response.buses[0]?.availableSeats.toString())
            Log.d("BusList", Gson().toJson(response.buses))
            Log.d("BusList", Gson().toJson(response))

            preference?.setStringValue(ConstantClass.searchKey, response.searchKey.toString())
            Log.d("responseHeader", preference?.getStringValue(ConstantClass.searchKey, "").toString())

            response.buses.forEach { busList ->
                busDataList.add(busList)
                AllBusList.add(busList)
                BusListForFilter.add(busList)
            }

            busOperatorNameList.clear()
            busBoardingPointList.clear()
            busDropingPointList.clear()
            busTypeList.clear()
            busDepartureList.clear()

            busTypeList.add(ConstantClass.ac to false)
            busTypeList.add(ConstantClass.nonac to false)

            busDepartureList.add("${ConstantClass.before6am},${BusDepartureDateAndTime}"  to false)
            busDepartureList.add("${ConstantClass.after6am},${BusDepartureDateAndTime}" to false)
            busDepartureList.add("${ConstantClass.after12pm},${BusDepartureDateAndTime}" to false)
            busDepartureList.add("${ConstantClass.after6pm},${BusDepartureDateAndTime}" to false)

            AllBusList.forEach{ bus->
                val name = bus.operatorName ?: return@forEach
                val boardingpoint = bus.boardingDetails
                val dropingpoint = bus.droppingDetails


                if (busOperatorNameList.none { it.first == name }) {
                    busOperatorNameList.add(name to false)
                }

                boardingpoint.forEach { it->
                    val name = it.boardingName ?: return@forEach
                    if (busBoardingPointList.none { it.first == name }) {
                        busBoardingPointList.add(name to false)
                    }
                }


                dropingpoint.forEach { it->
                    val name = it.droppingName ?: return@forEach
                    if (busDropingPointList.none { it.first == name }) {
                        busDropingPointList.add(name to false)
                    }
                }

            }

            before6layout = false
            after6amlayout = false
            after12pmlayout = false
            after6pmlayout = false

            if(busDataList.isNotEmpty()){
                binding.loaderlayout.visibility=View.GONE
                binding.buslayout.visibility=View.VISIBLE
            }
            else {
                binding.loaderlayout.visibility=View.VISIBLE
                binding.buslayout.visibility=View.GONE
            }

            Log.d("AllBusListResp", Gson().toJson(AllBusList))
            busSearchAdapter.notifyDataSetChanged()

        }

        else {
            binding.animationView.setAnimation("notfoundbus.json")
            binding.animationView.repeatCount = LottieDrawable.INFINITE
            binding.animationView.playAnimation()
            binding.textmsg.text= resources.getString(R.string.notsearchbusmsg)
            binding.buslayout.visibility=View.GONE

            Log.d("BookList",response.responseHeader?.errorDesc.toString())
            Toast.makeText(this, response.responseHeader?.errorDesc.toString(), Toast.LENGTH_SHORT).show()
        }

    }

}