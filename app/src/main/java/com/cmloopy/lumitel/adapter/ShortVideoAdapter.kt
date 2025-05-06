package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.utils.ViewTimeFormat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ShortVideoAdapter(private var shortVideoList : List<Video>, private val onItemClick: (Int) -> Unit)
    : RecyclerView.Adapter<ShortVideoAdapter.ShortVideoViewHolder>() {
    inner class ShortVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var thumb = itemView.findViewById<ShapeableImageView>(R.id.img_thumbnail)
        private var title = itemView.findViewById<MaterialTextView>(R.id.txt_title_short_video)
        private var view = itemView.findViewById<MaterialTextView>(R.id.txt_view_short_video)
        fun bind(video: Video){
            title.text = video.videoTitle
            Glide.with(itemView.context).load(video.videoImage).into(thumb)
            view.text = ViewTimeFormat.getTotalView(video.totalViews)
            itemView.setOnClickListener {
                onItemClick(video.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortVideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_short_video,parent,false)
        return ShortVideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shortVideoList.size
    }

    override fun onBindViewHolder(holder: ShortVideoViewHolder, position: Int) {
        val video = shortVideoList[position]
        holder.bind(video)
    }

    fun updateData(newList: List<Video>) {
        shortVideoList = newList
        notifyDataSetChanged()
    }
}