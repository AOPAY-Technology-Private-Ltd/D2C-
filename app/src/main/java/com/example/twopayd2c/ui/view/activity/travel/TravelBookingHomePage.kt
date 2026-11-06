package com.example.twopayd2c.ui.view.activity.travel

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.ServicesListModel
import com.example.twopayd2c.databinding.ActivityBusBookingHomePageBinding
import com.example.twopayd2c.ui.view.activity.travel.fragment.FlightSearchHomePageFragment
import com.example.twopayd2c.ui.adapter.TravelServicesAdapter
import com.example.twopayd2c.ui.view.activity.travel.fragment.BusSearchHomePageFragment
import com.example.twopayd2c.utils.ConstantClass.Companion.FromCityName
import com.example.twopayd2c.utils.ConstantClass.Companion.RETAILERALLSERVICES
import com.example.twopayd2c.utils.ConstantClass.Companion.ToCityName

class TravelBookingHomePage : AppCompatActivity() {
    lateinit var binding: ActivityBusBookingHomePageBinding
    var serviceslist : MutableList<ServicesListModel> = mutableListOf()
    lateinit var travelServicesAdapter: TravelServicesAdapter

    companion object
    {
        var serviceName: String=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBusBookingHomePageBinding.inflate(layoutInflater)
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
        setTravel()
        setOnClickListner()

    }


    fun setOnClickListner(){
        binding.backicon.setOnClickListener {
            onBackPressed()
        }
    }

    fun  setTravel(){
        serviceslist.clear()

        serviceslist.add(ServicesListModel(R.drawable.flightserviceicon,getString(R.string.flight),"F0134","Y"))
        serviceslist.add(ServicesListModel(R.drawable.busserviceicon,getString(R.string.bus),"F0133","Y"))
        serviceslist.add(ServicesListModel(R.drawable.trainserviceicon,getString(R.string.train),"F0140","Y"))
        serviceslist.add(ServicesListModel(R.drawable.hotel,getString(R.string.hotel),"F0140","Y"))


        travelServicesAdapter= TravelServicesAdapter(serviceslist,this@TravelBookingHomePage,this@TravelBookingHomePage)
        binding.financialserviceslist.adapter = travelServicesAdapter
        travelServicesAdapter.updateViewSelection(serviceName)

        if(serviceName.equals(resources.getString(R.string.bus))){
           callFragment(BusSearchHomePageFragment(), "bus",serviceslist[1].featurecode,serviceslist[1].activeYN,"BusMainFragment")

        }

        if (serviceName.equals(resources.getString(R.string.flight))){
            callFragment(FlightSearchHomePageFragment(), "flight", serviceslist[0].featurecode,serviceslist[0].activeYN,"FlightMainFragment")
        }


    }


    fun callFragment(fragment: Fragment, rechargeType: String, featureCode:String, activestatus:String, tag: String) {
        val bundle = Bundle()
        bundle.putString("RechargeType", rechargeType)
        bundle.putString("FeatureCode", featureCode)
        bundle.putString("ActiveStatus", activestatus)
        fragment.arguments = bundle

        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment,tag)
        transaction.addToBackStack(tag)
        transaction.commit()
        binding.fragment.visibility= View.VISIBLE
    }



    override fun onBackPressed() {
        FromCityName = ""
        ToCityName = ""
        finish()
        super.onBackPressed()


    }


}