package com.cmloopy.lumitel.data.api.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val DOMAIN = "https://lumitelapi.ringme.vn/"
    private const val CONTEXT_PATH_VIDEO_DEV = "video-service-v2/"
    private const val BASE_URL = DOMAIN + CONTEXT_PATH_VIDEO_DEV

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1200, TimeUnit.SECONDS)
        .readTimeout(600, TimeUnit.SECONDS)
        .writeTimeout(600, TimeUnit.SECONDS)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
