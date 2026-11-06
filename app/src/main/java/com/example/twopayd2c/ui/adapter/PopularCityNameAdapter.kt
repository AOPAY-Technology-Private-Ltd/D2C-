package com.example.twopayd2c.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.PopularCityNameListModel
import com.example.twopayd2c.databinding.PopularcityItemLayoutBinding


class PopularCityNameAdapter(private val context: Context, private val popularCityNamelist: List<PopularCityNameListModel>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PopularCityNameAdapter.VideoViewHolder>() {


    inner class VideoViewHolder(val binding: PopularcityItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = PopularcityItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
     holder.binding.cityIcon.setImageResource(popularCityNamelist[position].image)
     holder.binding.cityName.text= "${popularCityNamelist[position].tocity}"

    }


    override fun getItemCount(): Int = popularCityNamelist.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}