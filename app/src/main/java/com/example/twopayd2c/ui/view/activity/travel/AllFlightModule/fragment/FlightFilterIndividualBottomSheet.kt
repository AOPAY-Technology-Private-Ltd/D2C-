package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.databinding.IndividualFilterLayoutBinding
import com.example.twopayd2c.databinding.IndividualFlightFilterLayoutBinding
import com.example.twopayd2c.ui.adapter.AirlinesAdapter
import com.example.twopayd2c.ui.adapter.FlightIndividualFilterAdapter
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.CheckNonStop
import com.example.twopayd2c.utils.ConstantClass.Companion.FlightListForFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.SearchFlightList
import com.example.twopayd2c.utils.ConstantClass.Companion.airportNameList
import com.example.twopayd2c.utils.ConstantClass.Companion.departureFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightDepartureList
import com.example.twopayd2c.utils.ConstantClass.Companion.flightOperatorFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightTypeFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.flightTypeList
import com.example.twopayd2c.utils.ConstantClass.Companion.lastFilterList
import com.example.twopayd2c.utils.ConstantClass.Companion.priceFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.priceList
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class FlightFilterIndividualBottomSheet : BottomSheetDialogFragment(), FlightIndividualFilterAdapter.OnClickListener {

    private var onCloseListener: (() -> Unit)? = null

    lateinit var binding:IndividualFlightFilterLayoutBinding
    lateinit var adapter: FlightIndividualFilterAdapter

    private var selectedAirline = emptyList<String>()
    private var selectedFlightType = emptyList<String>()
    private var selectedDeparture = emptyList<String>()
    private var selectedPrice = emptyList<String>()
    lateinit var preference: SharedPreference


    companion object {
        var filterType: String = ""
        const val TAG = "FlightSearchSheet"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = IndividualFlightFilterLayoutBinding.inflate(inflater, container, false)
        preference = SharedPreference(requireContext())
        if (SearchFlightList.isNullOrEmpty()) {
            FlightListForFilter = preference.getFlightFilterList(ConstantClass.FlightAllList).toMutableList()
        } else {
            FlightListForFilter = SearchFlightList
        }
        setDataOnView()
        setOnClickListner()
        return binding.root
    }


    fun setOnClickListner() {

        binding.cross.setOnClickListener {
            /*filterType = ""
            Log.d("onCanclesearchlist", "${SearchFlightList.size}")

            if (SearchFlightList.size > 0) {

            }
            else {
                SearchFlightList = preference.getFlightFilterList(ConstantClass.FlightAllList).toMutableList()
                SearchFlightList = SearchFlightList
                    .filter { !CheckNonStop || it.segments?.size == 1 }
                    .toMutableList()
            }

            preference.saveFlightFilterlist(ConstantClass.FlightFilterData, SearchFlightList)
            dismiss()*/

            filterType = ""
            Log.d("onCancelSearchList", "${SearchFlightList.size}")

            if (SearchFlightList.isEmpty()) {
                SearchFlightList = preference
                    .getFlightFilterList(ConstantClass.FlightAllList)
                    .toMutableList()
            }

            // Apply filter in both cases
            SearchFlightList = SearchFlightList
                .filter { !CheckNonStop || it.segments?.size == 1 }
                .toMutableList()

            preference.saveFlightFilterlist(ConstantClass.FlightFilterData, SearchFlightList)

            dismiss()
        }

        binding.showbusseslayout.setOnClickListener {
            if (SearchFlightList.isNotEmpty()) {
                preference.saveFlightFilterlist(ConstantClass.FlightFilterData, SearchFlightList)
                dismiss()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("No Flights Found")
                    .setMessage("No flights match your filter. Showing previous results.")
                    .setPositiveButton("OK") { dialog, _ ->
                        SearchFlightList.clear()
                        SearchFlightList.addAll(lastFilterList)
                        FlightListForFilter = lastFilterList
                        preference.saveFlightFilterlist(ConstantClass.FlightFilterData, SearchFlightList)
                        dialog.dismiss()
                        dismiss()
                    }.show()
            }

        }

        binding.searchairlines.addTextChangedListener(object : TextWatcher {

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

               if(filterType == ConstantClass.FlightAirlines) {
                   adapter = FlightIndividualFilterAdapter(requireContext(), filteredList, FlightIndividualFilterType.AIRLINE, this@FlightFilterIndividualBottomSheet)
                   binding.showairlinelist.adapter=adapter
               }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }


    private fun setDataOnView() {
        binding.filterType.text = filterType
        binding.showairlinelist.layoutManager = LinearLayoutManager(requireContext())

        when {
            filterType == ConstantClass.FlightPrice -> {
                adapter = FlightIndividualFilterAdapter(
                    requireContext(),
                    priceList,
                    FlightIndividualFilterType.PRICE,
                    this
                )
                binding.chooseairlineslayout.visibility = View.GONE
            }

            filterType == ConstantClass.FlightType -> {
                adapter = FlightIndividualFilterAdapter(
                    requireContext(),
                    flightTypeList,
                    FlightIndividualFilterType.FLIGHTTYPE,
                    this
                )
                binding.chooseairlineslayout.visibility = View.GONE
            }

            filterType == ConstantClass.FlightDeparture -> {
                adapter = FlightIndividualFilterAdapter(
                    requireContext(),
                    flightDepartureList,
                    FlightIndividualFilterType.DEPARTURE,
                    this
                )
                binding.chooseairlineslayout.visibility = View.GONE
            }

            filterType == ConstantClass.FlightAirlines -> {
                adapter = FlightIndividualFilterAdapter(
                    requireContext(),
                    airportNameList,
                    FlightIndividualFilterType.AIRLINE,
                    this
                )
                binding.chooseairlineslayout.visibility = View.VISIBLE
            }
        }
        binding.showairlinelist.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    override fun setonclicklistner(selectedNames: List<String>, filterType: FlightIndividualFilterType) {
        when (filterType) {

            FlightIndividualFilterType.PRICE -> {
                selectedPrice = selectedNames

            }

            FlightIndividualFilterType.AIRLINE -> {
                selectedAirline = selectedNames

            }

            FlightIndividualFilterType.FLIGHTTYPE -> {
                selectedFlightType = selectedNames

            }

            FlightIndividualFilterType.DEPARTURE -> {
                selectedDeparture = selectedNames

            }
        }

        applyFilters()
    }


    fun applyFilters() {
        lastFilterList = SearchFlightList.toMutableList()

        preference.saveFlightFilterlist(ConstantClass.FlightFilterData, null)
        var filteredList: MutableList<FlightsItem> = mutableListOf()
        // Always start from original list

        filteredList = preference.getFlightFilterList(ConstantClass.FlightAllList).toMutableList()

        // 1️⃣ Flight Type Filter (stops / Non stops)
        if (selectedFlightType.isNotEmpty()) {
            filteredList = filteredList.filter { flight ->
                when {
                    selectedFlightType.contains(ConstantClass.nonstop) -> flight.segments?.size == 1
                    selectedFlightType.contains(ConstantClass.stops) -> (flight.segments?.size ?: 0) > 1

                    else -> true
                }
            }.toMutableList()

            flightTypeFilter = true
        }
        else{
            flightTypeFilter = false
        }

        if (selectedPrice.isNotEmpty()) {
            filteredList = filteredList.let { flight ->
                when {
                    selectedPrice.contains(ConstantClass.FlightLowToHigh) -> flight.sortBy {
                        it.fares?.getOrNull(
                            0
                        )?.fareDetails?.getOrNull(0)?.totalAmount?.toDoubleOrNull()?:Double.MAX_VALUE
                    }

                    selectedPrice.contains(ConstantClass.FlightHighToLow) -> flight.sortByDescending {
                        it.fares?.getOrNull(
                            0
                        )?.fareDetails?.getOrNull(0)?.totalAmount?.toDoubleOrNull()?:Double.MIN_VALUE
                    }

                    else -> true
                }
                flight.toMutableList()
            }
            priceFilter = true
        }
        else{
            priceFilter = false
        }

        // 2️⃣ Departure Filter
        if (selectedDeparture.isNotEmpty()) {
            filteredList = filteredList.filter { flight ->

                val time = flight.segments?.getOrNull(0)?.departureDateTime
                if (time.isNullOrEmpty()) return@filter false

                runCatching {

                    // Extract hour from "03/06/2026 16:35"
                    val hour = time.substringAfter(" ").substringBefore(":").toInt()

                    when {
                        selectedDeparture.contains(ConstantClass.before6am) -> hour < 6
                        selectedDeparture.contains(ConstantClass.after6am) -> hour in 6..11
                        selectedDeparture.contains(ConstantClass.after12pm) -> hour in 12..17
                        selectedDeparture.contains(ConstantClass.after6pm) -> hour >= 18
                        else -> true
                    }

                }.getOrDefault(false)

            }.toMutableList()
            departureFilter = true
        }else{
            departureFilter = false
        }


        // 3️⃣ Airline Filter
        if (selectedAirline.isNotEmpty()) {
            filteredList = filteredList.filter { flight ->
                val airlineName = flight.segments?.getOrNull(0)?.airlineName
                airlineName != null && selectedAirline.contains(airlineName)
            }.toMutableList()
            flightOperatorFilter = true
        }
        else{
            flightOperatorFilter = false
        }


        // Final Update
        FlightListForFilter = filteredList
        SearchFlightList.clear()
        SearchFlightList.addAll(filteredList)

    }


    enum class FlightIndividualFilterType {
        PRICE,
        FLIGHTTYPE,
        DEPARTURE,
        AIRLINE
    }


    fun setOnCloseListener(listener: () -> Unit) {
        onCloseListener = listener
    }


    override fun onDismiss(dialog: android.content.DialogInterface) {
        super.onDismiss(dialog)
        onCloseListener?.invoke()
    }




}