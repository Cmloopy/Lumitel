package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.data.api.CommentApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.comment.Comment

class CommentRepository {
    private val timestamp = System.currentTimeMillis().toString()
    private val mpw = "9EBB7AE993E7FCDFA600E108CC21A259"
    private val security = ""

    private val apiCommentService = RetrofitClient.instance.create(CommentApi::class.java)
    suspend fun getCommentVideo(contentId:Int, msisdn: String): List<Comment>{
        val result = apiCommentService.getCommentVideo(contentId,msisdn,0,20,timestamp,"")
        return result.data
    }
}