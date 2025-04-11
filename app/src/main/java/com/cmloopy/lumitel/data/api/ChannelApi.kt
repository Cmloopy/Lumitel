package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.channel.ChannelResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ChannelApi {
    @GET("v1/channel/{channelId}/info")
    suspend fun getChannelInfo(
        @Path("channelId") channelId: Int,
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): ChannelResponse
}