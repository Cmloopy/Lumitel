package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.models.Video
import com.cmloopy.lumitel.data.repository.VideoRepository

class VideoViewModel: ViewModel() {
    private val videoRepository = VideoRepository()
    private val _videos = MutableLiveData<List<Video>>()
    val videol: LiveData<List<Video>> get() = _videos
    init {
        loadVideos()
    }

    private fun loadVideos() {
        _videos.value = videoRepository.getVideoExample()
    }
}