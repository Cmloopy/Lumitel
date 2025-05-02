package com.cmloopy.lumitel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.adapter.ShortAdapter
import com.cmloopy.lumitel.data.models.video.Video
import com.google.android.material.textview.MaterialTextView

class VideoViewAdapter(private var listVideo: List<Video>) :
    RecyclerView.Adapter<VideoViewAdapter.VideoViewHolder>() {

    private val playersMap = mutableMapOf<Int, ExoPlayer>()
    private var currentPlayingViewHolder: VideoViewHolder? = null
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        private var exoPlayer: ExoPlayer? = null
        fun bind(video: Video, position: Int) {
            exoPlayer = ExoPlayer.Builder(itemView.context).build().apply {
                setMediaItem(MediaItem.fromUri(video.videoMedia))
                prepare()
                repeatMode = Player.REPEAT_MODE_ALL
                playWhenReady = false
            }
            playerView.player = exoPlayer
            playersMap[position] = exoPlayer!!
        }
        fun onAttached() {
            exoPlayer?.playWhenReady = true
        }
        fun onDetached() {
            exoPlayer?.pause()
        }
        fun resume(){
            exoPlayer?.play()
        }
        fun releasePlayer(position: Int) {
            playersMap[position]?.let { player ->
                player.stop()
                player.release()
            }
            playersMap.remove(position)
            playerView.player = null
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_activity, parent, false)
        return VideoViewHolder(view)
    }
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listVideo[position], position)
    }
    override fun getItemCount(): Int = listVideo.size
    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
        currentPlayingViewHolder = holder
    }
    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetached()
    }
    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        val position = holder.bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            holder.releasePlayer(position)
        }
    }
    fun stopVideo(){
        currentPlayingViewHolder?.onDetached()
    }
    fun resumeVideo(){
        currentPlayingViewHolder?.resume()
    }
    fun updateData(newList: List<Video>) {
        listVideo = newList
        notifyDataSetChanged()
    }
}

