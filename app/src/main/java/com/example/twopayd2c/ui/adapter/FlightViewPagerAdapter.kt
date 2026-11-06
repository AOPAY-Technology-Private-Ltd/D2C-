package com.example.twopayd2c.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bos.bos.app.ui.view.travel.airfragment.CancelledRefundFlight
import com.bos.bos.app.ui.view.travel.airfragment.CompletedAir
import com.bos.bos.app.ui.view.travel.airfragment.UpcomingAir
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.UpcomingBus

private const val NUM_TABS = 3

class FlightViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {


    override fun getItemCount(): Int {
        return NUM_TABS
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UpcomingAir()
            1-> CancelledRefundFlight()
            2 -> CompletedAir()
            else -> UpcomingBus()
        }



    }


}