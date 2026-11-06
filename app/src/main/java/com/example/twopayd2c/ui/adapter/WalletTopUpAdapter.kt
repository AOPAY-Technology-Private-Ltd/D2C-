package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.AirportNameListLayoutBinding
import com.example.twopayd2c.databinding.WalletRecommendedAmountItemLayoutBinding
import com.example.twopayd2c.ui.adapter.AirPortListAdapter.ViewHolder
import com.example.twopayd2c.ui.view.activity.wallet.WalletTopUpPage
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference

class WalletTopUpAdapter(var context: Context, var amountlist: MutableList<String>, var listner: ClickListner, var preference: SharedPreference) : RecyclerView.Adapter<WalletTopUpAdapter.ViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletTopUpAdapter.ViewHolder {
        val bin = WalletRecommendedAmountItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }


    override fun onBindViewHolder(holder: WalletTopUpAdapter.ViewHolder, position: Int) {
        if (position == 0 || position == 1) {
            holder.bind.enterAmount.text = " ₹${amountlist[position]}"
        } else {
            holder.bind.enterAmount.text = "+ ₹${amountlist[position]}"
        }

        val isSelected = selectedPosition == position
        val isRecommended = position == 1
        val isWalletClicked = preference.getBoolanValue(ConstantClass.WalletAmountClick, false)

        //  Handle Recommended Layout
        holder.bind.recommendedlayout.visibility = if (isRecommended && !isWalletClicked) View.VISIBLE else View.INVISIBLE

        //  Handle Background
        val backgroundRes = when {
            isSelected -> R.drawable.selectedfrombox
            isRecommended && !isWalletClicked -> R.drawable.buttonshape
            else -> R.drawable.walletborder
        }
        holder.bind.amountlayout.setBackgroundResource(backgroundRes)

       //  Handle Text Color
        val textColor = when {
            isRecommended && !isWalletClicked -> R.color.white
            else -> R.color.primary_blue
        }
        holder.bind.enterAmount.setTextColor(ContextCompat.getColor(context, textColor))

        //  Click Handling
        holder.itemView.setOnClickListener {
            selectedPosition = position
            listner.itemclickListner(position, amountlist[position])
            notifyDataSetChanged()
        }

    }


    override fun getItemCount(): Int {
        return amountlist.size
    }


    class ViewHolder(var bind: WalletRecommendedAmountItemLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {

    }

    interface ClickListner{
        fun itemclickListner(position:Int, amount: String)
    }

}