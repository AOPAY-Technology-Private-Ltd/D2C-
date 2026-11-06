package com.example.twopayd2c.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.RecentSearchListModel
import com.example.twopayd2c.databinding.RecentBusSearchLayoutBinding


class RecentBusSearchAdapter(private val recentsearchlist: List<RecentSearchListModel>,private var listner: onItemClickListner) : RecyclerView.Adapter<RecentBusSearchAdapter.BannerViewHolder>() {

    class BannerViewHolder(var binding : RecentBusSearchLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = RecentBusSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(view)
    }


    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
       holder.binding.Fromcityname.text = recentsearchlist[position].fromcity
       holder.binding.Tocityname.text = recentsearchlist[position].tocity
       holder.binding.searchdate.text = recentsearchlist[position].searchdate
       holder.itemView.setOnClickListener {
           listner.recentItemClick(recentsearchlist[position])
       }
    }

    override fun getItemCount(): Int = recentsearchlist.size

    interface onItemClickListner{
        fun recentItemClick( recentsearchdata:RecentSearchListModel)
    }
}