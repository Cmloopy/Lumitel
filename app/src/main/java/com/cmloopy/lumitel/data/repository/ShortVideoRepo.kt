package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.ShortVideo

class ShortVideoRepo {
    fun getShortExample(): List<ShortVideo>{
        return listOf(
            ShortVideo(R.raw.vd1, "Quán lẩu mà sinh viên cực thích", 12, 131,12, 31, R.drawable.nen1),
            ShortVideo(R.raw.vd2, "Quán lẩu mà sinh viên cực ghet", 24,323,32,13, R.drawable.nen2),
            ShortVideo(R.raw.vd3, "Quán lẩu mà sinh viên cực che", 36,42,53,87, R.drawable.nen3),
            ShortVideo(R.raw.vd4, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            ShortVideo(R.raw.vd5, "Quán lẩu mà sinh viên cực thích", 75, 18,93,26, R.drawable.nen2)
        )
    }
    fun getShort(): List<ShortVideo>{
        return listOf(
            ShortVideo(R.raw.vd5, "Quán lẩu mà sinh viên cực thích", 75, 18,93,26, R.drawable.nen2),
            ShortVideo(R.raw.vd6, "Quán lẩu mà sinh viên cực thích", 12, 131,12, 31, R.drawable.nen1),
            ShortVideo(R.raw.vd7, "Quán lẩu mà sinh viên cực ghet", 24,323,32,13, R.drawable.nen2),
            ShortVideo(R.raw.vd8, "Quán lẩu mà sinh viên cực che", 36,42,53,87, R.drawable.nen3),
            ShortVideo(R.raw.vd9, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            ShortVideo(R.raw.vd10, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            ShortVideo(R.raw.vd11, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            ShortVideo(R.raw.vd12, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            ShortVideo(R.raw.vd13, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            ShortVideo(R.raw.vd14, "Quán lẩu mà sinh viên cực thích", 2342, 1812,93,61, R.drawable.nen3),
            ShortVideo(R.raw.vd15, "Quán lẩu mà sinh viên cực thích", 7500, 812,93,54, R.drawable.nen3)
        )
    }
}