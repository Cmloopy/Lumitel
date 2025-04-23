package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.channel.Channel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ListFollowAdapter(private var listChannels: List<Channel>, private val infoChannelClick: (Channel) -> Unit): RecyclerView.Adapter<ListFollowAdapter.ListFollowViewHolder>() {
    inner class ListFollowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imgChannelFollow = itemView.findViewById<ShapeableImageView>(R.id.img_channel_follow)
        private val nameChannelFollow = itemView.findViewById<MaterialTextView>(R.id.txt_name_channel_follow)
        fun bind(channel: Channel){
            Glide.with(itemView.context).load(channel.channelAvatar).error(R.drawable.ic_logo).into(imgChannelFollow)
            nameChannelFollow.text = channel.channelName
            itemView.setOnClickListener {
                infoChannelClick(channel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_follow, parent, false)
        return ListFollowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listChannels.size
    }

    override fun onBindViewHolder(holder: ListFollowViewHolder, position: Int) {
        holder.bind(listChannels[position])
    }
    fun updateList(list: List<Channel>){
        listChannels = list
        notifyDataSetChanged()
    }
}