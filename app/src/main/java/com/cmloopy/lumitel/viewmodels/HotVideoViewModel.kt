package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.models.ShortVideo
import com.cmloopy.lumitel.data.models.Video
import com.cmloopy.lumitel.data.repository.ShortVideoRepo
import com.cmloopy.lumitel.data.repository.VideoRepository

class HotVideoViewModel : ViewModel() {
    private val shortRepository = ShortVideoRepo()
    private val repository = VideoRepository()
    private val _videos = MutableLiveData<List<ShortVideo>>()
    private  val __videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<ShortVideo>> get() = _videos
    val videol: LiveData<List<Video>> get() = __videos
    init {
        loadShortVideos()
        loadVideos()
    }

    private fun loadVideos() {
        __videos.value = repository.getVideoExample()
    }

    private fun loadShortVideos() {
        _videos.value = shortRepository.getShortExample()
    }
}