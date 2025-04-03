package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.models.Comment
import com.cmloopy.lumitel.data.models.Video
import com.cmloopy.lumitel.data.repository.CommentRepository
import com.cmloopy.lumitel.data.repository.VideoRepository

class ShortViewModel: ViewModel() {
    private val repository = VideoRepository()
    private val commentRepo = CommentRepository()
    private val _videos = MutableLiveData<List<Video>>()
    private val _videol = MutableLiveData<List<Video>>()
    private val _comments = MutableLiveData<List<Comment>>()
    val videos: LiveData<List<Video>> get() = _videos
    val videol: LiveData<List<Video>> get() = _videol
    val comments: LiveData<List<Comment>> get() = _comments
    init {
        loadVideos()
        loadVideol()
        loadComment()
    }

    private fun loadVideol() {
        _videol.value = repository.getVideoExample()
    }

    private fun loadComment() {
        _comments.value = commentRepo.getCommentExample()
    }

    private fun loadVideos() {
        _videos.value = repository.getShort()
    }
}