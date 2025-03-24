package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.models.ShortVideo
import com.cmloopy.lumitel.data.repository.ShortVideoRepo

class ShortViewModel: ViewModel() {
    private val repository = ShortVideoRepo()
    private val _videos = MutableLiveData<List<ShortVideo>>()
    val videos: LiveData<List<ShortVideo>> get() = _videos
    init {
        loadVideos()
    }
    private fun loadVideos() {
        _videos.value = repository.getShort()
    }
}