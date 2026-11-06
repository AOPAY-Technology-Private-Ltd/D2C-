package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.FilterFlightItemLayoutBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.AirlinesAdapter
import com.example.twopayd2c.ui.adapter.FlightIndividualFilterAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.FlightFilterIndividualBottomSheet.Companion.filterType
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.FlightFilterIndividualBottomSheet.FlightIndividualFilterType
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.AC
import com.example.twopayd2c.utils.ConstantClass.Companion.AllFlightList
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightDepartureDateAndTime
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightListForFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.SearchFlightList
import com.example.twopayd2c.utils.ConstantClass.Companion.Stops
import com.example.twopayd2c.utils.ConstantClass.Companion.after12pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6amlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.after6pmlayout
import com.example.twopayd2c.utils.ConstantClass.Companion.airportNameList
import com.example.twopayd2c.utils.ConstantClass.Companion.before6layout
import com.example.twopayd2c.utils.ConstantClass.Companion.departureFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightOperatorFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightTypeFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.lastFilterList
import com.example.twopayd2c.utils.ConstantClass.Companion.nonAC
import com.example.twopayd2c.utils.ConstantClass.Companion.nonStops
import com.example.twopayd2c.utils.ConstantClass.Companion.priceFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.priceList
import com.example.twopayd2c.utils.SharedPreference

