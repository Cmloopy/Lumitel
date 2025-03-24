package com.cmloopy.lumitel.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.ShortVideo
import com.google.android.material.textview.MaterialTextView

class ShortAdapter(private val context: Context, private var shortList: List<ShortVideo>) :
    RecyclerView.Adapter<ShortAdapter.ShortViewHolder>() {

    private var currentPlayingViewHolder: ShortViewHolder? = null

    inner class ShortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        var like: MaterialTextView = itemView.findViewById(R.id.txt_number_like)
        var cmt: MaterialTextView = itemView.findViewById(R.id.txt_number_cmt)
        var share: MaterialTextView = itemView.findViewById(R.id.txt_number_share)
        var player: ExoPlayer? = null

        fun bind(context: Context, video: ShortVideo) {
            like.text = video.like.toString()
            cmt.text = video.cmt.toString()
            share.text = video.share.toString()

            val uri = Uri.parse("android.resource://${context.packageName}/${video.urlShort}")

            player = ExoPlayer.Builder(itemView.context).build().apply {
                setMediaItem(MediaItem.fromUri(uri))
                repeatMode = Player.REPEAT_MODE_ALL
                prepare()
                playWhenReady = false
            }
            playerView.player = player
        }

        fun playVideo() {
            player?.playWhenReady = true
        }

        fun stopVideo() {
            player?.playWhenReady = false
        }
        fun releasePlayer() {
            player?.release()
            player = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_short_activity, parent, false)
        return ShortViewHolder(view)
    }

    override fun getItemCount(): Int = shortList.size

    override fun onBindViewHolder(holder: ShortViewHolder, position: Int) {
        holder.bind(context, shortList[position])
    }

    override fun onViewAttachedToWindow(holder: ShortViewHolder) {
        super.onViewAttachedToWindow(holder)
        currentPlayingViewHolder?.stopVideo()  // Dừng video trước đó nếu có
        currentPlayingViewHolder = holder
        holder.playVideo()  // Chỉ phát video khi ViewHolder này được gắn vào màn hình
    }

    override fun onViewDetachedFromWindow(holder: ShortViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopVideo()  // Dừng phát video khi ViewHolder bị cuộn ra khỏi màn hình
    }

    fun updateData(newList: List<ShortVideo>) {
        shortList = newList
        notifyDataSetChanged()
    }
    fun releaseVideo() {
        currentPlayingViewHolder?.releasePlayer()
        currentPlayingViewHolder = null
    }
    fun pauseVideo(){
        currentPlayingViewHolder?.stopVideo()
    }
    fun resumeVideo(){
        currentPlayingViewHolder?.playVideo()
    }

}
