package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.Video
import retrofit2.http.GET

interface VideoApi {
    @GET("ex")
    suspend fun getVideoList(): List<Video>
}