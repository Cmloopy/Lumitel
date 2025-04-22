package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.comment.Comment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ShortCommentAdapter(private var listShortCmt: List<Comment>): RecyclerView.Adapter<ShortCommentAdapter.ShortCommentViewHolder>() {
    inner class ShortCommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgChannel = itemView.findViewById<ShapeableImageView>(R.id.img_channel_comment)
        val nameChannel = itemView.findViewById<MaterialTextView>(R.id.txt_name_channel_comment)
        val contentCmt = itemView.findViewById<MaterialTextView>(R.id.txt_comment_video)
        val like = itemView.findViewById<MaterialTextView>(R.id.txt_total_like_cmt)
        val dislike = itemView.findViewById<MaterialTextView>(R.id.txt_total_dislike_cmt)
        fun bind(comment: Comment){
            Glide.with(itemView.context).load(comment.avatar).error(R.drawable.ic_logo).into(imgChannel)
            nameChannel.text = comment.name
            contentCmt.text = comment.content
            like.text = comment.like.toString()
            dislike.text = comment.dislike.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortCommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_short_video, parent, false)
        return ShortCommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listShortCmt.size
    }

    override fun onBindViewHolder(holder: ShortCommentViewHolder, position: Int) {
        holder.bind(listShortCmt[position])
    }

    fun updateComment(newList: List<Comment>){
        this.listShortCmt = newList
        notifyDataSetChanged()
    }
}