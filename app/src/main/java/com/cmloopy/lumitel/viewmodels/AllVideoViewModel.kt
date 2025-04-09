package com.cmloopy.lumitel.viewmodels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.repository.VideoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AllVideoViewModel : ViewModel() {
    private val videoRepository = VideoRepository()
    private val _videos = MutableLiveData<List<Video>>()
    private val _videol = MutableLiveData<List<Video>>()
    private val _resultList = MutableLiveData<List<Video>>()
    private val _isEmptyS = MutableLiveData<Boolean>()
    private val _isEmptyL = MutableLiveData<Boolean>()
    private val _isLoadingS = MutableLiveData<Boolean>()
    private val _isLoadingL = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>()
    val videos: LiveData<List<Video>> get() = _videos
    val videol: LiveData<List<Video>> get() = _videol
    val resultList: LiveData<List<Video>> get() = _resultList
    val isLoading: LiveData<Boolean> get() = _isLoading
    val isEmptyS: LiveData<Boolean> get() = _isEmptyS
    val isEmptyL: LiveData<Boolean> get() = _isEmptyL
    init{
        _isLoadingS.value = true
        _isLoadingL.value = true
        _isEmptyS.value = true
        _isEmptyL.value = true
        _isLoading.value = true
    }
    fun setCategory(idCategory: Int) {
        if (idCategory == -1) {
            loadHotVideos()
        } else {
            loadVideoByCategory(idCategory)
        }
    }
    private fun loadHotVideos() {
        viewModelScope.launch {
            try {
                val resultList = videoRepository.getVideoHot()
                val safeList = resultList.map { it.copy(aspecRatio = it.aspecRatio ?: "1.5") }
                _videos.value = safeList.filter { it.aspecRatio.toFloat() >= 1.0f }
                _videol.value = safeList.filter { it.aspecRatio.toFloat() < 1.0f }
                if(_videos.value.isNullOrEmpty()) _isEmptyS.value = true else _isEmptyS.value = false
                if(_videol.value.isNullOrEmpty()) _isEmptyL.value = true else _isEmptyL.value = false
                _resultList.value = safeList
            } catch (e: Exception) {
                _videos.value = emptyList()
                _videol.value = emptyList()
                _resultList.value = emptyList()
            }
        }
    }

    private fun loadVideoByCategory(idCategory: Int) {
        viewModelScope.launch {
            try {
                val resultList = videoRepository.getVideoByCategory(idCategory)
                val safeList = resultList.map { it.copy(aspecRatio = it.aspecRatio ?: "1.5") }

                _videos.value = safeList.filter { it.aspecRatio.toFloat() >= 1.0f }
                _videol.value = safeList.filter { it.aspecRatio.toFloat() < 1.0f }
                _resultList.value = safeList
                if(_videos.value.isNullOrEmpty()) _isEmptyS.value = true else _isEmptyS.value = false
                if(_videol.value.isNullOrEmpty()) _isEmptyL.value = true else _isEmptyL.value = false
            } catch (e: Exception) {
                _videos.value = emptyList()
                _videol.value = emptyList()
                if(_videos.value == null) _isEmptyS.value = true
                if(_videol.value == null) _isEmptyL.value = true
                _resultList.value = emptyList()
            }
        }
    }
    fun setLoadingS(){
        _isLoadingS.value = !_isLoadingS.value!!
    }
    fun setLoadingL(){
        _isLoadingL.value = !_isLoadingL.value!!
    }
    fun setLoading(){
        if(_isLoadingS.value == _isLoadingL.value && _isLoadingS.value == false){
            _isLoading.value = false
        } else _isLoading.value = true
    }
}