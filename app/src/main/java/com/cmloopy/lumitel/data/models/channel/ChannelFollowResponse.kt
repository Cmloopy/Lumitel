package com.cmloopy.lumitel.data.models.channel

import com.google.gson.annotations.SerializedName

data class ChannelFollowResponse (
    @SerializedName("data") val data: List<Channel>,
    @SerializedName("code") val code: Int,
    @SerializedName("desc") val desc: String
)