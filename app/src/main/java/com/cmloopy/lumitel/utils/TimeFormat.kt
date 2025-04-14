package com.cmloopy.lumitel.utils

object TimeFormat {
    fun formatTime(time: String): String{
        val timee = time.toInt()
        val minute = timee / 60
        val sec = timee % 60
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
}