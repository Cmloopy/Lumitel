package com.cmloopy.lumitel.data.repository

import android.util.Log
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.api.VideoApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.channel.Channel
import com.cmloopy.lumitel.data.models.video.Video
import com.google.gson.Gson

class VideoRepository {

    private val msisdn = "+84338363931"
    private val timestamp = System.currentTimeMillis().toString()

    private val listVideoHot = mutableListOf<Video>()
    private val listVideoByCategory = mutableListOf<Video>()
    private val listVideoByChannel = mutableListOf<Video>()
    private val listShortByChannel = mutableListOf<Video>()

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
    suspend fun getInfoVideo(idVideo: Int) : Video {
        val result = apiVideoService.getInfoVideo(idVideo = idVideo, msisdn, timestamp, "")
        return result.data
    }

    suspend fun getVideoByChannel(channelId: Int): List<Video>{
        val result = apiVideoService.getVideoByChannel(channelId = channelId, msisdn, "", 0, 20, timestamp,"")
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listVideoByChannel.add(it)
            }
        }
        return listVideoByChannel

    }
    suspend fun getShortByChannel(channelId: Int): List<Video>{
        val result = apiVideoService.getShortByChannel(channelId, msisdn,"", 0, 20, timestamp, "")
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listShortByChannel.add(it)
            }
        }
        return listShortByChannel
    }

    //Test
    fun listShortChannelTest(): List<Video>{
        return listOf(Video(0,1,1,"","","", "nen1","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen2","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen3","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen1","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen2","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen3","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen1","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen2","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen3","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen1","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen2","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        Video(0,1,1,"","","", "nen3","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null)
        )
    }
    fun listVideoChannelTest(): List<Video>{
        return listOf(
            Video(0,1,1,"","","", "img_eok","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_adqvcd","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_ksea","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_ksea","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_eok","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_eok","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_ksea","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_adqvcd","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_ksea","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_adqvcd","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
            Video(0,1,1,"","","", "img_eok","","","",1,0,0,0,123123,123,24,123,123,1,"",1,1,1,null, Channel(1,"","","","",1,"",1,1,1,1L,1,1,"",""),"","",1,1,1,null),
        )
    }
}