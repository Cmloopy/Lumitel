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
        var imgVideo = itemView.findViewById<ShapeableImageView>(R.id.img_video_image_short)
        var titleVideo = itemView.findViewById<MaterialTextView>(R.id.txt_title_video_short)
        var imgChannel = itemView.findViewById<ShapeableImageView>(R.id.img_avatar_channel_short)
        var txtChanel = itemView.findViewById<MaterialTextView>(R.id.txt_name_channel_short)
        var txtTotalLike = itemView.findViewById<MaterialTextView>(R.id.txt_total_like_short)
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