package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ItemFlightShimmerBinding

class ShimmerAdapter(var context: Context) : RecyclerView.Adapter<ShimmerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding = ItemFlightShimmerBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 6 // number of shimmer items

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.shimmerLayout.startShimmer()
    }

    class ViewHolder(var binding: ItemFlightShimmerBinding) : RecyclerView.ViewHolder(binding.root)

}