package com.example.twopayd2c.ui.view.activity.travel.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.twopayd2c.adapter.CurrentWithNextDateAdapter
import com.example.twopayd2c.adapter.RecentBusSearchAdapter
import com.example.twopayd2c.data.model.travelmodel.RecentSearchListModel
import com.example.twopayd2c.databinding.FragmentBusSearchHomePageBinding
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusCitySearchPage
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSearchDetails
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.MyBookingBusActivity
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.Currentwithnext5dateList
import com.example.twopayd2c.utils.ConstantClass.Companion.FromBus
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityId
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.SearchFromToEditText
import com.example.twopayd2c.utils.ConstantClass.Companion.ToBus
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityId
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.convertDate
import com.example.twopayd2c.utils.ConstantClass.Companion.getCurrentwithnext5date
import com.example.twopayd2c.utils.ConstantClass.Companion.searchDateFormList
import com.example.twopayd2c.utils.ConstantClass.Companion.searchDateIfNotInList
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date


class BusSearchHomePageFragment : Fragment(), CurrentWithNextDateAdapter.onItemClickListner, RecentBusSearchAdapter.onItemClickListner {
    lateinit var binding: FragmentBusSearchHomePageBinding
    var recentSearchList: MutableList<RecentSearchListModel> = mutableListOf()
    private var checkReverse = false
    lateinit var logtag : String
    lateinit var adapter : CurrentWithNextDateAdapter
    var searchDateForTravel : String=""
    lateinit var preference: SharedPreference


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentBusSearchHomePageBinding.inflate(layoutInflater)
        logtag = "BusSearchHomePageFragment"
        preference = SharedPreference(requireContext())

        getCurrentwithnext5date()
        setRecentData()
        setDataInView()
        setClickListner()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        if(FromCityName.isNotEmpty() || ToCityName.isNotEmpty()){
            binding.fromcityname.text = FromCityName
            binding.tocityname.text = ToCityName
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun  setClickListner(){
        binding.fromcityLayout.setOnClickListener {
            SearchFromToEditText= FromBus
            startActivity(Intent(requireContext(), BusCitySearchPage::class.java))
        }

        binding.tocityLayout.setOnClickListener {
            SearchFromToEditText= ToBus
            startActivity(Intent(requireContext(), BusCitySearchPage::class.java))
        }

        binding.reversetrip.setOnClickListener {
            FromCityName = binding.fromcityname.text.toString()
            ToCityName = binding.tocityname.text.toString()

            if (checkReverse) {
                binding.fromcityname.text = ToCityName
                binding.tocityname.text = FromCityName
                checkReverse = false
            }
            else {
                binding.tocityname.text = FromCityName
                binding.fromcityname.text = ToCityName
                checkReverse = true
            }
        }

        binding.calendarLayout.setOnClickListener {
            openCalendar()
        }

        binding.busSearchButton.setOnClickListener {
            validationCity()

        }

        binding.mybookingButton.setOnClickListener {
            startActivity(Intent(requireContext(), MyBookingBusActivity::class.java))
        }

    }


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
                fromcityId = FromCityId,
                "",
                searchDateForTravel,
                binding.tocityname.text.toString(),
                "",
                tocityId = ToCityId
            )

            val savedDataList = preference.getList(ConstantClass.RecentSavedData).toMutableList()

            // 1️⃣ Remove duplicate (same from, to, date)
            savedDataList.removeAll { it.fromcity == newItem.fromcity && it.tocity == newItem.tocity && it.searchdate == newItem.searchdate && it.fromcityId == newItem.fromcityId && it.tocityId == newItem.tocityId }

            // 2️⃣ Add newest item at top
            savedDataList.add(0, newItem)

            if (savedDataList.size > 5) {
                savedDataList.subList(5, savedDataList.size).clear()
            }
            preference.savelist(ConstantClass.RecentSavedData, savedDataList)

            setRecentData()

            preference?.setStringValue(ConstantClass.fromDesignationId, FromCityId)
            preference?.setStringValue(ConstantClass.fromDesignationName, binding.fromcityname.text.toString())
            preference?.setStringValue(ConstantClass.toDesignationId, ToCityId)
            preference?.setStringValue(ConstantClass.toDesignationName, binding.tocityname.text.toString())
            preference?.setStringValue(ConstantClass.dateAndTime, searchDateForTravel)


            startActivity(Intent(requireContext(), BusSearchDetails::class.java))
        }

    }



    fun setRecentData(){
        recentSearchList = preference.getList(ConstantClass.RecentSavedData).toMutableList()
        binding.recentsearchlayout.adapter = RecentBusSearchAdapter(recentSearchList,this)
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
            searchDateForTravel=searchDateFormList(Currentwithnext5dateList[0])
        }
        Log.d(logtag, "setDataInView: "+now)

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

            Log.d(logtag, "openCalendar: $date")
            Toast.makeText(requireContext(), selectedDate.toString(), Toast.LENGTH_SHORT).show()
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int) {
        searchDateForTravel=searchDateFormList(Currentwithnext5dateList[position])
        Log.d(logtag, searchDateForTravel)
    }


    override fun recentItemClick(recentsearchdata: RecentSearchListModel) {
        binding.fromcityname.text= recentsearchdata.fromcity
        FromCityId = recentsearchdata.fromcityId.toString()
        ToCityId = recentsearchdata.tocityId.toString()
        binding.tocityname.text= recentsearchdata.tocity
    }


}