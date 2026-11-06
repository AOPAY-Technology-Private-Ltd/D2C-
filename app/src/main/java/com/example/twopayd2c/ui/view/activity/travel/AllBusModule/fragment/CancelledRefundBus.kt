package com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twopayd2c.data.model.travelmodel.travel.bus.busTicket.BusBookingListReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPassengerDetailsReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.CancelTicketDataItem
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.PaXDetailsItem
import com.example.twopayd2c.data.model.travelmodel.bus.ticketCancellation.CheckActiveStatusReq
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.FragmentCancelledRefundBusBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.BusTicketCancelledAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.MyBookingBusActivity
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class CancelledRefundBus : Fragment() , MyBookingBusActivity.BookingRefreshListener {
    lateinit var binding: FragmentCancelledRefundBusBinding
    var BusCancelList: MutableList<CancelTicketDataItem> = mutableListOf()
    var CancelTicketList : MutableList<CancelTicketDataItem> = mutableListOf()
    lateinit var upcomingadapter: BusTicketCancelledAdapter
    var tableData : MutableList<MutableList<String?>> = arrayListOf()
    var checkSelectedPassangerList : MutableList<Int> = mutableListOf()
    var selectedCardPosition:Int = 0
    private var mStash: SharedPreference? = null
    private var passangerList : MutableList<PaXDetailsItem> = mutableListOf()
    private lateinit var viewModel: TravelViewModel
    private var isViewReady = false
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        mStash = SharedPreference.getInstance(context)
        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(GetAllAPIServiceRepository(ApiClient.apiAllInterface)))[GetAllApiServiceViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentCancelledRefundBusBinding.inflate(inflater,container,false)
        isViewReady = true
        return binding.root
    }


    private fun  setRecyclerview(){
        if(CancelTicketList.size>0){
            binding.notfounddatalayout.visibility=View.GONE
            binding.showingCancelList.visibility=View.VISIBLE
            upcomingadapter= BusTicketCancelledAdapter(requireContext(),CancelTicketList,this)
            binding.showingCancelList.apply { layoutManager= LinearLayoutManager(context)
                adapter=upcomingadapter
            }
            upcomingadapter.notifyDataSetChanged()
        }
        else{
            binding.notfounddatalayout.visibility=View.VISIBLE
            binding.showingCancelList.visibility=View.GONE
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun hitApiForPassangerDetails(position:Int, bookingrefNo:String){
        var requestForPassengerDetails = BusPassengerDetailsReq(
            bookingRefNo = bookingrefNo
        )
        Log.d("BusPassangerReq", Gson().toJson(requestForPassengerDetails))

        viewModel.getPassangerDetailsRequest(requestForPassengerDetails).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                if(response.isSuccess){
                                    Log.d("PaxDetailsResponse", Gson().toJson(response))
                                    var getdata = response.data!![0].apiData
                                    passangerList!!.clear()
                                    passangerList!!.addAll(response.data!![0].apiData.paXDetails!!)
                                    var boardingTime = getdata.busDetail.departureTime
                                    var fromCity = getdata.busDetail.fromCity
                                    var toCity = getdata.busDetail.toCity
                                    var busOperatorName= getdata.busDetail.operatorName
                                    var travelDate = getdata.busDetail.travelDate
                                    var travelType = getdata.busDetail.busType
                                    var droppingTime = getdata.busDetail.arrivalTime
                                    var passangerQunatity = getdata.noofPax

                                    tableData.clear()
                                    var index = 1
                                    for (passenger in passangerList) {
                                        if (passenger.status.equals("Canceled", ignoreCase = true) || passenger.status.equals("Payment Pending", ignoreCase = true) ) {
                                            if (passenger.gender == "0" || passenger.gender == "0.00") {
                                                tableData.add(
                                                    mutableListOf(
                                                        index.toString(),
                                                        passenger.paXName,
                                                        "Male",
                                                        passenger.seatNumber,
                                                        passenger.ticketNumber
                                                    )
                                                )
                                            } else {
                                                tableData.add(
                                                    mutableListOf(
                                                        index.toString(),
                                                        passenger.paXName,
                                                        "Female",
                                                        passenger.seatNumber,
                                                        passenger.ticketNumber
                                                    )
                                                )
                                            }
                                            index++
                                        }
                                    }


                                    CancelTicketList.set(position,CancelTicketDataItem(CancelTicketList[position].bookingRefNo,CancelTicketList[position].loginId,CancelTicketList[position].apiData,CancelTicketList[position].registrationID,CancelTicketList[position].statusdesc,CancelTicketList[position].statusCode,
                                        passangerList, droppingTime,boardingTime,fromCity,toCity,busOperatorName,travelType,travelDate,passangerQunatity,tableData,true))


                                    if(upcomingadapter!=null){
                                       upcomingadapter.updateList(CancelTicketList,position)
                                    }
                                    
                                }
                                else{
                                    Toast.makeText(context,response.returnMessage, Toast.LENGTH_SHORT).show()
                                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                        ConstantClass.dialog.dismiss()
                                    }
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.loading(requireContext())
                    }
                }
            }

        }

    }


    override fun refreshData(startDate: String, endDate: String) {
        // Call cancel API only
        if (!isAdded || !isViewReady) {
            ConstantClass.startDate = startDate
            ConstantClass.endDate = endDate
            return
        }
        hitApiForBusTicketCancelList(startDate, endDate)
    }


    override fun onResume() {
        super.onResume()

        val start = ConstantClass.startDate
        val end = ConstantClass.endDate

        if (!start.isNullOrEmpty() && !end.isNullOrEmpty()) {

            // Prevent duplicate API calls
            Log.d("CancelledBus", "Calling booking API from onResume")
            if (ConstantClass.startDate.isNotBlank() && ConstantClass.endDate.isNotBlank())
                hitApiForBusTicketCancelList(ConstantClass.startDate, ConstantClass.endDate)

        }

        hitApiForCheckActiveStatus()
    }


    fun hitApiForBusTicketCancelList(startDate: String, endDate: String){

        val busRequery = BusBookingListReq(
            loginID = mStash!!.getStringValue(ConstantClass.RegistrationId, ""),
            startDate = startDate,
            endDate = endDate
        )
        Log.d("BookingListReq", Gson().toJson(busRequery))
        viewModel.getBusCancelTicketRequest(busRequery).observe(viewLifecycleOwner) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        // pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }
                                Log.d("CanceledTicket", response.toString())
                                var dataItem = response.data
                                CancelTicketList.clear()
                                dataItem?.let { it1 ->
                                    BusCancelList.addAll(it1)
                                    CancelTicketList= BusCancelList
                                }
                                setRecyclerview()

                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                            ConstantClass.dialog.dismiss()
                        }
                        binding.notfounddatalayout.visibility = View.VISIBLE
                        binding.showingCancelList.visibility = View.GONE
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.loading(requireContext())
                    }
                }
            }
        }

    }

    fun hitApiForCheckActiveStatus() {
        var statusreq = CheckActiveStatusReq(
            userID =  mStash!!.getStringValue(ConstantClass.RegistrationId, "").toString()
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
                                ConstantClass.checkActiveStatusAndLogout(requireContext(), activeStatus, mStash!!)
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


}