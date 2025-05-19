package com.cmloopy.lumitel.utils

import java.text.DecimalFormat

object ViewTimeFormat {
    fun formatTime(time: String): String{
        val uploadTime = time.toInt()
        val minute = uploadTime / 60
        val sec = uploadTime % 60
        return if(sec > 9) {
            "$minute:$sec"
        } else {
            "$minute:0$sec"
        }
    }
    fun formatTimeDuration(milliseconds: Long): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30
        val years = days / 365

        return when {
            seconds < 60 -> "vừa xong"
            minutes < 60 -> "$minutes phút trước"
            hours < 24 -> "$hours giờ trước"
            days < 30 -> "$days ngày trước"
            months < 12 -> "$months tháng trước"
            else -> "$years năm trước"
        }
    }
    fun getTotalView(totalView: Int): String {
        val df = DecimalFormat("#.#")

        val nView = totalView / 1000.0
        val mView = totalView / 1000000.0
        val bView = totalView / 1000000000.0

        return when {
            totalView < 1000 -> "$totalView lượt xem"
            totalView < 1_000_000 -> "${df.format(nView)}K lượt xem"
            totalView < 1_000_000_000 -> "${df.format(mView)}M lượt xem"
            else -> "${df.format(bView)}T lượt xem"
        }
    }
    fun formatTotal(totalView: Int): String {
        val df = DecimalFormat("#.#")

        val nView = totalView / 1000.0
        val mView = totalView / 1000000.0
        val bView = totalView / 1000000000.0

        return when {
            totalView < 1000 -> "$totalView"
            totalView < 1_000_000 -> "${df.format(nView)}K"
            totalView < 1_000_000_000 -> "${df.format(mView)}M"
            else -> "${df.format(bView)}T"
        }
    }
}