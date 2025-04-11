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
    private val commentRepo = CommentRepository()
    private val _videos = MutableLiveData<List<Video>>()
    private val _comments = MutableLiveData<List<Comment>>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    val videos: LiveData<List<Video>> get() = _videos
    val comments: LiveData<List<Comment>> get() = _comments
    fun updateId(idCate: Int, idVideo: Int){
        if(idCate == -1){
            loadVideoHot(idVideo)
        }
        else{
            loadVideos(idVideo, idCate)
        }
    }

    private fun loadVideoHot(idVideo: Int) {
        viewModelScope.launch {
            try {
                val finalList = mutableListOf<Video>()
                val firstVideo = videoRepo.getInfoVideo(idVideo = idVideo)
                finalList.add(firstVideo)
                val result = videoRepo.getVideoHot()
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

    fun loadVideos(idVideo: Int, idCate: Int) {
        viewModelScope.launch {
            try {
                val finalList = mutableListOf<Video>()
                val firstVideo = videoRepo.getInfoVideo(idVideo = idVideo)
                finalList.add(firstVideo)
                val result = videoRepo.getVideoByCategory(idCategory = idCate)
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
}