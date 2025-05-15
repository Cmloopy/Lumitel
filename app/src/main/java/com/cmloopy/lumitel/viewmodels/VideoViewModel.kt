package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.channel.Channel
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.repository.ChannelRepository
import com.cmloopy.lumitel.data.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val channelRepo = ChannelRepository()
    private val videoRepo = VideoRepository()
    private val _videos = MutableLiveData<List<Video>>()
    private val _channel = MutableLiveData<Channel>()
    private val _isFollow = MutableLiveData<Int>()
    val videos: LiveData<List<Video>> get() = _videos
    val channel :LiveData<Channel>get() = _channel
    val isFollow: LiveData<Int> get() = _isFollow
    fun getInfoChannel(msisdn: String){
        viewModelScope.launch {
            try {
                val result = channelRepo.getInfoMyChannel(msisdn)
                _channel.value = result.data
            }catch (e:Exception){
                e.printStackTrace()
                _channel.value = Channel(-1,"","","","",null, "", 0,0,0,0L,0,0,"", "")
            }
        }
    }
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
    fun setStatusFollow(isFollow: Int){
        _isFollow.value = isFollow
    }
    fun followChannel(channelId: Int, msisdn: String){
        var noneStatus: Int = -1
        viewModelScope.launch {
            try {
                if(_isFollow.value == 1){
                    noneStatus = 1
                    val result = channelRepo.unFollowChannel(channelId, msisdn)
                    if(result.code == 200){
                        _isFollow.value = 0
                    }
                } else {
                    noneStatus = 0
                    val result = channelRepo.followChannel(channelId, msisdn)
                    if(result.code == 200){
                        _isFollow.value = 1
                    }
                }
            } catch (e:Exception){
                e.printStackTrace()
                _isFollow.value = noneStatus
            }
        }
    }
}