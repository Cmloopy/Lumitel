package com.cmloopy.lumitel.data.models.video

import com.cmloopy.lumitel.data.models.Channel
import com.google.gson.annotations.SerializedName

data class Video(
    /*@SerializedName("id") val id: Int,
    @SerializedName("cateId") val cateId: Int,
    @SerializedName("channelId") val channelId: Int,
    @SerializedName("videoTitle") val videoTitle: String,
    @SerializedName("videoDesc") val videoDesc: String,
    @SerializedName("videoMedia") val videoMedia: String,
    @SerializedName("videoImage") val videoImage: String,
    @SerializedName("imageThumb") val imageThumb: String?,
    @SerializedName("imageSmall") val imageSmall: String?,
    @SerializedName("videoTime") val videoTime: String,
    @SerializedName("actived") val actived: Int,
    @SerializedName("isNew") val isNew: Int,
    @SerializedName("isHot") val isHot: Int,
    @SerializedName("isShort") val isShort: Int,
    @SerializedName("publishTime") val publishTime: Long,
    @SerializedName("totalViews") val totalViews: Int,
    @SerializedName("totalLikes") val totalLikes: Int,
    @SerializedName("totalShares") val totalShares: Int,
    @SerializedName("totalComments") val totalComments: Int,
    @SerializedName("resolution") val resolution: Int,
    @SerializedName("aspecRatio") val aspecRatio: String,
    @SerializedName("isAdaptive") val isAdaptive: Int,
    @SerializedName("isLike") val isLike: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("cate") val cate: Any?,
    @SerializedName("channel") val channel: Channel,
    @SerializedName("url") val url: String,
    @SerializedName("hashId") val hashId: String,
    @SerializedName("is_paid") val isPaid: Int,
    @SerializedName("is_vip") val isVip: Int,
    @SerializedName("is_allow") val isAllow: Int,
    @SerializedName("list_resolution") val listResolution: List<Resolution>?*/
    val urlShort: Int,
    val title: String,
    val view: Int,
    val like: Int,
    val cmt: Int,
    val share: Int,
    val img: Int
)