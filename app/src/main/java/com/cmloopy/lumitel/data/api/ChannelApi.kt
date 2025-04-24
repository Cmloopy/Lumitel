package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.channel.ChannelFollowResponse
import com.cmloopy.lumitel.data.models.channel.ChannelResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

    @GET("v1/user/channel/mine")
    suspend fun getMyChannelInfo(
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Query("clientType") client_Type: String = "Android",
        @Query("revision") revision: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): ChannelResponse

    @FormUrlEncoded
    @POST("v1/user/channel/push")
    suspend fun createAndUpdateChannel(
        @Field("channelName") channelName: String,
        @Field("channelDesc") channelDesc: String,
        @Field("channelAvatar") channelAvatar: String,
        @Field("msisdn") msisdn: String,
        @Field("timestamp") timestamp: String,
        @Field("security") security: String = "",
        @Field("clientType") clientType: String = "Android",
        @Field("revision") revision: String,
        @Header("Accept-Language") language: String = "en",
        @Header("sec-api") secApi: String = "123"
    ): ChannelResponse

    @GET("v1/channel/following")
    suspend fun getListChannelFollow(
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): ChannelFollowResponse

    @GET("v1/channel/{channelId}/follow")
    suspend fun followChannel(
        @Path("channelId") channelId: Int,
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): ChannelResponse

    @GET("v1/channel/{channelId}/unfollow")
    suspend fun unFollowChannel(
        @Path("channelId") channelId: Int,
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): ChannelResponse
}