package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.ShortVideo

class ShortVideoRepo {
    fun getShort(): List<ShortVideo>{
        return listOf(
            ShortVideo(R.raw.vd1, "Quán lẩu mà sinh viên cực thích", 12, R.drawable.nen1),
            ShortVideo(R.raw.vd2, "Quán lẩu mà sinh viên cực ghet", 24, R.drawable.nen2),
            ShortVideo(R.raw.vd3, "Quán lẩu mà sinh viên cực che", 36, R.drawable.nen3),
            ShortVideo(R.raw.vd4, "Quán lẩu mà sinh viên k di", 51, R.drawable.nen1),
            ShortVideo(R.raw.vd5, "Quán lẩu mà sinh viên cực thích", 75, R.drawable.nen2)
        )
    }
}