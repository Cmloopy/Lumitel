package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.Comment
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.repository.CommentRepository
import com.cmloopy.lumitel.data.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoViewModel: ViewModel() {
    private val videoRepo = VideoRepository()
    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos
    fun updateId(idCate: Int, idVideo: Int, isFromChannel: Boolean, idChannel: Int, isShort: Boolean, msisdn: String){
        if(!isFromChannel){
            if(idCate == -1){
                loadVideoHot(idVideo, msisdn)
            }
            else{
                loadVideos(idVideo, idCate, msisdn)
            }
        }
        else {
            loadVideoFromChannel(idVideo, idChannel, isShort, msisdn)
        }
    }

    private fun loadVideoHot(idVideo: Int, msisdn: String) {
        viewModelScope.launch {
            try {
                val finalList = mutableListOf<Video>()
                val firstVideo = videoRepo.getInfoVideo(idVideo = idVideo, msisdn)
                finalList.add(firstVideo)
                val result = videoRepo.getVideoHot(msisdn)
                val safeList = result.map { it.copy(aspecRatio = it.aspecRatio ?: "1.5") }
                safeList.forEach {
                    finalList.add(it)
                }
                _videos.value = finalList
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadVideos(idVideo: Int, idCate: Int, msisdn: String) {
        viewModelScope.launch {
            try {
                val finalList = mutableListOf<Video>()
                val firstVideo = videoRepo.getInfoVideo(idVideo = idVideo, msisdn)
                finalList.add(firstVideo)
                val result = videoRepo.getVideoByCategory(idCategory = idCate, msisdn)
                val safeList = result.map { it.copy(aspecRatio = it.aspecRatio ?: "1.5") }
                safeList.forEach {
                    finalList.add(it)
                }
                _videos.value = finalList
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    private fun loadVideoFromChannel(idVideo: Int, idChannel: Int, isShort: Boolean, msisdn: String){
        viewModelScope.launch {
            try {
                val finalList = mutableListOf<Video>()
                val firstVideo = videoRepo.getInfoVideo(idVideo = idVideo, msisdn)
                finalList.add(firstVideo)
                if (!isShort) {
                    val result = videoRepo.getVideoByChannel(channelId =  idChannel, msisdn)
                    val safeList = result.map { it.copy(aspecRatio = it.aspecRatio ?: "1.5") }
                    safeList.forEach {
                        finalList.add(it)
                    }
                    _videos.value = finalList
                } else {
                    val result = videoRepo.getShortByChannel(channelId =  idChannel, msisdn)
                    val safeList = result.map { it.copy(aspecRatio = it.aspecRatio ?: "1.5") }
                    safeList.forEach {
                        finalList.add(it)
                    }
                    _videos.value = finalList
                }

            } catch (e: Exception){
                _videos.value = emptyList()
                e.printStackTrace()
            }
        }
    }
}