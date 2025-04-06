package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.Video
import retrofit2.http.GET

interface ShortVideoApi {
    @GET("ex")
    suspend fun getShortList(): List<Video>
    @GET("")
    suspend fun getShortEx(): List<Video>
}