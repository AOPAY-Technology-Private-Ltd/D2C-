package com.example.twopayd2c.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.data.model.VideoItemModel
import com.example.twopayd2c.databinding.VideolayoutBinding
import com.example.twopayd2c.utils.ConstantClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoAdapter(private val context: Context, private val videoList: List<VideoItemModel>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var currentPlayingPosition = RecyclerView.NO_POSITION
    private var currentBinding: VideolayoutBinding? = null

    inner class VideoViewHolder(val binding: VideolayoutBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val videoUri = Uri.parse("android.resource://${context.packageName}/${videoList[adapterPosition].videoResId}")
                    listener.onItemClick(adapterPosition,videoUri)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideolayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoUri = Uri.parse("android.resource://${context.packageName}/${videoList[position].videoResId}")

        // 1. Initial State: Show thumbnail, hide/stop video
        holder.binding.thumbnail.visibility = View.VISIBLE
        holder.binding.thumbnail.alpha = 1f
        holder.binding.videoview.visibility = View.INVISIBLE
        holder.binding.videoview.setVideoURI(videoUri)
        holder.binding.videotext.text = videoList[position].videotextmsg

        // 2. Load Thumbnail
        CoroutineScope(Dispatchers.IO).launch {
            val thumbnail = ConstantClass.getVideoThumbnail(videoUri, context)
            withContext(Dispatchers.Main) {
                if (holder.adapterPosition == position) {
                    holder.binding.thumbnail.setImageBitmap(thumbnail)
                }
            }
        }


    }





    override fun getItemCount(): Int = videoList.size




    interface OnItemClickListener {
        fun onItemClick(position: Int,uri:Uri)
    }
}