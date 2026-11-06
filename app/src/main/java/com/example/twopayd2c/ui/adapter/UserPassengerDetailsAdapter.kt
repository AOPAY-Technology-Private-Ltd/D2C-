package com.example.twopayd2c.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.PaXDetails
import com.example.twopayd2c.databinding.BookedpassengerItemlayoutBinding
import com.example.twopayd2c.databinding.BookingItemBinding

class UserPassengerDetailsAdapter(private val context: Context, private val dateList: List<PaXDetails>) : RecyclerView.Adapter<UserPassengerDetailsAdapter.DateViewHolder>() {
    val colors = listOf(
        Color.parseColor("#FFCDD2"), // Light Red
        Color.parseColor("#C8E6C9"), // Light Green
        Color.parseColor("#BBDEFB"), // Light Blue
        Color.parseColor("#FFF9C4"), // Light Yellow
        Color.parseColor("#D1C4E9")  // Light Purple
    )

    inner class DateViewHolder(val bin: BookedpassengerItemlayoutBinding) : RecyclerView.ViewHolder(bin.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = BookedpassengerItemlayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return DateViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val dateModel = dateList[position]
        val randomColor = colors.random()
        holder.bin.cardnamefirstname.setCardBackgroundColor(randomColor)
        holder.bin.charactername.text = dateModel!!.paXName!!.take(2).uppercase()


        holder.bin.passengerName.text = dateModel.title.toString()+". " + dateModel.paXName.toString()
        when {
            dateModel.gender.toString() == "0" -> holder.bin.passengerdetails.text =
                "${dateModel.age.toString()} | Male | ${dateModel.ticketNumber.toString()}"
            dateModel.gender.toString() == "1" -> holder.bin.passengerdetails.text =
                "${dateModel.age.toString()} | Female | ${dateModel.ticketNumber.toString()}"
        }

        holder.bin.seatnumber.text = dateModel.seatNumber.toString()

    }

    override fun getItemCount(): Int = dateList.size
}