package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.utils.TimeFormat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class VideoChannelAdapter(private var listVideo: List<Video>, private val onClick: (Int) -> Unit): RecyclerView.Adapter<VideoChannelAdapter.VideoChannelViewHolder>() {
    inner class VideoChannelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val videoText = itemView.findViewById<MaterialTextView>(R.id.video_title)
        val img = itemView.findViewById<ShapeableImageView>(R.id.img_video_length_image)
        val duration = itemView.findViewById<MaterialTextView>(R.id.textDuration)
        fun bind(video:Video){
            videoText.text = video.videoTitle
            Glide.with(itemView.context).load(video.videoImage).into(img)
            duration.text = TimeFormat.formatTime(video.videoTime)

            itemView.setOnClickListener {
                onClick(video.id)
            }
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