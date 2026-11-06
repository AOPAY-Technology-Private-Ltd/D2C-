package com.example.twopayd2c.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.CancelledRefundBus
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment.UpcomingBus


class ViewPagerAdapter(fm: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.add(UpcomingBus())
        fragments.add(CancelledRefundBus())
    }


    override fun getItemCount(): Int {
        return fragments.size
    }



    override fun createFragment(position: Int): Fragment {
        return fragments[position]

        }


    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }


}
