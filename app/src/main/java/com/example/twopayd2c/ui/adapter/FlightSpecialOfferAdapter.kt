package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.SpecialOfferItemlayoutBinding


class FlightSpecialOfferAdapter (private val context: Context, private val offerList: MutableList<Pair<String,Boolean>>,val btnlistener: setClickListner):
    RecyclerView.Adapter<FlightSpecialOfferAdapter.ViewHolder>() {


    companion object {
        var mClickListener: setClickListner? = null
        var selection = -1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bin = SpecialOfferItemlayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bin)
    }



    override fun getItemCount(): Int {
       return offerList.size
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mClickListener = btnlistener
        holder.offerTypeName.text= offerList.get(position).first
        if(selection == position){
            holder.mainlayout.background=context.resources.getDrawable(R.drawable.bg_button_border_blue)
            holder.offerTypeName.setTextColor(context.getColor(R.color.primary_blue))
        }
        else{
            holder.mainlayout.background=context.resources.getDrawable(R.drawable.editouterborder)
            holder.offerTypeName.setTextColor(context.getColor(R.color.edtblack))
        }

        holder.itemView.setOnClickListener {
            if(mClickListener!=null){
                mClickListener!!.itemclickListner(position)
            }
            selection=position
            notifyDataSetChanged()
        }
    }



    class ViewHolder(bind: SpecialOfferItemlayoutBinding) : RecyclerView.ViewHolder(bind.root) {
        var offerTypeName = bind.categoryName
        var offertxt = bind.offerstxt
        var mainlayout = bind.mainlayout
    }

   open interface setClickListner{
        fun itemclickListner(position:Int)
    }

}