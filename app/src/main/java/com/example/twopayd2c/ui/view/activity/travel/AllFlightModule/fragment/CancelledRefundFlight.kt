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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.FragmentCancelledFlightBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.AirTicketCancelAdapter
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson


class CancelledRefundFlight : Fragment() {
    lateinit var binding: FragmentCancelledFlightBinding
    private var preference: SharedPreference? = null
    private lateinit var viewModel: TravelViewModel
    lateinit var upcominadapter : AirTicketCancelAdapter


    companion object{
        var AirCancelTicketList : MutableList<com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.DataItem> = mutableListOf()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentCancelledFlightBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        preference = SharedPreference.getInstance(requireContext())
        setDataInList()

        return binding.root
    }


    fun setDataInList(){
        if(AirCancelTicketList !=null && AirCancelTicketList.size>0){
            binding.notfounddatalayout.visibility = View.GONE
            binding.showtickets.visibility = View.VISIBLE
            upcominadapter = AirTicketCancelAdapter(requireContext(), AirCancelTicketList,this)
            binding.showtickets.adapter = upcominadapter
            upcominadapter.notifyDataSetChanged()
        }else{
            binding.notfounddatalayout.visibility = View.VISIBLE
            binding.showtickets.visibility = View.GONE
        }

    }



}