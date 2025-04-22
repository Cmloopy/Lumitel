package com.cmloopy.lumitel.data.models.comment

import com.google.gson.annotations.SerializedName

data class CommentResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<Comment>,
    @SerializedName("desc") val desc: String
)