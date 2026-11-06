package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryDataItem
import com.example.twopayd2c.data.model.travelmodel.flight.DataItem
import com.example.twopayd2c.databinding.AirportNameListLayoutBinding
import com.example.twopayd2c.databinding.TransactionHistoryItemBinding
import com.example.twopayd2c.ui.view.activity.transactionhistory.TransactionHistoryDetailsPage
import com.example.twopayd2c.ui.view.activity.transactionhistory.TransactionHistoryDetailsPage.Companion.transactionHistoryDataItem
import com.example.twopayd2c.utils.ConstantClass.Companion.transactionformatDate
import com.example.twopayd2c.utils.QRCodeGenerator.formatDate


class TransactionHistoryListAdapter (private val context: Context, private var transactionList: MutableList<TransactionHistoryDataItem?>?):
    RecyclerView.Adapter<TransactionHistoryListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = TransactionHistoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }



    override fun getItemCount(): Int {
       return transactionList!!.size
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind.remarks.text = transactionList!!.get(position)?.remark
        val regex = Regex("""[a-zA-Z0-9.\-_]+@[a-zA-Z]+""")
        val upiId = transactionList!!.get(position)?.transferFromMsg?.let { regex.find(it) }?.value
        holder.bind.refid.text = "REF ID ${transactionList!!.get(position)?.amtTransferTransID}"

        if(upiId.isNullOrEmpty()){
            holder.bind.transactionid.visibility= View.GONE
        }else {
            holder.bind.transactionid.visibility= View.VISIBLE
            holder.bind.transactionid.text = upiId
        }

        if(transactionList!!.get(position)?.ledgerType?.lowercase().equals("spend",true)){
            holder.bind.checkImageView.setImageResource(R.drawable.payouticon)
            holder.bind.amount.text = " - ₹${transactionList!!.get(position)?.transferAmt.toString()}"
            holder.bind.amount.setTextColor(context.getColor(R.color.red))
        }
        else
        {
            holder.bind.checkImageView.setImageResource(R.drawable.payinicon)
            holder.bind.amount.text = "+ ₹${transactionList!!.get(position)?.transferAmt.toString()}"
            holder.bind.amount.setTextColor(context.getColor(R.color.green))
        }

        val formatted = transactionformatDate(transactionList!!.get(position)?.recordDateTime!!)
        holder.bind.date.text = formatted

        holder.itemView.setOnClickListener {
            var intent = Intent(context, TransactionHistoryDetailsPage::class.java)
            transactionHistoryDataItem = transactionList!!.get(position)!!
            context.startActivity(intent)
        }


    }



    class ViewHolder(var bind: TransactionHistoryItemBinding) : RecyclerView.ViewHolder(bind.root)



}