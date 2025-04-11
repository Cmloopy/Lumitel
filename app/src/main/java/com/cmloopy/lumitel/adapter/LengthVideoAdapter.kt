package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class LengthVideoAdapter(private var videoList: List<Video>, private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<LengthVideoAdapter.HotVideoViewHolder>() {
    inner class HotVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var imgBiaVideo: ShapeableImageView = itemView.findViewById(R.id.img_bia_video)
        private var txtLengthVideo: MaterialTextView = itemView.findViewById(R.id.txt_length_video)
        private var imgAuthor:ShapeableImageView = itemView.findViewById(R.id.img_author)
        private var txtTitleVideo:MaterialTextView = itemView.findViewById(R.id.txt_title_video)
        private var txtNameAuthor:MaterialTextView = itemView.findViewById(R.id.txt_name_author)
        private var txtViewVideo:MaterialTextView = itemView.findViewById(R.id.txt_view_video)
        //var txtUpdatedAt:MaterialTextView = itemView.findViewById(R.id.txt_updated_at)

        fun bind(video: Video){
            Glide.with(itemView.context).load(video.videoImage).into(imgBiaVideo)
            txtLengthVideo.text = formatTime(video.videoTime)
            Glide.with(itemView.context).load(video.channel.channelAvatar).into(imgAuthor)
            txtTitleVideo.text = video.videoTitle
            txtNameAuthor.text = video.channel.channelName
            txtViewVideo.text = "${video.totalViews} Views"
            itemView.setOnClickListener {
                onItemClick(video.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotVideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_length_video, parent, false)
        return HotVideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: HotVideoViewHolder, position: Int) {
        holder.bind(videoList[position])
    }
    fun updateData(newList: List<Video>){
        videoList = newList
        notifyDataSetChanged()
    }
    fun formatTime(time: String): String{
        val timee = time.toInt()
        val minute = timee / 60
        val sec = timee % 60
        return "$minute:$sec"
    }
}