package com.cmloopy.lumitel.data.api

import com.cmloopy.lumitel.data.models.category.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CategoryApi {
    @GET("v1/category/list/v2")
    suspend fun getAllCategory(
        @Query("msisdn") msisdn: String,
        @Query("lastHashId") lastHashId: String = "",
        @Query("timestamp") timestamp: String,
        @Query("security") security: String = "",
        @Header("Accept-language") language: String = "en",
        @Header("Client-Type") clientType: String = "Android",
        @Header("sec-api") secApi: String = "123"
    ): CategoryResponse
}