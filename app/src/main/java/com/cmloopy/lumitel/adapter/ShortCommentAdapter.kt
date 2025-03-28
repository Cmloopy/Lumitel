package com.cmloopy.lumitel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.Comment
import com.google.android.material.textview.MaterialTextView

class ShortCommentAdapter(private var listShortCmt: List<Comment>): RecyclerView.Adapter<ShortCommentAdapter.ShortCommentViewHolder>() {
    inner class ShortCommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var commentShortVideo = itemView.findViewById<MaterialTextView>(R.id.txt_comment_short_video)
        fun bind(comment: Comment){
            commentShortVideo.text = comment.titleCm
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
}