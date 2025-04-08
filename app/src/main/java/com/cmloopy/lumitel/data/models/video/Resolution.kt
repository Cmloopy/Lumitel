package com.cmloopy.lumitel.data.models.video

import com.google.gson.annotations.SerializedName
data class Resolution(
    @SerializedName("key") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("video_path") val videoPath: String
)