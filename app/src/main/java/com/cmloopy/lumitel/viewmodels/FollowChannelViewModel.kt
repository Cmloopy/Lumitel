package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.channel.Channel
import com.cmloopy.lumitel.data.repository.ChannelRepository
import kotlinx.coroutines.launch

class FollowChannelViewModel: ViewModel() {
    private val channelRepository = ChannelRepository()
    private val _channels = MutableLiveData<List<Channel>>()

    val channels : LiveData<List<Channel>> get() = _channels
    fun getListChannelFollow(msisdn:String){
        viewModelScope.launch {
            try {
                val result = channelRepository.getListChannelFollow(msisdn)
                _channels.value = result
            }catch (e:Exception){
                e.printStackTrace()
                _channels.value = emptyList()
            }
        }
    }
}