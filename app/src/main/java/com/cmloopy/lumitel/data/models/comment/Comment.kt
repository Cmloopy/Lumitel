package com.cmloopy.lumitel.data.models.comment

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id:String,
    @SerializedName("commentId") val commentId:String,
    @SerializedName("msisdn") val msisdn:String,
    @SerializedName("name") val name:String,
    @SerializedName("contentId") val contentId:Int,
    @SerializedName("content") val content:String,
    @SerializedName("avatar") val avatar:String,
    @SerializedName("commentAt") val commentAt:Long,
    @SerializedName("serverTime") val serverTime:Long,
    @SerializedName("count") val count:Int,
    @SerializedName("action") val action: String?,
    @SerializedName("level") val level:Int,
    @SerializedName("like") val like:Int,
    @SerializedName("dislike") val dislike:Int
)