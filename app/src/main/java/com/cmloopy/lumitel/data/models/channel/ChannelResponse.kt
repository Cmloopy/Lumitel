package com.cmloopy.lumitel.data.models.channel

import com.google.gson.annotations.SerializedName

data class ChannelResponse(
    @SerializedName("data") val data: Channel,
    @SerializedName("code") val code: Int,
    @SerializedName("desc") val desc: String
)