import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class FlightFilterActivity: AppCompatActivity() , AirlinesAdapter.OnClickListener,FlightIndividualFilterAdapter.OnClickListener {
    private lateinit var binding : FilterFlightItemLayoutBinding
    lateinit var viewModel : TravelViewModel
    private var mStash: SharedPreference? = null
    val code = "100"
    private var airlinesNameList: List<String> = listOf()
    val filteredList = mutableListOf<String>()
    private var selectedPrice = emptyList<String>()
    lateinit var preference: SharedPreference
    lateinit var priceadapter: FlightIndividualFilterAdapter

    companion object {
        const val TAG = "FlightFilterBottomSheet"
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        binding = FilterFlightItemLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preference = SharedPreference(this)

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


        mStash = SharedPreference(this)
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        selectedFlightType()
        when {
            nonStops -> {
                binding.nonstoplayout.background=resources.getDrawable(R.drawable.selectedfrombox)
                binding.nonstopicon.imageTintList=resources.getColorStateList(R.color.primary_blue)
                binding.nonstoptxt.setTextColor(resources.getColor(R.color.primary_blue))
            }

            Stops -> {
                binding.stopslayout.background=resources.getDrawable(R.drawable.selectedfrombox)
                binding.stopsicon.imageTintList=resources.getColorStateList(R.color.primary_blue)
                binding.stopstext.setTextColor(resources.getColor(R.color.primary_blue))
            }
        }

        setupTimeFilters()
        selectionHoverofTime ()
        when {
            before6layout -> highlightSelected(binding.before6layout, binding.before6icon, binding.before6txt)
            after6amlayout -> highlightSelected(binding.after6amlayout, binding.after6amimage, binding.after6amtxt)
            after12pmlayout -> highlightSelected(binding.after12pmlayout, binding.after12pmicon, binding.after12pmtxt)
            after6pmlayout -> highlightSelected(binding.after6pmlayout, binding.after6pmicon, binding.after6pmtxt)
        }

        setDataOnView()

        setOnClickListner()


    }


    private fun setupTimeFilters() {
        if (FlightDepartureDateAndTime != null) {
            val currentCal = Calendar.getInstance()
            val departureCal = Calendar.getInstance().apply {
                time = Date(FlightDepartureDateAndTime)
            }
            val isSameDay = currentCal.get(Calendar.YEAR) == departureCal.get(Calendar.YEAR) && currentCal.get(Calendar.DAY_OF_YEAR) == departureCal.get(Calendar.DAY_OF_YEAR)
            val currentHour = currentCal.get(Calendar.HOUR_OF_DAY)

            if (isSameDay) {
                // Enable and highlight based on current time
                binding.before6layout.apply {
                    isEnabled = currentHour < 6
                    alpha = if (isEnabled) 1f else 0.5f
                }

                binding.after6amlayout.apply {
                    isEnabled = currentHour < 12
                    alpha = if (isEnabled) 1f else 0.5f
                }

                binding.after12pmlayout.apply {
                    isEnabled = currentHour < 18
                    alpha = if (isEnabled) 1f else 0.5f
                }

                binding.after6pmlayout.apply {
                    isEnabled =  currentHour < 24
                    alpha = if (isEnabled) 1f else 0.5f
                }
            }
            else {
                // Future date - enable all with full visibility
                binding.before6layout.apply {
                    isEnabled = true
                    alpha = 1f
                }
                binding.after6amlayout.apply {
                    isEnabled = true
                    alpha = 1f
                }
                binding.after12pmlayout.apply {
                    isEnabled = true
                    alpha = 1f
                }
                binding.after6pmlayout.apply {
                    isEnabled = true
                    alpha = 1f
                }
            }

        }
        else
        {
            // Past time - disable all
            binding.before6layout.isEnabled = false
            binding.after6amlayout.isEnabled = false
            binding.after12pmlayout.isEnabled = false
            binding.after6pmlayout.isEnabled = false
        }
    }


    private fun setDataOnView(){
        binding.flightcount.text= filteredList.size.toString().plus(" Flights Found")

        binding.showairlinelist.layoutManager = LinearLayoutManager(this)
        binding.pricelist.layoutManager = LinearLayoutManager(this)


        priceadapter = FlightIndividualFilterAdapter(this, priceList, FlightIndividualFilterType.PRICE, this)
        binding.pricelist.adapter = priceadapter


        var adapter = AirlinesAdapter(this,airportNameList,this)
        binding.showairlinelist.adapter=adapter


        binding.showairlinelist.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    if (binding.showairlinelist.childCount == 0) return

                    val itemHeight = binding.showairlinelist.getChildAt(0).measuredHeight
                    val visibleItems = minOf(airportNameList.size, 6)

                    val params = binding.showairlinelist.layoutParams
                    params.height = itemHeight * visibleItems
                    binding.showairlinelist.layoutParams = params

                    binding.showairlinelist.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        )


        binding.showairlinelist.apply {
            isNestedScrollingEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER
        }


        binding.showairlinelist.addOnItemTouchListener(
            object : RecyclerView.OnItemTouchListener {

                override fun onInterceptTouchEvent(
                    rv: RecyclerView,
                    e: MotionEvent
                ): Boolean {

                    when (e.action) {
                        MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_MOVE -> {
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                        }

                        MotionEvent.ACTION_UP,
                        MotionEvent.ACTION_CANCEL -> {
                            rv.parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            }
        )


    }



    fun setOnClickListner(){

        binding.clearAllFilter.setOnClickListener {
            selectedFlightType()
            selectionHoverofTime()

            nonStops= false
            Stops= false
            before6layout = false
            after6amlayout= false
            after12pmlayout= false
            after6pmlayout= false


            priceFilter = false
            flightTypeFilter = false
            departureFilter = false
            flightOperatorFilter = false

            if (SearchFlightList.size > 0) {

            }
            else {
                SearchFlightList = preference.getFlightFilterList(ConstantClass.FlightAllList).toMutableList()
            }

            preference.saveFlightFilterlist(ConstantClass.FlightFilterData, SearchFlightList)


            // update list
            var updateAirList = airportNameList.map { it.first to false }.toMutableList()
            airportNameList=updateAirList
            setDataOnView()

            val resultIntent = Intent()
            resultIntent.putExtra("code", code)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }


        binding.cross.setOnClickListener {
            finish()
        }

        binding.nonstoplayout.setOnClickListener {
            nonStops=true
            Stops=false
            selectedFlightType()
            applyFilters()
            binding.nonstoplayout.background=resources.getDrawable(R.drawable.selectedfrombox)
            binding.nonstopicon.imageTintList=resources.getColorStateList(R.color.primary_blue)
            binding.nonstoptxt.setTextColor(resources.getColor(R.color.primary_blue))

        }


        binding.stopslayout.setOnClickListener {
            nonStops= false
            Stops=true
            selectedFlightType()
            applyFilters()
            binding.stopslayout.background=resources.getDrawable(R.drawable.selectedfrombox)
            binding.stopsicon.imageTintList=resources.getColorStateList(R.color.primary_blue)
            binding.stopstext.setTextColor(resources.getColor(R.color.primary_blue))

        }

        binding.filterbyfliteoperator.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("code", code)

            if(SearchFlightList.isNotEmpty()){
                preference.saveFlightFilterlist(ConstantClass.FlightFilterData, SearchFlightList)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                Log.d("FilterList",filteredList.size.toString().plus( ""))
            }
            else{
                SearchFlightList.clear()
                SearchFlightList.addAll(lastFilterList)
                FlightListForFilter = lastFilterList
                preference.saveFlightFilterlist(ConstantClass.FlightFilterData, SearchFlightList)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                Log.d("FilterList",filteredList.size.toString().plus( ""))
            }

        }


        binding.before6layout.setOnClickListener {
            selectionHoverofTime ()
            binding.before6layout.background= resources.getDrawable(R.drawable.selectedfrombox)
            binding.before6icon.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.primary_blue)
            binding.before6txt.setTextColor(
                ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.primary_blue)
            )

            before6layout =true
            after6amlayout= false
            after12pmlayout= false
            after6pmlayout= false
            applyFilters()

        }

        binding.after6amlayout.setOnClickListener {
            selectionHoverofTime ()
            binding.after6amlayout.background= resources.getDrawable(R.drawable.selectedfrombox)
            binding.after6amimage.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity,R.color.primary_blue)
            binding.after6amtxt.setTextColor(
                ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.primary_blue)
            )
            before6layout =false
            after6amlayout= true
            after12pmlayout= false
            after6pmlayout= false

            applyFilters()

        }

        binding.after12pmlayout.setOnClickListener {
            selectionHoverofTime ()
            binding.after12pmlayout.background= resources.getDrawable(R.drawable.selectedfrombox)
            binding.after12pmicon.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity,R.color.primary_blue)
            binding.after12pmtxt.setTextColor(ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.primary_blue))
            before6layout =false
            after6amlayout= false
            after12pmlayout= true
            after6pmlayout= false
            applyFilters()

        }

        binding.after6pmlayout.setOnClickListener {
            selectionHoverofTime ()
            binding.after6pmlayout.background= resources.getDrawable(R.drawable.selectedfrombox)
            binding.after6pmicon.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity,R.color.primary_blue)
            binding.after6pmtxt.setTextColor(
                ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.primary_blue)
            )
            before6layout =false
            after6amlayout= false
            after12pmlayout= false
            after6pmlayout= true

            applyFilters()
        }

        binding.searchairlines.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()

                val filteredList = if (searchText.isNotEmpty()) {
                    airportNameList.filter { it.first.contains(searchText, ignoreCase = true) }.toMutableList()
                }
                else {
                    airportNameList // show full list if input is empty
                }

                var adapter = AirlinesAdapter(this@FlightFilterActivity,filteredList,this@FlightFilterActivity)
                binding.showairlinelist.adapter=adapter
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }




    fun selectedFlightType()
    {
        binding.nonstoplayout.background=resources.getDrawable(R.drawable.editouterborder)
        binding.nonstopicon.imageTintList=resources.getColorStateList(R.color.black)
        binding.nonstoptxt.setTextColor(resources.getColor(R.color.edtsubblack))

        binding.stopslayout.background=resources.getDrawable(R.drawable.editouterborder)
        binding.stopsicon.imageTintList=resources.getColorStateList(R.color.black)
        binding.stopstext.setTextColor(resources.getColor(R.color.edtsubblack))

    }




    fun selectionHoverofTime (){

        binding.before6layout.background= resources.getDrawable(R.drawable.editouterborder)
        binding.after6amlayout.background= resources.getDrawable(R.drawable.editouterborder)
        binding.after12pmlayout.background= resources.getDrawable(R.drawable.editouterborder)
        binding.after6pmlayout.background= resources.getDrawable(R.drawable.editouterborder)


        binding.before6icon.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.black)
        binding.after6amimage.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity,R.color.black)
        binding.after12pmicon.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity,R.color.black)
        binding.after6pmicon.imageTintList = ContextCompat.getColorStateList(this@FlightFilterActivity,R.color.black)



        binding.after6amtxt.setTextColor(
            ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.edtblack)
        )
        binding.after12pmtxt.setTextColor(
            ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.edtblack)
        )
        binding.after6pmtxt.setTextColor(
            ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.edtblack)
        )
        binding.before6txt.setTextColor(
            ContextCompat.getColorStateList(this@FlightFilterActivity, R.color.edtblack)
        )

    }


    fun highlightSelected(layout: View, icon: ImageView, text: TextView) {
        layout.background = ContextCompat.getDrawable(this, R.drawable.selectedfrombox)
        icon.imageTintList = ContextCompat.getColorStateList(this, R.color.primary_blue)
        text.setTextColor(ContextCompat.getColorStateList(this, R.color.primary_blue))
    }



    override fun setonclicklistner(airlineNames: List<String>) {
        airlinesNameList = airlineNames
        applyFilters()
    }



    fun applyFilters() {
        lastFilterList = SearchFlightList.toMutableList()

        preference.saveFlightFilterlist(ConstantClass.FlightFilterData, null)

        var filteredList: MutableList<FlightsItem> = mutableListOf()
        // Always start from original list

        filteredList = preference.getFlightFilterList(ConstantClass.FlightAllList).toMutableList()

        filteredList = filteredList.let { flight ->
            when {
                selectedPrice.contains(ConstantClass.FlightLowToHigh) ->
                    flight.sortBy { it.fares?.getOrNull(0)?.fareDetails?.getOrNull(0)?.totalAmount?.toDoubleOrNull()?:Double.MAX_VALUE }

                selectedPrice.contains(ConstantClass.FlightHighToLow) ->
                    flight.sortByDescending { it.fares?.getOrNull(0)?.fareDetails?.getOrNull(0)?.totalAmount?.toDoubleOrNull()?:
                    Double.MIN_VALUE}

                else -> true
            }
            flight.toMutableList()
        }


        // Filter by stops
        if (nonStops) {
            filteredList = filteredList.filter { it.segments?.size == 1 }.toMutableList()
        }
        else if (Stops) {
            filteredList = filteredList.filter { (it.segments?.size ?: 0) > 1 }.toMutableList()
        }


        SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())

        filteredList = filteredList.filter { flight ->

            val time = flight.segments?.getOrNull(0)?.departureDateTime
            if (time.isNullOrEmpty()) return@filter false

            runCatching {

                // Extract hour from "03/06/2026 16:35"
                val hour = time.substringAfter(" ").substringBefore(":").toInt()

                when {
                    before6layout -> hour < 6
                    after6amlayout -> hour in 6..11
                    after12pmlayout -> hour in 12..17
                    after6pmlayout -> hour >= 18
                    else -> true
                }

            }.getOrDefault(false)

        }.toMutableList()


        // Filter by airline
        if (airlinesNameList.isNotEmpty()) {
            filteredList = filteredList.filter { flight ->
                val airlineName = flight.segments?.getOrNull(0)?.airlineName
                airlineName != null && airlinesNameList.contains(airlineName)
            }.toMutableList()
        }


        // Update search list
        FlightListForFilter = filteredList
        SearchFlightList.clear()
        SearchFlightList.addAll(filteredList)

    }

    override fun setonclicklistner(selectedNames: List<String>, filterType: FlightIndividualFilterType) {
        selectedPrice = selectedNames
        applyFilters()
    }


}