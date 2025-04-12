package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.google.android.material.imageview.ShapeableImageView

class VideoChannelAdapter(private var listVideo: List<Video>): RecyclerView.Adapter<VideoChannelAdapter.VideoChannelViewHolder>() {
    inner class VideoChannelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ShapeableImageView>(R.id.img_video_length_image)
        fun bind(video:Video){
            val resId: Int = itemView.context.getResources().getIdentifier(video.videoImage, "drawable", itemView.context.getPackageName())
            img.setImageResource(resId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_channel,parent,false)
        return VideoChannelViewHolder((view))
    }

    override fun getItemCount(): Int {
        return listVideo.size
    }

    override fun onBindViewHolder(holder: VideoChannelViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }
}