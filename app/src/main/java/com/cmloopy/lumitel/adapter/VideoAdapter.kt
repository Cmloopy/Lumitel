package com.cmloopy.lumitel.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.Video
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class VideoAdapter(private val context: Context, private var videoList: List<Video>): RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var currentPlayingViewHolder: VideoAdapter.VideoViewHolder? = null
    private var allInforBoolean = false
    inner class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imgAuthorVideoAct = itemView.findViewById<ShapeableImageView>(R.id.img_author_video_activity)
        var nameAuthorVideoAct = itemView.findViewById<MaterialTextView>(R.id.txt_name_author_video_act)
        var numberFollow = itemView.findViewById<MaterialTextView>(R.id.txt_number_follow)
        var numberVideo = itemView.findViewById<MaterialTextView>(R.id.txt_number_videos)
        var btnFollow = itemView.findViewById<MaterialButton>(R.id.btn_follow)
        var videoView = itemView.findViewById<PlayerView>(R.id.player_view_video)
        var titleVide = itemView.findViewById<MaterialTextView>(R.id.txt_title_video_activity)
        var btnAllInfoVideo = itemView.findViewById<ShapeableImageView>(R.id.btn_all_info_video)
        //views
        //time
        var allInfoVideo = itemView.findViewById<MaterialTextView>(R.id.txt_all_info_video)
        //like
        //cmt
        //share
        var player: ExoPlayer? = null
        fun bind(context: Context, video: Video){
            imgAuthorVideoAct.setImageResource(R.drawable.nen3)
            nameAuthorVideoAct.text = "Orinn Deep"
            titleVide.text = video.title

            val uri = Uri.parse("android.resource://${context.packageName}/${video.urlVideo}")

            player = ExoPlayer.Builder(itemView.context).build().apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
                playWhenReady = false
            }
            videoView.player = player
            videoView.setOnClickListener{
                player?.let {
                    if(it.isPlaying){
                        it.pause()
                    }
                    else {
                        it.play()
                    }
                }
            }

            btnAllInfoVideo.setOnClickListener {
                if(!allInforBoolean){
                    allInfoVideo.visibility = View.VISIBLE
                    btnAllInfoVideo.setImageResource(R.drawable.ic_triangle_up)
                    allInforBoolean = true
                }
                else {
                    allInfoVideo.visibility = View.GONE
                    btnAllInfoVideo.setImageResource(R.drawable.ic_triagnle_down)
                    allInforBoolean = false
                }
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_length, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(context, videoList[position])
    }

    override fun onViewAttachedToWindow(holder: VideoAdapter.VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        currentPlayingViewHolder?.stopVideo()  // Dừng video trước đó nếu có
        currentPlayingViewHolder = holder
        holder.playVideo()  // Chỉ phát video khi ViewHolder này được gắn vào màn hình
    }

    override fun onViewDetachedFromWindow(holder: VideoAdapter.VideoViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopVideo()  // Dừng phát video khi ViewHolder bị cuộn ra khỏi màn hình
    }

    fun updateData(newList: List<Video>) {
        videoList = newList
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