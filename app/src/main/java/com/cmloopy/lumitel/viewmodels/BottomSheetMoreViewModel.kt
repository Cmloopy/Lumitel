package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.channel.ChannelResponse
import com.cmloopy.lumitel.data.repository.ChannelRepository
import kotlinx.coroutines.launch

class BottomSheetMoreViewModel: ViewModel() {
    private val channelRepository = ChannelRepository()
    private var _channelRes = MutableLiveData<ChannelResponse>()
    private var _existChannel = MutableLiveData<Boolean>()
    private val channelRes: LiveData<ChannelResponse> get() = _channelRes
    val existChannel : LiveData<Boolean> get() = _existChannel
    fun setIdUser(msisdn: String){
        checkChannel(msisdn)
    }

    private fun checkChannel(msisdn: String) {
        viewModelScope.launch {
            try {
                val result = channelRepository.getInfoMyChannel(msisdn = msisdn)
                _channelRes.value = result
                _existChannel.value = channelRes.value?.code == 200

            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}