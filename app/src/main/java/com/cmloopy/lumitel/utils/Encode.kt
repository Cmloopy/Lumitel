package com.cmloopy.lumitel.utils

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Encode {
    fun url(value: String): String {
        return try {
            URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            value
        }
    }
}
