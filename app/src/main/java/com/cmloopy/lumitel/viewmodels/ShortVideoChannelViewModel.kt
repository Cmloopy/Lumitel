package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.repository.VideoRepository

class ShortVideoChannelViewModel : ViewModel() {
    private val videoRepo = VideoRepository()
    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos
    init {
        getShortVideoChannelExample()
    }

    private fun getShortVideoChannelExample() {
        val result = videoRepo.listShortChannelTest()
        _videos.value = result
    }
}