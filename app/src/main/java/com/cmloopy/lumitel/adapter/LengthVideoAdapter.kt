package com.cmloopy.lumitel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.Video
import com.cmloopy.lumitel.views.VideoActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class LengthVideoAdapter(private var videoList: List<Video>): RecyclerView.Adapter<LengthVideoAdapter.HotVideoViewHolder>() {
    inner class HotVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var imgBiaVideo: ShapeableImageView = itemView.findViewById(R.id.img_bia_video)
        private var txtLengthVideo: MaterialTextView = itemView.findViewById(R.id.txt_length_video)
        private var imgAuthor:ShapeableImageView = itemView.findViewById(R.id.img_author)
        private var txtTitleVideo:MaterialTextView = itemView.findViewById(R.id.txt_title_video)
        //var txtNameAuthor:MaterialTextView = itemView.findViewById(R.id.txt_name_author)
        //var txtViewVideo:MaterialTextView = itemView.findViewById(R.id.txt_view_video)
        //var txtUpdatedAt:MaterialTextView = itemView.findViewById(R.id.txt_updated_at)

        fun bind(video: Video){
            imgBiaVideo.setImageResource(video.imgVideo)
            txtLengthVideo.text = "5:08"
            imgAuthor.setImageResource(R.drawable.nen1)
            txtTitleVideo.text = video.title

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, VideoActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotVideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hot_video, parent, false)
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
}