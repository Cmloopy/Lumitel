package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.comment.CommentResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentApi {
    @GET("v1/comment/list")
    suspend fun getCommentVideo(
        @Query("contentId") contentId: Int,
        @Query("msisdn") msisdn: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): CommentResponse
}