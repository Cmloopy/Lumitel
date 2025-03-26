package com.cmloopy.lumitel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.ShortVideo
import com.cmloopy.lumitel.views.ShortVideoActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ShortVideoAdapter(private var shortVideoList : List<ShortVideo>)
    : RecyclerView.Adapter<ShortVideoAdapter.ShortVideoViewHolder>() {
    inner class ShortVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var thumb = itemView.findViewById<ShapeableImageView>(R.id.img_thumbnail)
        var title = itemView.findViewById<MaterialTextView>(R.id.txt_title_short_video)
        var view = itemView.findViewById<MaterialTextView>(R.id.txt_view_short_video)
        fun bind(video: ShortVideo){
            title.text = video.title

            thumb.setImageResource(video.img)

            view.text = "${video.view} lượt xem"

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ShortVideoActivity::class.java)
                context.startActivity(intent)
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

    fun updateData(newList: List<ShortVideo>) {
        shortVideoList = newList
        notifyDataSetChanged()
    }
}