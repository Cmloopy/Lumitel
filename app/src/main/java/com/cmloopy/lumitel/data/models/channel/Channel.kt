package com.cmloopy.lumitel.data.models.channel

import com.google.gson.annotations.SerializedName
data class Channel(
    @SerializedName("id") val id: Int,
    @SerializedName("msisdn") val msisdn: String,
    @SerializedName("channelName") val channelName: String,
    @SerializedName("channelAvatar") val channelAvatar: String,
    @SerializedName("headerBanner") val headerBanner: String?,
    @SerializedName("actived") val actived: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("numFollows") val numFollows: Int,
    @SerializedName("numVideos") val numVideos: Int,
    @SerializedName("isOfficial") val isOfficial: Int?,
    @SerializedName("createdFrom") val createdFrom: Long?,
    @SerializedName("isFollow") var isFollow: Int,
    @SerializedName("isOwner") val isOwner: Int,
    @SerializedName("url") val url: String?,
    @SerializedName("state") val state: String?
)
