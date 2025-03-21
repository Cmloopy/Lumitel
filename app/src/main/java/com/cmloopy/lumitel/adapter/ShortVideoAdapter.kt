package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.ShortVideo
import com.google.android.material.textview.MaterialTextView

class ShortVideoAdapter(private val shortVideoList : List<ShortVideo>): RecyclerView.Adapter<ShortVideoAdapter.ShortVideoViewHolder>() {
    inner class ShortVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var urlShort = itemView.findViewById<PlayerView>(R.id.video_view_short)
        var title = itemView.findViewById<MaterialTextView>(R.id.txt_title_short_video)
        var view = itemView.findViewById<MaterialTextView>(R.id.txt_view_short_video)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortVideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_short_video,parent,false)
        return ShortVideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shortVideoList.size
    }

    override fun onBindViewHolder(holder: ShortVideoViewHolder, position: Int) {

    }
}