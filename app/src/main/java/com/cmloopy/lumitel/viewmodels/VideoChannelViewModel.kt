package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.repository.VideoRepository

class VideoChannelViewModel : ViewModel() {
    private val videoRepository = VideoRepository()
    private val _videol = MutableLiveData<List<Video>>()
    val videol: LiveData<List<Video>> get() = _videol
    init {
        getVideoShortExample()
    }

    private fun getVideoShortExample() {
        val result = videoRepository.listVideoChannelTest()
        _videol.value = result
    }
}