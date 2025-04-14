package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.repository.VideoRepository
import kotlinx.coroutines.launch

class ShortVideoChannelViewModel : ViewModel() {
    private val videoRepo = VideoRepository()
    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos
    fun setChannelId(channelId: Int){
        getShortByChannel(channelId)
    }

    private fun getShortByChannel(channelId: Int) {
        viewModelScope.launch {
            try {
                val resultList = videoRepo.getShortByChannel(channelId = channelId)
                val safeList = resultList.map { it.copy(aspecRatio = it.aspecRatio ?: "1.5") }
                _videos.value = safeList
            } catch (e:Exception){
                e.printStackTrace()
                _videos.value = emptyList()
            }
        }
    }

}