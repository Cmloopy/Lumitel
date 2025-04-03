package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.Video

class VideoRepository {
    fun getShortExample(): List<Video>{
        return listOf(
            Video(R.raw.vd1, "Quán lẩu mà sinh viên cực thích", 12, 131,12, 31, R.drawable.nen1),
            Video(R.raw.vd2, "Quán lẩu mà sinh viên cực ghet", 24,323,32,13, R.drawable.nen2),
            Video(R.raw.vd3, "Quán lẩu mà sinh viên cực che", 36,42,53,87, R.drawable.nen3),
            Video(R.raw.vd4, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            Video(R.raw.vd5, "Quán lẩu mà sinh viên cực thích", 75, 18,93,26, R.drawable.nen2)
        )
    }
    fun getShort(): List<Video>{
        return listOf(
            Video(R.raw.vdtest, "", 3,1,12,12, R.drawable.nen2),
            Video(R.raw.vd5, "Quán lẩu mà sinh viên cực thích", 75, 18,93,26, R.drawable.nen2),
            Video(R.raw.vd6, "Quán lẩu mà sinh viên cực thích", 12, 131,12, 31, R.drawable.nen1),
            Video(R.raw.vd7, "Quán lẩu mà sinh viên cực ghet", 24,323,32,13, R.drawable.nen2),
            Video(R.raw.vd8, "Quán lẩu mà sinh viên cực che", 36,42,53,87, R.drawable.nen3),
            Video(R.raw.vd9, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            Video(R.raw.vd10, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            Video(R.raw.vd11, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            Video(R.raw.vd12, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            Video(R.raw.vd13, "Quán lẩu mà sinh viên k di", 51,16,64,21, R.drawable.nen1),
            Video(R.raw.vd14, "Quán lẩu mà sinh viên cực thích", 2342, 1812,93,61, R.drawable.nen3),
            Video(R.raw.vd15, "Quán lẩu mà sinh viên cực thích", 7500, 812,93,54, R.drawable.nen3)
        )
    }
    fun getVideoExample(): List<Video>{
        return listOf(
            Video(R.raw.eok,"EM ỔN KHÔNG REMIX - SOÁI NHI x ORINN x GUANG", 75, 23, 53, 12, R.drawable.img_eok),
            Video(R.raw.adqvcd,"ANH ĐÃ QUEN VỚI CÔ ĐƠN REMIX - SOOBIN x ORINN x GUANG", 75, 212, 12, 34, R.drawable.img_adqvcd),
            Video(R.raw.adqvcd,"KHÔNG SAO EM À REMIX - ĐINH TÙNG HUY x ORINN", 123, 412,123 ,123, R.drawable.img_ksea),
            Video(R.raw.eok,"EM ỔN KHÔNG REMIX - SOÁI NHI x ORINN x GUANG", 234, 234,123,312, R.drawable.img_eok),
            Video(R.raw.ksea,"KHÔNG SAO EM À REMIX - ĐINH TÙNG HUY x ORINN",13,123,23,123, R.drawable.img_ksea)
        )
    }
}