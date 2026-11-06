package com.example.twopayd2c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BoardingDropingModel
import com.example.twopayd2c.adapter.BannerAdapter
import com.example.twopayd2c.databinding.PickupDropupItemlayoutBinding
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference


class PickUpDropListAdapter(var preference: SharedPreference,var context: Context,private val pickdroplist: List<BoardingDropingModel>,var type:String, var listner : onClickListner) : RecyclerView.Adapter<PickUpDropListAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION


    class ViewHolder(var bind: PickupDropupItemlayoutBinding) : RecyclerView.ViewHolder(bind.root.rootView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickUpDropListAdapter.ViewHolder {
       var view = PickupDropupItemlayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: PickUpDropListAdapter.ViewHolder, position: Int) {
        holder.bind.timetxt.text = pickdroplist[position].boardingTime
        holder.bind.boardingdropingname.text = pickdroplist[position].boarddropname

        if(selectedPosition == position) {
            holder.bind.malecheck.isChecked = true
        }else{
            holder.bind.malecheck.isChecked = false
        }

        holder.itemView.setOnClickListener {
            selectedPosition = position
            listner.onClick(pickdroplist[position],type)
            notifyDataSetChanged()
        }

        holder.bind.malecheck.setOnClickListener {
            selectedPosition = position
            listner.onClick(pickdroplist[position],type)
            notifyDataSetChanged()
        }

    }

    fun updateCheckPosition(position: Int){
        selectedPosition = position
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
       return pickdroplist.size
    }


    interface onClickListner{

        fun onClick(item :BoardingDropingModel,type:String )

    }


}