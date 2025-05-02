package com.cmloopy.lumitel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cmloopy.lumitel.databinding.ActivityVideoViewRemakeBinding

class VideoViewActivityRemake : AppCompatActivity() {
    private lateinit var binding: ActivityVideoViewRemakeBinding
    private val viewModel: VideoViewModelRemake by viewModels()
    private lateinit var adapter : VideoViewAdapter
    private var idCategory = -1
    private var idVideo = -1
    private var isFromChannel = false
    private var idChannel = -1
    private var isShort = false
    private var msisdn: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoViewRemakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        idCategory = intent.getIntExtra("idCategory", -1)
        idVideo = intent.getIntExtra("idVideo", -1)
        isFromChannel = intent.getBooleanExtra("isFromChannel", false)
        idChannel = intent.getIntExtra("idChannel", -1)
        isShort = intent.getBooleanExtra("isShort", false)
        msisdn = intent.getStringExtra("msisdn")?:"0"

        viewModel.updateId(idCategory, idVideo, isFromChannel, idChannel, isShort, msisdn!!)
        viewModel.getInfoChannel(msisdn!!)

        adapter = VideoViewAdapter(emptyList())

        obserViewModel()
    }
    private fun obserViewModel() {
        viewModel.videos.observe(this) { videos ->
            adapter = VideoViewAdapter(videos)
            binding.vpgShortVideoRemake.adapter = adapter
        }
    }
    override fun onPause() {
        super.onPause()
        adapter.stopVideo()
    }
    override fun onResume() {
        super.onResume()
        adapter.resumeVideo()
    }
}