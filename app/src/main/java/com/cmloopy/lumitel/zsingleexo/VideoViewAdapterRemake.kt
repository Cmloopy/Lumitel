package com.cmloopy.lumitel.zsingleexo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video

class VideoViewAdapterRemake(private var listVideo: List<Video>):RecyclerView.Adapter<VideoViewAdapterRemake.VideoViewHolder>(){
    inner class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)

        fun bind(video: Video){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_activity, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listVideo.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }
}