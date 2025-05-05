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

class ShortCateAdapter(private var list: List<Video>, private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<ShortCateAdapter.ShortCateViewHolder>() {
    inner class ShortCateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var imgVideo: ShapeableImageView = itemView.findViewById(R.id.img_video_image_short)
        private var titleVideo: MaterialTextView = itemView.findViewById(R.id.txt_title_video_short)
        private var imgChannel: ShapeableImageView = itemView.findViewById(R.id.img_avatar_channel_short)
        private var txtChanel: MaterialTextView = itemView.findViewById(R.id.txt_name_channel_short)
        private var txtTotalLike: MaterialTextView = itemView.findViewById(R.id.txt_total_like_short)
        fun bind(video: Video){
            Glide.with(itemView.context).load(video.videoImage).into(imgVideo)
            titleVideo.text = video.videoTitle
            Glide.with(itemView.context).load(video.channel.channelAvatar).into(imgChannel)
            txtChanel.text = video.channel.channelName
            txtTotalLike.text = video.totalLikes.toString()
            itemView.setOnClickListener {
                onItemClick(video.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortCateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_short_cate_video, parent, false)
        return ShortCateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShortCateViewHolder, position: Int) {
        holder.bind(list[position])
    }
    fun updateData(newList: List<Video>){
        list = newList
        notifyDataSetChanged()
    }
}