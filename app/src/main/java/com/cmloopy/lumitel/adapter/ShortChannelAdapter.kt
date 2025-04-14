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

class ShortChannelAdapter(private var listShort:List<Video>):RecyclerView.Adapter<ShortChannelAdapter.ShortChannelViewHolder>() {
    inner class ShortChannelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgVideoImage = itemView.findViewById<ShapeableImageView>(R.id.img_video_image)
        val totalView = itemView.findViewById<MaterialTextView>(R.id.txt_total_view)
        fun bind(video: Video){
            Glide.with(itemView.context).load(video.videoImage).into(imgVideoImage)
            totalView.text = "${video.totalViews} lượt xem"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_short_channel, parent, false)
        return ShortChannelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listShort.size
    }

    override fun onBindViewHolder(holder: ShortChannelViewHolder, position: Int) {
        holder.bind(listShort[position])
    }
}