package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.models.Comment
import com.cmloopy.lumitel.data.models.ShortVideo
import com.cmloopy.lumitel.data.repository.CommentRepository
import com.cmloopy.lumitel.data.repository.ShortVideoRepo

class ShortViewModel: ViewModel() {
    private val repository = ShortVideoRepo()
    private val commentRepo = CommentRepository()
    private val _videos = MutableLiveData<List<ShortVideo>>()
    private val _comments = MutableLiveData<List<Comment>>()
    val videos: LiveData<List<ShortVideo>> get() = _videos
    val comments: LiveData<List<Comment>> get() = _comments
    init {
        loadVideos()
        loadComment()
    }

    private fun loadComment() {
        _comments.value = commentRepo.getCommentExample()
    }

    private fun loadVideos() {
        _videos.value = repository.getShort()
    }
}