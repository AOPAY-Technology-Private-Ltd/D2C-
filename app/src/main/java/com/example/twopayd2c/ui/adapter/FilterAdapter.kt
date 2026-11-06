package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.FilterItem
import com.example.twopayd2c.databinding.FilterItemLayoutBinding


class FilterAdapter(var context: Context, var filterList:List<FilterItem>,var listner:setOnItemCheckListner): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {


    class ViewHolder(val binding:FilterItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FilterAdapter.ViewHolder {
        val binding = FilterItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        val item = filterList[position]

        // Remove old listener (important for RecyclerView reuse)
        holder.binding.loadcheckbox.setOnCheckedChangeListener(null)

        holder.binding.loadcheckbox.text = item.name
        holder.binding.loadcheckbox.isChecked = item.isSelected


        holder.binding.loadcheckbox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
            val selectedTypes = filterList
                .filter { it.isSelected }
                .map { it.name.lowercase() }
            listner.checkItemClick(selectedTypes, isChecked)
            notifyDataSetChanged()
        }


    }


    override fun getItemCount(): Int {
        return filterList.size
    }


    interface setOnItemCheckListner{
        fun checkItemClick(filteritem: List<String>, isChecked:Boolean)
    }


}