package com.cmloopy.lumitel.data.repository

import android.util.Log
import com.cmloopy.lumitel.data.api.ChannelApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.channel.Channel
import com.cmloopy.lumitel.data.models.channel.ChannelFollowResponse
import com.cmloopy.lumitel.data.models.channel.ChannelResponse
import com.cmloopy.lumitel.utils.Encode

class ChannelRepository {
    private val timestamp = System.currentTimeMillis().toString()

    private val channelApi = RetrofitClient.instance.create(ChannelApi::class.java)
    suspend fun getInfoChannel(channelId: Int, msisdn: String): Channel {
        val result = channelApi.getChannelInfo(channelId = channelId, msisdn, timestamp, "")
        return result.data
    }

    suspend fun getInfoMyChannel(msisdn: String): ChannelResponse{
        val result = channelApi.getMyChannelInfo(msisdn, timestamp)
        return result
    }

    suspend fun createAndUpdateChannel(channelName: String, channelDesc: String, channelAvatar: String, msisdn:String): ChannelResponse{
        return channelApi.createAndUpdateChannel(channelName, channelDesc, channelAvatar, msisdn, timestamp, revision = "")
    }

    suspend fun getListChannelFollow(msisdn: String): List<Channel>{
        val result = channelApi.getListChannelFollow(msisdn, timestamp, "")
        return result.data
    }
}