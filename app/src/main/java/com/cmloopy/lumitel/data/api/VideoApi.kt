package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.video.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {
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
}