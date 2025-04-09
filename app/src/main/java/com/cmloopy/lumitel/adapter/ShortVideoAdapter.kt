package com.cmloopy.lumitel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.views.ShortVideoActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class ShortVideoAdapter(private var shortVideoList : List<Video>)
    : RecyclerView.Adapter<ShortVideoAdapter.ShortVideoViewHolder>() {
    inner class ShortVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var thumb = itemView.findViewById<ShapeableImageView>(R.id.img_thumbnail)
        var title = itemView.findViewById<MaterialTextView>(R.id.txt_title_short_video)
        var view = itemView.findViewById<MaterialTextView>(R.id.txt_view_short_video)
        fun bind(video: Video){
            title.text = video.videoTitle

            Glide.with(itemView.context).load(video.videoImage).into(thumb)

            view.text = "${video.totalViews} lượt xem"

            itemView.setOnClickListener {
                /*val context = itemView.context
                val intent = Intent(context, ShortVideoActivity::class.java)
                intent.putExtra("isShort",1)
                context.startActivity(intent)*/
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