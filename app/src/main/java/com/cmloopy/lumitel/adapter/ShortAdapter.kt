package com.cmloopy.lumitel.adapter

import android.content.Context
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
import androidx.media3.common.Timeline
import androidx.media3.common.VideoSize
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlin.math.*

class ShortAdapter(private val context: Context,
                   private var shortList: List<Video>,
                   private val onCommentClick: (Video) -> Unit,
                   private val onRotateClick: (Boolean) -> Unit) :
    RecyclerView.Adapter<ShortAdapter.ShortViewHolder>() {

    private var currentPlayingViewHolder: ShortViewHolder? = null
    private var viewHolders = mutableListOf<ShortAdapter.ShortViewHolder>()
    //Portrait = false; Landscape = true
    private var isRotate = false
    private var isMute = false

    inner class ShortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        private var scLike: MaterialTextView = itemView.findViewById(R.id.txt_number_like)
        private var btnLike: ShapeableImageView = itemView.findViewById(R.id.btn_like)
        private var scCmt: MaterialTextView = itemView.findViewById(R.id.txt_number_cmt)
        private var btnCmt: ShapeableImageView = itemView.findViewById(R.id.btn_comment)
        private var scShare: MaterialTextView = itemView.findViewById(R.id.txt_number_share)
        //var btnShare: ShapeableImageView = itemView.findViewById(R.id.btn_share)
        private var btnPauseResume: ShapeableImageView = itemView.findViewById(R.id.btn_pause_resume)
        private var btnBackward: ShapeableImageView = itemView.findViewById(R.id.btn_back_10s)
        private var btnForward: ShapeableImageView = itemView.findViewById(R.id.btn_next_10s)
        private var btnFullScreen: MaterialButton = itemView.findViewById(R.id.btn_short_fullscreen)
        private var player: ExoPlayer? = null
        private var duration = 0L
        private var seekBar: SeekBar = itemView.findViewById(R.id.seekbar_short_video)
        private var currentTime: MaterialTextView = itemView.findViewById(R.id.txt_current_time_short_video)
        private var fullTime: MaterialTextView = itemView.findViewById(R.id.txt_full_short_video)

        var btnBackToPortrait: ShapeableImageView = itemView.findViewById(R.id.btn_back_to_portrait)
        var btnMuteUnmute: ShapeableImageView = itemView.findViewById(R.id.btn_mute_unmute)
        //var btnSettingVideoPlay: ShapeableImageView = itemView.findViewById(R.id.btn_setting_video_play)
        var linearLikeCmtShare: LinearLayout = itemView.findViewById(R.id.linearLayout_like_cmt_share_short)
        var linearTitile: LinearLayout = itemView.findViewById(R.id.linearLayout_title_short)
        var linearTimeShort: LinearLayout = itemView.findViewById(R.id.linearLayout_timeShort)
        var linearSettingVideoPlay: LinearLayout = itemView.findViewById(R.id.ln_setting_video_play)
        //Handler of Landscape
        private val handler = Handler(Looper.getMainLooper())
        //Handler current Time
        private val handlerCurrentTime = Handler(Looper.getMainLooper())
        fun bind(context: Context, video: Video) {
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

            }
            btnCmt.setOnClickListener {
                onCommentClick(video)
            }
            //Set button Pause & Resume
            itemView.setOnClickListener {
                if (!isRotate) {
                    player?.let {
                        seekBar.visibility = View.VISIBLE
                        if (it.isPlaying) {
                            it.pause()
                            btnPauseResume.visibility = View.VISIBLE
                            linearTimeShort.visibility = View.VISIBLE
                            linearSettingVideoPlay.visibility = View.VISIBLE
                            btnForward.visibility = View.VISIBLE
                            btnBackward.visibility = View.VISIBLE
                            btnPauseResume.setImageResource(R.drawable.ic_pause)
                        } else {
                            it.play()
                            hideControl()
                            seekBar.visibility = View.VISIBLE
                            btnPauseResume.setImageResource(R.drawable.ic_play)
                        }
                    }
                }
                //Landscape
                else {
                    if (btnPauseResume.visibility == View.GONE) {
                        btnPauseResume.visibility = View.VISIBLE
                        btnBackward.visibility = View.VISIBLE
                        btnForward.visibility = View.VISIBLE
                        seekBar.visibility = View.VISIBLE
                        linearTimeShort.visibility = View.VISIBLE
                        linearSettingVideoPlay.visibility = View.VISIBLE
                        btnBackToPortrait.visibility = View.VISIBLE
                        handler.removeCallbacksAndMessages(null)
                        if (player!!.isPlaying) {
                            handler.postDelayed({
                                hideControl()
                            }, 5000)
                        }
                    } else {
                        handler.removeCallbacksAndMessages(null)
                        hideControl()
                    }
                }
            }
            //Update seekbar theo thời gian Video
            updateSeekBar()
            //Set độ dài Video
            player!!.addListener(object : Player.Listener {
                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    duration = player!!.duration
                    if (duration != C.TIME_UNSET) {
                        fullTime.text = formatTime(duration)
                    }
                }
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        duration = player!!.duration
                        if (duration != C.TIME_UNSET) {
                            fullTime.text = formatTime(duration)
                        }
                    }
                }
                //Kiểm tra tỉ lệ khung hình
                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    super.onVideoSizeChanged(videoSize)
                    val videoRatio = videoSize.width.toFloat() / videoSize.height.toFloat()
                    if (videoRatio > 1.4) {
                        if (!isRotate) btnFullScreen.visibility = View.VISIBLE
                    } else {
                        btnFullScreen.visibility = View.GONE
                    }
                }
            })
            //Button quay ngang màn hình
            btnFullScreen.setOnClickListener {
                isRotate = true
                onRotateClick(isRotate)
                hideControl()
                btnFullScreen.visibility = View.GONE
                linearLikeCmtShare.visibility = View.GONE
                linearTitile.visibility = View.GONE
            }
            //Nút pause giữa UI (Chỉ tác dụng khi ở Landscape)
            btnPauseResume.setOnClickListener {
                if (isRotate) {
                    handler.removeCallbacksAndMessages(null)
                    player?.let {
                        if (it.isPlaying) {
                            it.pause()
                            btnPauseResume.setImageResource(R.drawable.ic_pause)
                        } else {
                            it.play()
                            btnPauseResume.setImageResource(R.drawable.ic_play)
                            handler.postDelayed(
                                {
                                    hideControl()
                                },
                                5000
                            )
                        }
                    }
                }
                else{
                    player?.let {
                        if(it.isPlaying){
                            it.pause()
                        }
                        else{
                            it.play()
                            hideControl()
                            seekBar.visibility = View.VISIBLE
                            btnPauseResume.setImageResource(R.drawable.ic_play)
                        }
                    }
                }
            }
            //Button mute & unmute Video
            btnMuteUnmute.setOnClickListener {
                if (isMute) {
                    player?.volume = 1.0f; // Bật âm thanh
                    btnMuteUnmute.setImageResource(R.drawable.ic_unmute)
                } else {
                    player?.volume = 0f; // Tắt âm thanh
                    btnMuteUnmute.setImageResource(R.drawable.ic_mute)
                }
                isMute = !isMute;
            }
            //Xử lý tua đi & tua ngược 10s Video
            btnBackward.setOnClickListener {
                handler.removeCallbacksAndMessages(null)
                val newPosition = max(0, player!!.currentPosition - 10000)
                player!!.seekTo(newPosition)
                updateSeekbarProgress(newPosition)
                handler.postDelayed(
                    {
                        hideControl()
                    },
                    5000
                )
            }
            btnForward.setOnClickListener {
                handler.removeCallbacksAndMessages(null)
                val newPosition = min(player!!.currentPosition + 10000, duration)
                player!!.seekTo(newPosition)
                updateSeekbarProgress(newPosition)
                handler.postDelayed(
                    {
                        hideControl()
                    },
                    5000
                )
            }
            //Button quay về màn dọc
            btnBackToPortrait.setOnClickListener {
                isRotate = false
                onRotateClick(isRotate)
                handler.removeCallbacksAndMessages(null)
                hideControl()
                seekBar.visibility = View.VISIBLE
                btnFullScreen.visibility = View.VISIBLE
                linearLikeCmtShare.visibility = View.VISIBLE
                linearTitile.visibility = View.VISIBLE
            }
            //Kéo thả Seekbar
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser && player != null) {
                        val newPosition = (progress * duration) / 100
                        currentTime.text = formatTime(newPosition)
                        player!!.seekTo(newPosition)
                    }
                }
                //Bắt đầu kéo thanh tua
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    player?.pause()
                    handler.removeCallbacksAndMessages(null)
                    linearTitile.visibility = View.GONE
                    linearLikeCmtShare.visibility = View.GONE
                    linearTimeShort.visibility = View.VISIBLE
                    linearSettingVideoPlay.visibility = View.VISIBLE
                    seekBar?.setPadding(50, 42, 50, 21)
                    seekBar?.thumb =
                        ContextCompat.getDrawable(context, R.drawable.custom_thumb_seekbar_2)
                }
                //Thả thanh tua
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    player?.play()
                    if (!isRotate) {
                        linearTitile.visibility = View.VISIBLE
                        linearLikeCmtShare.visibility = View.VISIBLE
                        btnPauseResume.visibility = View.GONE
                        btnPauseResume.setImageResource(R.drawable.ic_play)
                        player?.let {
                            currentTime.text = formatTime(it.currentPosition)
                        }
                        handlerCurrentTime.postDelayed({
                            linearTimeShort.visibility = View.GONE
                            linearSettingVideoPlay.visibility = View.GONE
                            seekBar?.setPadding(50, 51, 50, 25)
                            seekBar?.thumb =
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.custom_thumb_seekbar
                                )
                        }, 5000)
                    } else {
                        btnPauseResume.apply {
                            setImageResource(R.drawable.ic_play)
                            visibility = View.VISIBLE
                        }
                        btnBackward.visibility = View.VISIBLE
                        btnForward.visibility = View.VISIBLE
                        player?.let {
                            currentTime.text = formatTime(it.currentPosition)
                        }
                        seekBar?.setPadding(50, 51, 50, 25)
                        seekBar?.thumb =
                            ContextCompat.getDrawable(context, R.drawable.custom_thumb_seekbar)
                        handler.postDelayed(
                            {
                                hideControl()
                            },
                            5000
                        )
                    }
                }
            })
        }
        fun hideControl() {
            linearSettingVideoPlay.visibility = View.GONE
            btnBackward.visibility = View.GONE
            btnForward.visibility = View.GONE
            btnPauseResume.visibility = View.GONE
            seekBar.visibility = View.GONE
            linearTimeShort.visibility = View.GONE
            btnBackToPortrait.visibility = View.GONE
        }
        fun playVideo() {
            player?.playWhenReady = true
            btnPauseResume.setImageResource(R.drawable.ic_play)
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
                            updateSeekbarProgress(position)
                        }
                        handlerCurrentTime.postDelayed(this, 50)
                    }
                }
            }
            handlerCurrentTime.post(runnable)
        }
        fun updateSeekbarProgress(position: Long){
            val progress = ((position * 100) / duration).toInt()
            seekBar.progress = progress
            currentTime.text = formatTime(position)
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
    fun backToPortrait(){
        currentPlayingViewHolder?.btnBackToPortrait?.performClick()
    }
    fun updateData(newList: List<Video>) {
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
