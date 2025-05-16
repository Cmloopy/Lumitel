package com.cmloopy.lumitel.zsingleexo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.manager.ExoPlayerManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView

@UnstableApi
class VideoViewAdapterRemake(
    private var listVideo: List<Video>,
    private val context: Context,
    private val rotateVideo:() -> Unit
) : RecyclerView.Adapter<VideoViewAdapterRemake.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerView: PlayerView = itemView.findViewById(R.id.player_view_short_video)
        private val btnRotate: MaterialButton = itemView.findViewById(R.id.btn_short_fullscreen)
        private val btnBackPortrait: ShapeableImageView = itemView.findViewById(R.id.btn_back_to_portrait)
        private val linearTitle: LinearLayout = itemView.findViewById(R.id.linearLayout_title_short)
        private val linearLike: LinearLayout = itemView.findViewById(R.id.linearLayout_like_cmt_share_short)
        private val btnPauseResume: ShapeableImageView = itemView.findViewById(R.id.btn_pause_resume)
        private val seekBar: SeekBar = itemView.findViewById(R.id.seekbar_short_video)

        fun bind(video: Video){
            if(video.aspecRatio.toDouble() < 1.2){
                btnRotate.visibility = View.VISIBLE
            } else btnRotate.visibility = View.GONE
            btnRotate.setOnClickListener {
                rotateVideo()
                btnRotate.visibility = View.GONE
                linearLike.visibility = View.GONE
                linearTitle.visibility = View.GONE
                seekBar.visibility = View.GONE
                btnBackPortrait.visibility = View.VISIBLE
            }
            btnBackPortrait.setOnClickListener {
                rotateVideo()
                btnRotate.visibility = View.VISIBLE
                linearLike.visibility = View.VISIBLE
                linearTitle.visibility = View.VISIBLE
                seekBar.visibility = View.VISIBLE
                btnBackPortrait.visibility = View.GONE
            }
            itemView.setOnClickListener {
                if(ExoPlayerManager.getPlayer(context).isPlaying){
                    ExoPlayerManager.pause()
                    btnPauseResume.visibility = View.VISIBLE
                    btnPauseResume.setImageResource(R.drawable.ic_pause)
                } else {
                    ExoPlayerManager.resume()
                    btnPauseResume.visibility = View.GONE
                    btnPauseResume.setImageResource(R.drawable.ic_play)
                }
            }
        }
        fun clearPlayer() {
            playerView.player = null
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
}
