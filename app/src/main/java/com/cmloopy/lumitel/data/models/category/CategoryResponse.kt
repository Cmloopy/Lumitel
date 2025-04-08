package com.cmloopy.lumitel.data.models.category

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @SerializedName("data") val data: List<Category>,
    @SerializedName("code") val code: Int,
    @SerializedName("desc") val desc: String,
)