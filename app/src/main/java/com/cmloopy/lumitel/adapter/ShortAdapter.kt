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

class ShortAdapter(private val context: Context, private var shortList: List<ShortVideo>): RecyclerView.Adapter<ShortAdapter.ShortViewHolder>() {
    inner class ShortViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        var like = itemView.findViewById<MaterialTextView>(R.id.txt_number_like)
        var cmt = itemView.findViewById<MaterialTextView>(R.id.txt_number_cmt)
        var share = itemView.findViewById<MaterialTextView>(R.id.txt_number_share)
        var player: ExoPlayer? = null
        fun bind(context: Context, video: ShortVideo){
            like.text = video.like.toString()
            cmt.text = video.cmt.toString()
            share.text = video.share.toString()

            val uri = Uri.parse("android.resource://${context.packageName}/${video.urlShort}")

            player = ExoPlayer.Builder(itemView.context).build()
            playerView.player = player
            val mediaItem = MediaItem.fromUri(uri)
            player?.setMediaItem(mediaItem)
            player?.repeatMode = Player.REPEAT_MODE_ALL
            player?.prepare()
            player?.playWhenReady = true
        }
        fun releasePlayer() {
            player?.release()
            player = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_short_activity,parent,false)
        return ShortViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shortList.size
    }

    override fun onBindViewHolder(holder: ShortViewHolder, position: Int) {
        val video = shortList[position]
        holder.bind(context,video)
    }

    fun updateData(newList: List<ShortVideo>) {
        shortList = newList
        notifyDataSetChanged()
    }
    override fun onViewDetachedFromWindow(holder: ShortViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.releasePlayer()
    }
}