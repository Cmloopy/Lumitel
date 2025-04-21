package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.data.api.VideoApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.video.UploadVideoResponse
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.models.video.VideoAllInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class VideoRepository {

    private val timestamp = System.currentTimeMillis().toString()
    private val mpw = "9EBB7AE993E7FCDFA600E108CC21A259"
    private val security = ""

    private val listVideoHot = mutableListOf<Video>()
    private val listVideoByCategory = mutableListOf<Video>()
    private val listVideoByChannel = mutableListOf<Video>()
    private val listShortByChannel = mutableListOf<Video>()

    private val apiVideoService = RetrofitClient.instance.create(VideoApi::class.java)

    suspend fun getVideoHot(msisdn: String): List<Video>{
        val result = apiVideoService.getVideoHot(msisdn, "", timestamp, "", 0, 20)
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listVideoHot.add(it)
            }
        }
        return listVideoHot
    }
    suspend fun getVideoByCategory(idCategory: Int, msisdn: String): List<Video>{
        val result = apiVideoService.getVideoByCategory(idCategory = idCategory, msisdn, "", timestamp, "", 0, 20)
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listVideoByCategory.add(it)
            }
        }
        return listVideoByCategory
    }
    suspend fun getInfoVideo(idVideo: Int, msisdn:String) : Video {
        val result = apiVideoService.getInfoVideo(idVideo = idVideo, msisdn, timestamp, "")
        return result.data
    }

    suspend fun getVideoByChannel(channelId: Int, msisdn: String): List<Video>{
        val result = apiVideoService.getVideoByChannel(channelId = channelId, msisdn, "", 0, 20, timestamp,"")
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listVideoByChannel.add(it)
            }
        }
        return listVideoByChannel

    }
    suspend fun getShortByChannel(channelId: Int, msisdn: String): List<Video>{
        val result = apiVideoService.getShortByChannel(channelId, msisdn,"", 0, 20, timestamp, "")
        if(result.data.isNotEmpty()) {
            result.data.forEach {
                listShortByChannel.add(it)
            }
        }
        return listShortByChannel
    }
    suspend fun uploadVideo(msisdn: String, fName: String, uFile: MultipartBody.Part): UploadVideoResponse {
        return apiVideoService.uploadVideo(
            msisdn.toPlainRequestBody(),
            timestamp.toPlainRequestBody(),
            security.toPlainRequestBody(),
            mpw.toPlainRequestBody(),
            fName.toPlainRequestBody(),
            uFile)
    }

    suspend fun uploadImage(msisdn: String, fName: String, uFile: MultipartBody.Part): UploadVideoResponse {
        return apiVideoService.uploadVideo(
            msisdn.toPlainRequestBody(),
            timestamp.toPlainRequestBody(),
            security.toPlainRequestBody(),
            mpw.toPlainRequestBody(),
            fName.toPlainRequestBody(),
            uFile)
    }

    suspend fun createVideo(msisdn: String, categoryId: Int, videoTitle: String, videoDesc: String, imageUrl: String, videoUrl: String): VideoAllInfo {
        return apiVideoService.createVideo(msisdn, timestamp, categoryId, videoTitle, videoDesc, imageUrl, videoUrl, security)
    }
    //RequestBody
    fun String.toPlainRequestBody(): RequestBody {
        return this.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}