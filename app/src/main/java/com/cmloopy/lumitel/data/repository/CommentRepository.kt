package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.data.api.CommentApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.comment.Comment
import com.cmloopy.lumitel.utils.Encode

class CommentRepository {
    private val timestamp = System.currentTimeMillis().toString()
    private val security = ""

    private val apiCommentService = RetrofitClient.instance.create(CommentApi::class.java)
    suspend fun getCommentVideo(contentId:Int, msisdn: String): List<Comment>{
        val result = apiCommentService.getCommentVideo(contentId,Encode.url(msisdn),0,20,timestamp,security)
        return result.data
    }
}