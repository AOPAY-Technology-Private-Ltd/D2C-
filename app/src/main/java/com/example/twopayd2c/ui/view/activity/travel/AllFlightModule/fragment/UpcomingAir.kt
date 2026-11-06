package com.bos.bos.app.ui.view.travel.airfragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.AirTicketListResp
import com.example.twopayd2c.data.model.travel.flight.FlightRequeryReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirReprintReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketCancelDetails
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketCancelReq
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.FragmentUpcomingAirBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.AirTicketUpcomingAdapter
import com.example.twopayd2c.ui.adapter.AirTicketUpcomingAdapter.Companion.paxList
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightBookedTicketActivity.Companion.BookedTicketList
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightBookingPage
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson
import kotlin.concurrent.thread


class UpcomingAir : Fragment() {
    lateinit var binding: FragmentUpcomingAirBinding
    private var preference: SharedPreference? = null
    private lateinit var viewModel: TravelViewModel
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    lateinit var upcominadapter : AirTicketUpcomingAdapter
    var checkSelectedPassangerList : MutableList<Int> = mutableListOf()
    var selectedCardPosition:Int = 0


    companion object{
        var AirBookingTicketList : MutableList<com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.DataItem> = mutableListOf()
        var AirUpcomingTicketList : MutableList<com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.DataItem> = mutableListOf()
        lateinit var BookingListResponse : AirTicketListResp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentUpcomingAirBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        getAllApiServiceViewModel = ViewModelProvider( this, GetAllApiServiceViewModelFactory(
            GetAllAPIServiceRepository(ApiClient.apiAllInterface)
        ))[GetAllApiServiceViewModel::class.java]

        preference = SharedPreference.getInstance(requireContext())


        setDataInList()

        return binding.root

    }



    fun setDataInList(){
        if(AirUpcomingTicketList!=null && AirUpcomingTicketList.size>0){
            upcominadapter = AirTicketUpcomingAdapter(requireContext(), AirUpcomingTicketList,this)
            binding.showtickets.adapter = upcominadapter
            binding.notfounddatalayout.visibility = View.GONE
            binding.showtickets.visibility = View.VISIBLE
            upcominadapter.notifyDataSetChanged()
        }
        else{
            binding.notfounddatalayout.visibility = View.VISIBLE
            binding.showtickets.visibility = View.GONE
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun hitApiForCancelAirTicket(selectionList:MutableList<Int>, position:Int){
        checkSelectedPassangerList= selectionList
        selectedCardPosition= position

        var airticketCancelReq = AirTicketCancelReq(
            ticketCancelDetailsList=getCancelTicketList(position),
            airlinePNR = AirUpcomingTicketList.get(position).airPNR,
            refNo = AirUpcomingTicketList.get(position).bookingRefNo,
            cancelCode = "005",
            reqRemarks = "Change in travel plans",
            cancellationType = 0,
            ipAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId =  preference!!.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber,""),
            registrationId = preference?.getStringValue(ConstantClass.MerchantId, "")
        )
        Log.d("ticketcancelreq", Gson().toJson(airticketCancelReq))

        viewModel.getAirTicketCancelRequest(airticketCancelReq)
            .observe(viewLifecycleOwner) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    if(response.responseHeader.errorCode.equals("0000")){
                                        Toast.makeText(requireContext(),"Ticket cancel successfully", Toast.LENGTH_SHORT).show()

                                        val airTicketReprintreq = AirReprintReq(
                                            BookingRefNo = AirUpcomingTicketList.get(position).bookingRefNo,
                                            airlinePNR =  AirUpcomingTicketList.get(position).airPNR,
                                            ipAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
                                            requestId =  preference?.getStringValue(ConstantClass.requestId, ""),
                                            imeNumber = preference?.getStringValue(ConstantClass.imeINumber,""),
                                            registerId = preference?.getStringValue(ConstantClass.MerchantId, "")
                                        )

                                        hitApiForTicketReprint(airTicketReprintreq)

                                    }
                                    else{
                                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                            ConstantClass.dialog.dismiss()
                                        }
                                        Toast.makeText(requireContext(),response.responseHeader.errorInnerException, Toast.LENGTH_SHORT).show()
                                    }

                                    Log.d("ticketcancelresp",Gson().toJson(response))

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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCancelTicketList(position: Int): List<AirTicketCancelDetails>? {
        val ticketDetailsList = mutableListOf<AirTicketCancelDetails>()

            var flightId=AirUpcomingTicketList.get(position).apiData.airPNRDetails!![0].flights!![0].flightId

            for (i in 0 until checkSelectedPassangerList.size){
                var paxId = paxList.get(checkSelectedPassangerList.get(i)).paxId

                ticketDetailsList.add(AirTicketCancelDetails(
                        flightId  = flightId,
                        passangerId =  paxId,
                        segmentId ="0"
                    )
                )
            }

           return ticketDetailsList
    }


    fun hitApiForRequeryRequest(requeryReq : FlightRequeryReq){
        getAllApiServiceViewModel.getAirRequeryRequest(requeryReq)
            .observe(viewLifecycleOwner) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    Log.d("ticketrequeryresp",Gson().toJson(response))
                                    val mainActivity = requireActivity() as FlightBookingPage

                                    if(mainActivity !=null){
                                        mainActivity.hitApiForFlightTicketList()
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

                        }

                    }
                }
            }
    }


    fun hitApiForTicketReprint(airReprintTicketReq: AirReprintReq){

        Log.d("ticketreprintreq", Gson().toJson(airReprintTicketReq))

        viewModel.getAirTicketReprintRequest(airReprintTicketReq)
            .observe(viewLifecycleOwner) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    BookedTicketList =response

                                    var requeryReq = FlightRequeryReq(
                                        loginId = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                                        bookingRefNo = response.bookingRefNo,
                                        ipAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
                                        requestId = preference?.getStringValue(ConstantClass.requestId, ""),
                                        imeinumber = "2232323232323",
                                        registrationID = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                                        airPnr = response.airPNRDetails!![0]?.airlinePNR,
                                        flightNumber = response.airPNRDetails[0]!!.flights!![0].flightId, //flight_Id
                                        travelDate = response.airPNRDetails[0]!!.flights!![0].travelDate,
                                        ticketStatusId = response.airPNRDetails[0]!!.ticketStatusId,
                                        ticketStatusDesc = response.airPNRDetails[0]!!.ticketStatusDesc,
                                        apiResponse = Gson().toJson(response),
                                        createdBy = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                                        destination = response.airPNRDetails[0]!!.flights!![0].destination,
                                        origin = response.airPNRDetails[0]!!.flights!![0].origin
                                    )

                                    Log.d("ticketrequeryreq",Gson().toJson(requeryReq))
                                    hitApiForRequeryRequest(requeryReq)
                                    Log.d("ticketreprintresponse",response.bookingRefNo)

                                }
                            }
                        }

                        ApiStatus.ERROR -> {
                            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                ConstantClass.dialog.dismiss()
                            }
                        }

                        ApiStatus.LOADING -> {

                        }

                    }
                }
            }
        }


}