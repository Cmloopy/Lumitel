package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.data.api.VideoApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.video.Video

class VideoRepository {

    private val msisdn = "+84338363931"
    private val timestamp = System.currentTimeMillis().toString()

    private val listVideoHot = mutableListOf<Video>()
    private val listVideoByCategory = mutableListOf<Video>()

    private val apiVideoService = RetrofitClient.instance.create(VideoApi::class.java)

    suspend fun getVideoHot(): List<Video>{
        val result = apiVideoService.getVideoHot(msisdn, "", timestamp, "", 0, 20)
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listVideoHot.add(it)
            }
        }
        return listVideoHot
    }
    suspend fun getVideoByCategory(idCategory: Int): List<Video>{
        val result = apiVideoService.getVideoByCategory(idCategory = idCategory, msisdn, "", timestamp, "", 0, 20)
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listVideoByCategory.add(it)
            }
        }
        return listVideoByCategory
    }
}