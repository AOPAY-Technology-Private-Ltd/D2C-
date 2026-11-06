package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.FilterItem
import com.example.twopayd2c.databinding.DatefilterItemlayoutBinding
import com.example.twopayd2c.databinding.FilterItemLayoutBinding


class DateFilterAdapter(var context: Context, var filterList:List<FilterItem>, var listner:setOnItemCheckListner): RecyclerView.Adapter<DateFilterAdapter.ViewHolder>() {
    private var selectedPosition = -1

    class ViewHolder(val binding: DatefilterItemlayoutBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DateFilterAdapter.ViewHolder {
        val binding = DatefilterItemlayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DateFilterAdapter.ViewHolder, position: Int) {
        val item = filterList[position]
        holder.binding.radioButton.text = item.name
        holder.binding.radioButton.isChecked = position == selectedPosition

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            // update only changed items
            if (previousPosition != -1) notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }

    }


    override fun getItemCount(): Int {
        return filterList.size
    }


    interface setOnItemCheckListner{
        fun checkItemClick(filteritem: List<String>, isChecked:Boolean)
    }


}