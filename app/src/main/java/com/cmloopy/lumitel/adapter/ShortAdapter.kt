package com.cmloopy.lumitel.adapter

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.ShortVideo
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ShortAdapter(private val context: Context, private var shortList: List<ShortVideo>, private val onCommentClick: (ShortVideo) -> Unit) :
    RecyclerView.Adapter<ShortAdapter.ShortViewHolder>() {

    private var currentPlayingViewHolder: ShortViewHolder? = null
    private var viewHolders = mutableListOf<ShortAdapter.ShortViewHolder>()

    inner class ShortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        private var scLike: MaterialTextView = itemView.findViewById(R.id.txt_number_like)
        private var btnLike: ShapeableImageView = itemView.findViewById(R.id.btn_like)
        private var scCmt: MaterialTextView = itemView.findViewById(R.id.txt_number_cmt)
        private var btnCmt: ShapeableImageView = itemView.findViewById(R.id.btn_comment)
        private var scShare: MaterialTextView = itemView.findViewById(R.id.txt_number_share)
        //var btnShare: ShapeableImageView = itemView.findViewById(R.id.btn_share)
        var btnPauseResume: ShapeableImageView = itemView.findViewById(R.id.btn_pause_resume)
        var player: ExoPlayer? = null
        var seekBar: SeekBar = itemView.findViewById(R.id.seekbar_short_video)
        var currentTime: MaterialTextView = itemView.findViewById(R.id.txt_current_time_short_video)
        var fullTime: MaterialTextView = itemView.findViewById(R.id.txt_full_short_video)

        var linearLikeCmtShare: LinearLayout = itemView.findViewById(R.id.linearLayout_like_cmt_share_short)
        var linearTitile: LinearLayout = itemView.findViewById(R.id.linearLayout_title_short)
        var linearTimeShort: LinearLayout = itemView.findViewById(R.id.linearLayout_timeShort)

        private val handler = Handler(Looper.getMainLooper())

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
            btnCmt.setOnClickListener {
                onCommentClick(video)
            }
            //Setting Visibility for Pause & Resume
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
            //Update process Seekbar
            updateSeekBar()

            //Set defaute time to Seekbar
            var duration = 0L

            player!!.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        duration = player!!.duration
                        if (duration != C.TIME_UNSET) {
                            fullTime.text = formatTime(duration)
                        }
                    }
                }
            })
            //Set curent time Seekbar
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser && player != null) {
                        val newPosition = (progress * duration) / 100
                        currentTime.text = formatTime(newPosition)
                        player!!.seekTo(newPosition)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    player?.pause()
                    linearTitile.visibility = View.GONE
                    linearLikeCmtShare.visibility = View.GONE
                    linearTimeShort.visibility = View.VISIBLE
                    seekBar?.setPadding(50,42,50,21)
                    seekBar?.thumb = ContextCompat.getDrawable(context, R.drawable.custom_thumb_seekbar_2)
                }
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    player?.play()
                    linearTitile.visibility = View.VISIBLE
                    linearLikeCmtShare.visibility = View.VISIBLE
                    linearTimeShort.visibility = View.GONE
                    btnPauseResume.visibility = View.GONE

                    player?.let {
                        currentTime.text = formatTime(it.currentPosition)
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        seekBar?.setPadding(50, 51, 50, 25)
                        seekBar?.thumb = ContextCompat.getDrawable(context, R.drawable.custom_thumb_seekbar)
                    }, 3000)
                }

            })
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

        private fun updateSeekBar() {
            val runnable = object : Runnable {
                override fun run() {
                    player?.let {
                        if (it.isPlaying) {
                            val position = it.currentPosition
                            val duration = it.duration
                            val progress = ((position * 100) / duration).toInt()
                            seekBar.progress = progress
                        }
                        handler.postDelayed(this, 100)
                    }
                }
            }
            handler.post(runnable)
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

    //Setup new Video play if it's holder attaches to window
    override fun onViewAttachedToWindow(holder: ShortViewHolder) {
        super.onViewAttachedToWindow(holder)
        currentPlayingViewHolder?.stopVideo()  // Dừng video trước đó nếu có
        currentPlayingViewHolder = holder
        holder.playVideo()  // Chỉ phát video khi ViewHolder này được gắn vào màn hình
    }
    //Stop play video when ViewHolder detaches from window
    override fun onViewDetachedFromWindow(holder: ShortViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopVideo()
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
    private fun formatTime(milliseconds: Long): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
