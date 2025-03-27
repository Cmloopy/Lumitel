package com.cmloopy.lumitel.adapter

import android.content.Context
import android.graphics.Color
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
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ShortAdapter(private val context: Context, private var shortList: List<ShortVideo>) :
    RecyclerView.Adapter<ShortAdapter.ShortViewHolder>() {

    private var currentPlayingViewHolder: ShortViewHolder? = null
    private var viewHolders = mutableListOf<ShortAdapter.ShortViewHolder>()

    inner class ShortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        var scLike: MaterialTextView = itemView.findViewById(R.id.txt_number_like)
        var btnLike: ShapeableImageView = itemView.findViewById(R.id.btn_like)
        var scCmt: MaterialTextView = itemView.findViewById(R.id.txt_number_cmt)
        var btnCmt: ShapeableImageView = itemView.findViewById(R.id.btn_comment)
        var scShare: MaterialTextView = itemView.findViewById(R.id.txt_number_share)
        var btnShare: ShapeableImageView = itemView.findViewById(R.id.btn_share)
        var btnPauseResume: ShapeableImageView = itemView.findViewById(R.id.btn_pause_resume)
        var player: ExoPlayer? = null

        fun bind(context: Context, video: ShortVideo) {
            scLike.text = video.like.toString()
            scCmt.text = video.cmt.toString()
            scShare.text = video.share.toString()

            val uri = Uri.parse("android.resource://${context.packageName}/${video.urlShort}")

            player = ExoPlayer.Builder(itemView.context).build().apply {
                setMediaItem(MediaItem.fromUri(uri))
                repeatMode = Player.REPEAT_MODE_ALL
                prepare()
                playWhenReady = false
            }
            playerView.player = player

            btnLike.setOnClickListener {
                btnLike.setColorFilter(Color.parseColor("#FF3B30"))
            }

            itemView.setOnClickListener {
                player?.let {
                    if(it.isPlaying){
                        it.pause()
                        btnPauseResume.visibility = View.VISIBLE
                    }
                    else {
                        it.play()
                        btnPauseResume.visibility = View.GONE
                    }
                }
            }
        }

        fun playVideo() {
            player?.playWhenReady = true
            btnPauseResume.visibility = View.GONE
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
        val holder = ShortViewHolder(view)
        viewHolders.add(holder)
        return holder
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
        viewHolders.forEach { it.releasePlayer() }
        viewHolders.clear()
        currentPlayingViewHolder = null
    }
    fun pauseVideo(){
        currentPlayingViewHolder?.stopVideo()
    }
    fun resumeVideo(){
        currentPlayingViewHolder?.playVideo()
    }

}
