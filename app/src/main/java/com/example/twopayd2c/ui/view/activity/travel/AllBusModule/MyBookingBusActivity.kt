package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.twopayd2c.R
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityBusFinalBookedTicketPageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.PassengerDataForFlightAdapter
import com.example.twopayd2c.ui.adapter.ViewPagerAdapter
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MyBookingBusActivity : AppCompatActivity() {
    lateinit var binding : ActivityBusFinalBookedTicketPageBinding
    val statusArray = listOf("Booked Tickets", "Cancelled")
    private lateinit var viewModel: TravelViewModel
    private var preference: SharedPreference? = null
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var viewPagerAdapter: ViewPagerAdapter
    var startDate: String = ""
    var endDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBusFinalBookedTicketPageBinding.inflate(layoutInflater)
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
        setView()
        setupTabs()
        setupViewModel()
        setOnClickListner()

        startDate = getTodayIsoUtc()
        endDate = getTodayIsoUtc()

        ConstantClass.startDate = startDate
        ConstantClass.endDate = endDate

        val uiFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.startdate.setText(uiFormat.format(Date()))
        binding.enddate.setText(uiFormat.format(Date()))

        if(startDate.isNotBlank()&&endDate.isNotBlank()){
            refreshSelectedTab(startDate!!, endDate!!)
        }

    }

    private fun setupViewModel() {
        preference = SharedPreference.getInstance(this@MyBookingBusActivity)
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]

    }

    private fun setView() {
        viewPager = binding.viewPager
        tabLayout = binding.tablayout

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val textView = tab.customView ?.findViewById<TextView>(R.id.tabText)
                textView?.isSelected = true // triggers ColorStateList
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val textView = tab.customView ?.findViewById<TextView>(R.id.tabText)
                textView?.isSelected = false
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


        // Also mark the initially selected tab (0)
        (tabLayout.getTabAt(tabLayout.selectedTabPosition)?.customView ?.findViewById<TextView>(R.id.tabText))?.isSelected = true

    }

    private fun setupTabs() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = viewPagerAdapter
        viewPager.isUserInputEnabled = true

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabView = LayoutInflater.from(tabLayout.context).
            inflate(R.layout.tab_title, tabLayout,false)
            tabView.findViewById<TextView>(R.id.tabText).text = statusArray[position]
            tab.customView = tabView
        }.attach()

        tabLayout.post {
            val tab = tabLayout.getTabAt(0)
            val textView = tab?.customView?.findViewById<TextView>(R.id.tabText)
            textView?.isSelected = true
        }
    }


    fun  setOnClickListner(){
        binding.backicon.setOnClickListener {
            finish()
        }

        binding.startdatecalendar.setOnClickListener {
            // Define the format to ISO 8601 with UTC timezone
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")

            // Get current date for initializing DatePicker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                R.style.MyDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->

                    // Create Calendar from selected date
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(Calendar.YEAR, selectedYear)
                    selectedCalendar.set(Calendar.MONTH, selectedMonth)
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, selectedDay)
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, 0)
                    selectedCalendar.set(Calendar.MINUTE, 0)
                    selectedCalendar.set(Calendar.SECOND, 0)
                    selectedCalendar.set(Calendar.MILLISECOND, 0)

                    // Convert to ISO 8601 format in UTC
                    val isoDate = sdf.format(selectedCalendar.time)

                    // Store or use the ISO date string
                    startDate = isoDate
                    ConstantClass.startDate = isoDate
                    Log.d("Start Date", "" + startDate)
                    if (!startDate!!.isNullOrEmpty() && !endDate!!.isNullOrEmpty()) {


                        refreshSelectedTab(startDate!!, endDate!!)

                    }

                    // Display in user-friendly format (optional)
                    binding.startdate.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")

                },
                year,
                month,
                day
            )

            dpd.setOnShowListener {
                dpd.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    ?.setTextColor(ContextCompat.getColor(this, R.color.green))
                dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    ?.setTextColor(ContextCompat.getColor(this, R.color.red))
            }


            dpd.show()
        }

        binding.enddatecalendar.setOnClickListener {
            // Define the format to ISO 8601 with UTC timezone
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")

            // Get current date for initializing DatePicker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                R.style.MyDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->

                    // Create Calendar from selected date
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(Calendar.YEAR, selectedYear)
                    selectedCalendar.set(Calendar.MONTH, selectedMonth)
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, selectedDay)
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, 0)
                    selectedCalendar.set(Calendar.MINUTE, 0)
                    selectedCalendar.set(Calendar.SECOND, 0)
                    selectedCalendar.set(Calendar.MILLISECOND, 0)

                    // Convert to ISO 8601 format in UTC
                    val isoDate = sdf.format(selectedCalendar.time)

                    // Store or use the ISO date string
                    endDate = isoDate
                    ConstantClass.endDate = isoDate
                    Log.d("End Date", "" + endDate)

                    if (!startDate!!.isNullOrEmpty() && !endDate!!.isNullOrEmpty()) {
                        // hitApiForBookingList(startDate!!, endDate!!)
                        refreshSelectedTab(startDate!!, endDate!!)

                    }

                    // Display in user-friendly format (optional)
                    binding.enddate.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")

                },
                year,
                month,
                day
            )

            dpd.setOnShowListener {
                dpd.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    ?.setTextColor(ContextCompat.getColor(this, R.color.green))
                dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    ?.setTextColor(ContextCompat.getColor(this, R.color.red))
            }


            dpd.show()
        }

    }

    private fun refreshSelectedTab(startDate: String, endDate: String) {
        val currentPosition = viewPager.currentItem
        val fragment = viewPagerAdapter.getFragment(currentPosition)

        if (fragment is BookingRefreshListener) {
            fragment.refreshData(startDate, endDate)
        }
    }

    private fun getTodayIsoUtc(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        return sdf.format(cal.time)
    }

    interface BookingRefreshListener {
        fun refreshData(startDate: String, endDate: String)
    }

}