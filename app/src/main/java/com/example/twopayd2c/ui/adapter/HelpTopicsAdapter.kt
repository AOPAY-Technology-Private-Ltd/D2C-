package com.example.twopayd2c.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.HelpPointsModel
import com.example.twopayd2c.databinding.HelpTopicItemlayoutBinding


class HelpTopicsAdapter(private val context: Context, private val helppointslist: List<HelpPointsModel>) : RecyclerView.Adapter<HelpTopicsAdapter.VideoViewHolder>() {

        var currentposition = RecyclerView.NO_POSITION

    inner class VideoViewHolder(val binding: HelpTopicItemlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = HelpTopicItemlayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
     holder.binding.maintitle.text= "${helppointslist[position].title}"
     holder.binding.subtitle.text= "${helppointslist[position].subTitle}"

        if(position==currentposition){
            holder.binding.subtitle.visibility= View.VISIBLE
            holder.binding.downupimageview.setImageResource(R.drawable.upbuttonicon)
        }else{
            holder.binding.subtitle.visibility= View.GONE
            holder.binding.downupimageview.setImageResource(R.drawable.downbuttonicon)
        }


        holder.binding.cardlayout.setOnClickListener {

            currentposition = if (currentposition == position) {
                RecyclerView.NO_POSITION   // collapse
            } else {
                position                  // expand
            }

            notifyDataSetChanged()
        }


    }





    override fun getItemCount(): Int = helppointslist.size


}