package com.cmloopy.lumitel.zsingleexo

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.manager.ExoPlayerManager
import com.cmloopy.lumitel.utils.ViewTimeFormat
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max
import kotlin.math.min

@Suppress("DEPRECATION")
@UnstableApi
class VideoViewAdapterRemake(
    private var listVideo: List<Video>,
    private val context: Context,
    private val msisdn: String,
    private val rotateVideo:() -> Unit,
    private val infoChannelClick:(Video) -> Unit,
    private val followChannel:(Int, Int) -> Unit
) : RecyclerView.Adapter<VideoViewAdapterRemake.VideoViewHolder>() {
    private var isRotate = false
    private var isMute = false
    private var currentPlayingViewHolder: VideoViewAdapterRemake.VideoViewHolder? = null
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        private var duration = 0L
        private var seekBar: SeekBar = itemView.findViewById(R.id.seekbar_short_video)
        private var currentTime: MaterialTextView = itemView.findViewById(R.id.txt_current_time_short_video)
        private var fullTime: MaterialTextView = itemView.findViewById(R.id.txt_full_short_video)

        private var scLike: MaterialTextView = itemView.findViewById(R.id.txt_number_like)
        private var scCmt: MaterialTextView = itemView.findViewById(R.id.txt_number_cmt)
        private var scShare: MaterialTextView = itemView.findViewById(R.id.txt_number_share)
        private var imgChannel: ShapeableImageView = itemView.findViewById(R.id.img_channel)
        private val btnFollow: MaterialButton = itemView.findViewById(R.id.btn_follow_channel)

        private var btnPauseResume: ShapeableImageView = itemView.findViewById(R.id.btn_pause_resume)
        private var btnBackward: ShapeableImageView = itemView.findViewById(R.id.btn_back_10s)
        private var btnForward: ShapeableImageView = itemView.findViewById(R.id.btn_next_10s)
        private var btnFullScreen: MaterialButton = itemView.findViewById(R.id.btn_short_fullscreen)
        private var btnMuteUnmute: ShapeableImageView = itemView.findViewById(R.id.btn_mute_unmute)

        private var txtNameChannel: MaterialTextView = itemView.findViewById(R.id.txt_name_channel)
        private var txtVideoDesc: MaterialTextView = itemView.findViewById(R.id.txt_video_desc)

        private var linearTitle: LinearLayout = itemView.findViewById(R.id.linearLayout_title_short)
        private var linearTimeShort: LinearLayout = itemView.findViewById(R.id.linearLayout_timeShort)
        private var linearSettingVideoPlay: LinearLayout = itemView.findViewById(R.id.ln_setting_video_play)
        private var linearLikeCmtShare: LinearLayout = itemView.findViewById(R.id.linearLayout_like_cmt_share_short)
        private var btnBackToPortrait: ShapeableImageView = itemView.findViewById(R.id.btn_back_to_portrait)

        private val handler = Handler(Looper.getMainLooper())
        private val handlerCurrentTime = Handler(Looper.getMainLooper())

        fun bind(video: Video){
            scLike.text = ViewTimeFormat.formatTotal(video.totalViews)
            scCmt.text = ViewTimeFormat.formatTotal(video.totalComments)
            scShare.text = ViewTimeFormat.formatTotal(video.totalShares)
            txtNameChannel.text = video.channel.channelName
            txtVideoDesc.text = "${video.videoTitle}\n${video.videoDesc}"
            txtVideoDesc.setOnClickListener {
                if(txtVideoDesc.maxLines == 3) {
                    txtVideoDesc.maxLines = Integer.MAX_VALUE
                } else txtVideoDesc.maxLines = 3
            }
            Glide.with(context).load(video.channel.channelAvatar).into(imgChannel)
            if(msisdn == video.channel.msisdn) btnFollow.visibility = View.GONE
            if(video.channel.isFollow == 1){
                btnFollow.text = "Đã đăng kí"
                val color = ContextCompat.getColor(context, R.color.non_gray)
                btnFollow.backgroundTintList = ColorStateList.valueOf(color)
            }
            updateSeekBar()

            ExoPlayerManager.getPlayer(context).addListener(object: Player.Listener{
                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    duration = ExoPlayerManager.getDuration()
                    if (duration != C.TIME_UNSET) {
                        fullTime.text = ViewTimeFormat.formatTimeDuration(duration)
                    }
                }
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        duration = ExoPlayerManager.getDuration()
                        if (duration != C.TIME_UNSET) {
                            fullTime.text = ViewTimeFormat.formatTimeDuration(duration)
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
            //Kéo thả seekbar
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        val newPosition = (progress * duration) / 100
                        currentTime.text = ViewTimeFormat.formatTimeDuration(newPosition)
                        ExoPlayerManager.seekTo(newPosition)
                    }
                }
                //Bắt đầu kéo thanh tua
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    ExoPlayerManager.pause()
                    handler.removeCallbacksAndMessages(null)
                    linearTitle.visibility = View.GONE
                    linearLikeCmtShare.visibility = View.GONE
                    linearTimeShort.visibility = View.VISIBLE
                    linearSettingVideoPlay.visibility = View.VISIBLE
                    seekBar?.setPadding(50, 42, 50, 21)
                    seekBar?.thumb =
                        ContextCompat.getDrawable(context, R.drawable.custom_thumb_seekbar_2)
                }
                //Thả thanh tua
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    ExoPlayerManager.resume()
                    if (!isRotate) {
                        linearTitle.visibility = View.VISIBLE
                        linearLikeCmtShare.visibility = View.VISIBLE
                        btnPauseResume.visibility = View.GONE
                        btnBackward.visibility = View.GONE
                        btnForward.visibility = View.GONE
                        btnPauseResume.setImageResource(R.drawable.ic_play)
                        currentTime.text = ViewTimeFormat.formatTimeDuration(ExoPlayerManager.getCurrentPosition())
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
                        currentTime.text = ViewTimeFormat.formatTimeDuration(ExoPlayerManager.getCurrentPosition())
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
            //Set button Pause & Resume
            itemView.setOnClickListener {
                if (!isRotate) {
                    seekBar.visibility = View.VISIBLE
                    if (ExoPlayerManager.getStatus()) {
                        ExoPlayerManager.pause()
                        btnPauseResume.visibility = View.VISIBLE
                        linearTimeShort.visibility = View.VISIBLE
                        linearSettingVideoPlay.visibility = View.VISIBLE
                        btnForward.visibility = View.VISIBLE
                        btnBackward.visibility = View.VISIBLE
                        btnPauseResume.setImageResource(R.drawable.ic_pause)
                    } else {
                        ExoPlayerManager.resume()
                        hideControl()
                        seekBar.visibility = View.VISIBLE
                        btnPauseResume.setImageResource(R.drawable.ic_play)
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
                        if (ExoPlayerManager.getStatus()) {
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
            btnFullScreen.setOnClickListener {
                isRotate = true
                rotateVideo()
                hideControl()
                btnFullScreen.visibility = View.GONE
                linearLikeCmtShare.visibility = View.GONE
                linearTitle.visibility = View.GONE
            }
            btnBackToPortrait.setOnClickListener {
                isRotate = false
                rotateVideo()
                handler.removeCallbacksAndMessages(null)
                hideControl()
                seekBar.visibility = View.VISIBLE
                btnFullScreen.visibility = View.VISIBLE
                linearLikeCmtShare.visibility = View.VISIBLE
                linearTitle.visibility = View.VISIBLE
            }
            btnPauseResume.setOnClickListener {
                if (isRotate) {
                    handler.removeCallbacksAndMessages(null)
                    if (ExoPlayerManager.getStatus()) {
                        ExoPlayerManager.pause()
                        btnPauseResume.setImageResource(R.drawable.ic_pause)
                    } else {
                        ExoPlayerManager.resume()
                        btnPauseResume.setImageResource(R.drawable.ic_play)
                        handler.postDelayed(
                            {
                                hideControl()
                            },
                            5000
                        )
                    }
                }
                else{
                    if(ExoPlayerManager.getStatus()){
                        ExoPlayerManager.pause()
                    }
                    else{
                        ExoPlayerManager.resume()
                        hideControl()
                        seekBar.visibility = View.VISIBLE
                        btnPauseResume.setImageResource(R.drawable.ic_play)
                    }
                }
            }
            btnMuteUnmute.setOnClickListener {
                if (isMute) {
                    ExoPlayerManager.unMute()
                    btnMuteUnmute.setImageResource(R.drawable.ic_unmute)
                } else {
                    ExoPlayerManager.mute()
                    btnMuteUnmute.setImageResource(R.drawable.ic_mute)
                }
                isMute = !isMute
            }
            btnBackward.setOnClickListener {
                handler.removeCallbacksAndMessages(null)
                val newPosition = max(0, ExoPlayerManager.getCurrentPosition() - 10000)
                ExoPlayerManager.seekTo(newPosition)
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
                val newPosition = min(ExoPlayerManager.getCurrentPosition() + 10000, duration)
                ExoPlayerManager.seekTo(newPosition)
                updateSeekbarProgress(newPosition)
                handler.postDelayed(
                    {
                        hideControl()
                    },
                    5000
                )
            }
            imgChannel.setOnClickListener {
                infoChannelClick(video)
            }
            txtNameChannel.setOnClickListener {
                infoChannelClick(video)
            }
            btnFollow.setOnClickListener {
                followChannel(video.channel.isFollow, video.channelId)
            }

        }
        fun clearPlayer() {
            playerView.player = null
        }
        fun updateFollow(isFollow: Int){
            if(isFollow == 1) {
                btnFollow.text = "Đã đăng kí"
                val color = ContextCompat.getColor(context, R.color.non_gray)
                btnFollow.backgroundTintList = ColorStateList.valueOf(color)
            } else {
                btnFollow.text = "Đăng kí"
                val color = ContextCompat.getColor(context, R.color.blue_3)
                btnFollow.backgroundTintList = ColorStateList.valueOf(color)
            }
        }
        private fun updateSeekBar() {
            val runnable = object : Runnable {
                override fun run() {
                    if (ExoPlayerManager.getStatus()) {
                        val position = ExoPlayerManager.getCurrentPosition()
                        updateSeekbarProgress(position)
                    }
                    handlerCurrentTime.postDelayed(this, 50)
                }
            }
            handlerCurrentTime.post(runnable)
        }
        fun updateSeekbarProgress(position: Long){
            if(duration > 0) {
                val progress = ((position * 100) / duration).toInt()
                seekBar.progress = progress
                currentTime.text = ViewTimeFormat.formatTimeDuration(position)
            } else seekBar.progress = 0
        }
        fun hideControl(){
            linearSettingVideoPlay.visibility = View.GONE
            btnBackward.visibility = View.GONE
            btnForward.visibility = View.GONE
            btnPauseResume.visibility = View.GONE
            seekBar.visibility = View.GONE
            linearTimeShort.visibility = View.GONE
            btnBackToPortrait.visibility = View.GONE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_activity, parent, false)
        return VideoViewHolder(view)
    }
    override fun getItemCount(): Int = listVideo.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }
    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.bindingAdapterPosition
        val video = listVideo[position]
        currentPlayingViewHolder = holder
        holder.playerView.player = ExoPlayerManager.getPlayer(context)
        ExoPlayerManager.play(context, video.videoMedia)
    }

    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearPlayer()
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        holder.clearPlayer()
    }
    fun getVideo(position: Int): Video? = listVideo.getOrNull(position)
    fun updateFollow(isFollow: Int){
        currentPlayingViewHolder?.updateFollow(isFollow)
        val posit = currentPlayingViewHolder?.adapterPosition
        if(posit != null) {
            listVideo[posit].channel.isFollow = isFollow
        }
    }
}
