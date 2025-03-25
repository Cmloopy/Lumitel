package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.data.models.Video

class VideoRepository {
    fun getVideoExample(): List<Video>{
        return listOf(
            Video("EM ỔN KHÔNG REMIX - SOÁI NHI x ORINN x GUANG", R.drawable.img_eok, R.raw.eok, 26128123),
            Video("ANH ĐÃ QUEN VỚI CÔ ĐƠN REMIX - SOOBIN x ORINN x GUANG", R.drawable.img_adqvcd, R.raw.adqvcd, 82128123),
            Video("KHÔNG SAO EM À REMIX - ĐINH TÙNG HUY x ORINN", R.drawable.img_ksea, R.raw.ksea, 1212123),
            Video("EM ỔN KHÔNG REMIX - SOÁI NHI x ORINN x GUANG", R.drawable.img_eok, R.raw.eok, 8128123),
            Video("KHÔNG SAO EM À REMIX - ĐINH TÙNG HUY x ORINN", R.drawable.img_ksea, R.raw.ksea, 5128123)
        )
    }
}