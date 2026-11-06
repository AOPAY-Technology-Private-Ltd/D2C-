package com.example.twopayd2c.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.CurrentdateWithnextdateItemlayoutBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CurrentWithNextDateAdapter(private val dateList: MutableList<String>,var context: Context, private val listener:onItemClickListner) : RecyclerView.Adapter<CurrentWithNextDateAdapter.ViewHolder>() {
    var selectedPosition:Int = Adapter.NO_SELECTION

   inner class ViewHolder(var binding: CurrentdateWithnextdateItemlayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CurrentdateWithnextdateItemlayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = LocalDate.parse(dateList[position])
        val displayFormat = DateTimeFormatter.ofPattern("EEE , dd")
        var currentDateWithDay = date.format(displayFormat).split(",")
        Log.d("CurrentDateAdapter", currentDateWithDay[0])
        holder.binding.daytxt.text= currentDateWithDay[0]
        holder.binding.datetxt.text= currentDateWithDay[1]

        if(selectedPosition==position){
            holder.binding.daydatecard.setCardBackgroundColor(context.resources.getColor(R.color.primary_blue))
            holder.binding.daytxt.setTextColor(context.resources.getColor(R.color.white))
            holder.binding.datetxt.setTextColor(context.resources.getColor(R.color.white))
        }
        else {
            holder.binding.daydatecard.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.binding.daytxt.setTextColor(context.resources.getColor(R.color.edtblack))
            holder.binding.datetxt.setTextColor(context.resources.getColor(R.color.edtblack))
        }

        holder.binding.daydatecard.setOnClickListener {
            selectedPosition=position
            listener.onItemClick(selectedPosition)
            notifyDataSetChanged()
        }
    }


    fun updateViewSelection(position: Int) {
        if (position != -1) {
            selectedPosition = position
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int = dateList.size


    interface onItemClickListner{
        fun onItemClick(position: Int)
    }


}