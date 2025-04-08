package com.cmloopy.lumitel.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    private const val DOMAIN = "https://lumitelapi.ringme.vn"
    private const val CONTEXT_PATH_VIDEO_DEV = "/video-service-v2/"
    private const val BASE_URL = DOMAIN + CONTEXT_PATH_VIDEO_DEV
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}