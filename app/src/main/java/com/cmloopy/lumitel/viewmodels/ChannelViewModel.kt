package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.ViewModel
import com.cmloopy.lumitel.data.repository.ChannelRepository

class ChannelViewModel: ViewModel() {
    private val channelRepository = ChannelRepository()
}