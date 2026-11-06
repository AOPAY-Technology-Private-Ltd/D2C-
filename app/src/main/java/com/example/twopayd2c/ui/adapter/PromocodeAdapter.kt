package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.ServicesListModel
import com.example.twopayd2c.data.model.travelmodel.bus.promocode.PromocodeDataItem
import com.example.twopayd2c.databinding.OfferPromocodeLayoutBinding
import com.example.twopayd2c.databinding.TravelServiceItemLayoutBinding
import com.example.twopayd2c.ui.view.activity.travel.TravelBookingHomePage
import com.example.twopayd2c.ui.view.activity.travel.fragment.BusSearchHomePageFragment
import com.example.twopayd2c.ui.view.activity.travel.fragment.FlightSearchHomePageFragment
import com.example.twopayd2c.ui.view.activity.travel.fragment.TrainSearchHomePageFragment
import kotlin.math.min


class PromocodeAdapter(var servicesList: List<PromocodeDataItem?>?, var context: Context, private val activity: AppCompatActivity,var ticketAmount: Double, var listner:OnClickListner) : RecyclerView.Adapter<PromocodeAdapter.ViewHolder>(){

    class ViewHolder (var binding : OfferPromocodeLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    var check = false
    var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = OfferPromocodeLayoutBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(bin)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = servicesList!![position]!!

        val isEligible = ticketAmount >= item.minTransactionAmount!!
        // 🎨 Change UI based on eligibility
        holder.binding.root.alpha = if (isEligible) 1f else 0.6f
        holder.binding.radiobutton.isEnabled = isEligible
        holder.itemView.isClickable = isEligible
        holder.binding.details.isEnabled = isEligible

        holder.binding.checkeligibility.visibility =
            if (isEligible) View.GONE else View.VISIBLE

        holder.binding.checkeligibility.text= "${context.resources.getString(R.string.this_promo_code)} ${item.minTransactionAmount}"

        holder.binding.promocode.text= servicesList!![position]!!.promoCode
        holder.binding.subdescriptionPromotxt.text= servicesList!![position]!!.description
        holder.binding.type.text= servicesList!![position]!!.offerType

        val offerText = when (servicesList!![position]?.discountType!!.lowercase()) {
            "flat" -> "Flat Off ₹ ${servicesList!![position]?.maxDiscountAmount} "
            "percentage" -> "UP to ₹ ${servicesList!![position]?.maxDiscountAmount}"
            else -> "-"
        }
       holder.binding.offerdiscount.text = offerText

       val saveAmount = when(servicesList!![position]?.discountType!!.lowercase()) {
           "percentage" -> {
               val calculatedAmount = (ticketAmount * item.discountValue!!) / 100
               minOf(calculatedAmount.toInt(), item.maxDiscountAmount!!)
           }

           "flat" -> {
               minOf(item.discountValue!!, item.maxDiscountAmount!!)
           }

           else -> 0.00
       }


        holder.binding.saveamount.text= "Save ₹${saveAmount}"

        // ✅ Expand / Collapse
        holder.binding.subdescriptionPromotxt.visibility =
            if (check) View.VISIBLE else View.GONE

        holder.binding.details.text =
            if (check) "Hide" else "Details"

        holder.itemView.setOnClickListener {
            if (!isEligible) return@setOnClickListener
            check = !check
            notifyItemChanged(position)
        }

        // ✅ Radio Selection
        holder.binding.radiobutton.isChecked = position == selectedPosition


        holder.binding.radiobutton.setOnClickListener {
            if (!isEligible) return@setOnClickListener
            val previousPosition = selectedPosition
            selectedPosition = position
            listner.promoClickListner(saveAmount.toInt(),servicesList!![position]!!.promoCode)
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }



    }

    fun removeSelection(){
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
      return  servicesList!!.size
    }


    interface OnClickListner{
        fun promoClickListner(saveamount: Int,code:String?)
    }



}