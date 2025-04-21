package com.cmloopy.lumitel.data.models.video

import com.google.gson.annotations.SerializedName

data class UploadVideoResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("desc") val desc: String,
    @SerializedName("mediaPath") val mediaPath: String,
    @SerializedName("mediaUrl") val mediaUrl: String
)