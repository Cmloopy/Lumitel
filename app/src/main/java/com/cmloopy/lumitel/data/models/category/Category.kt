package com.cmloopy.lumitel.data.models.category

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id") val id: Int,
    @SerializedName("cateName") var cateName: String,
    @SerializedName("iconImage") val iconImage: String,
    @SerializedName("headerBanner") val headerBanner: String,
    @SerializedName("description") val description: String
)