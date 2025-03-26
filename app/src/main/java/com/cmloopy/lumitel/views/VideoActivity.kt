package com.cmloopy.lumitel.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.adapter.VideoAdapter
import com.cmloopy.lumitel.databinding.ActivityVideoBinding
import com.cmloopy.lumitel.viewmodels.VideoViewModel

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var adapter: VideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        adapter = VideoAdapter(this, emptyList())
        binding.viewpagerVideo.adapter = adapter

        observeViewModel()

        binding.btnCloseActivityVideo.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }

    private fun observeViewModel() {
        viewModel.videol.observe(this){videos ->
            adapter.updateData(videos)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        adapter.releaseVideo()
    }

    override fun onPause() {
        super.onPause()
        adapter.pauseVideo()
    }

    override fun onResume() {
        super.onResume()
        adapter.resumeVideo()
    }
}