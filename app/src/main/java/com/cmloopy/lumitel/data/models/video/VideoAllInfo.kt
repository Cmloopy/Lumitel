package com.cmloopy.lumitel.data.models.video

import com.google.gson.annotations.SerializedName

data class VideoAllInfo (
    @SerializedName("data") val data: Video,
    @SerializedName("code") val code: Int,
    @SerializedName("desc") val desc: String
)