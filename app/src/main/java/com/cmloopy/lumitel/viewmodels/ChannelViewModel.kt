package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.channel.Channel
import com.cmloopy.lumitel.data.repository.ChannelRepository
import kotlinx.coroutines.launch

class ChannelViewModel: ViewModel() {
    private val channelRepository = ChannelRepository()
    private val _channel = MutableLiveData<Channel>()
    val channel : LiveData<Channel> get() = _channel

    fun setChannelId(channelId: Int, msisdn:String){
        if(channelId >= 0) {
            getInfoChannel(channelId, msisdn)
        } else {
            getInfoMyChannel(msisdn)
        }
    }
    private fun getInfoChannel(channelId: Int, msisdn: String){
        viewModelScope.launch {
            try {
                val resultList = channelRepository.getInfoChannel(channelId = channelId, msisdn = msisdn)
                _channel.value = resultList
            } catch (e:Exception){
                e.printStackTrace()
                _channel.value = Channel(-1,"","","","",null, "", 0,0,0,0L,0,0,"", "")
            }
        }
    }
    private fun getInfoMyChannel(msisdn: String){
        viewModelScope.launch {
            try {
                val resultList = channelRepository.getInfoMyChannel(msisdn = msisdn)
                _channel.value = resultList.data
            } catch (e:Exception){
                e.printStackTrace()
                _channel.value = Channel(-1,"","","","",null, "", 0,0,0,0L,0,0,"", "")
            }
        }
    }
}