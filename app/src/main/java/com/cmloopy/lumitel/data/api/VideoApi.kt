package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.video.VideoAllInfo
import com.cmloopy.lumitel.data.models.video.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {
    //Lấy list video HOT
    @GET("v1/video/hot/new")
    suspend fun getVideoHot(
        @Query("msisdn") msisdn: String,
        @Query("lastHashId") lastHashId: String = "",
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): VideoResponse

    //Lấy list video theo category
    @GET("v1/video/{idCategory}/cate/v2")
    suspend fun getVideoByCategory(
        @Path("idCategory") idCategory: Int,
        @Query("msisdn") msisdn: String,
        @Query("lastHashId") lastHashId: String = "",
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): VideoResponse

    //Lấy info video theo ID
    @GET("v1/video/{idVideo}/info")
    suspend fun getInfoVideo(
        @Path("idVideo") idVideo: Int,
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ) : VideoAllInfo

    @GET("v1/video/{channelId}/channel/v2")
    suspend fun getVideoByChannel(
        @Path("channelId") channelId: Int,
        @Query("msisdn") msisdn: String,
        @Query("lastHashId") lastHashId: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): VideoResponse

    @GET("v1/video/short/channel/v2")
    suspend fun getShortByChannel(
        @Query("channelId") channelId: Int,
        @Query("msisdn") msisdn: String,
        @Query("lastHashId") lastHashId: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): VideoResponse
}