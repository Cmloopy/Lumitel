package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.data.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoChannelViewModel : ViewModel() {
    private val videoRepository = VideoRepository()
    private val _videol = MutableLiveData<List<Video>>()
    val videol: LiveData<List<Video>> get() = _videol
    fun setChannelId(channelId: Int, msisdn: String){
        getVideoByChannel(channelId, msisdn = msisdn)
    }

    private fun getVideoByChannel(channelId: Int, msisdn: String) {
        viewModelScope.launch {
            try {
                val result = videoRepository.getVideoByChannel(channelId, msisdn = msisdn)
                val safeList = result.map { it.copy(aspecRatio = it.aspecRatio ?: "0.5") }
                _videol.value = safeList
            } catch (e:Exception){
                e.printStackTrace()
            }
        }

    }
}