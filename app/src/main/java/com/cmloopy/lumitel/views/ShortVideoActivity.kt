package com.cmloopy.lumitel.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.adapter.ShortAdapter
import com.cmloopy.lumitel.databinding.ActivityShortVideoBinding
import com.cmloopy.lumitel.viewmodels.ShortViewModel

class ShortVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShortVideoBinding
    private val viewModel: ShortViewModel by viewModels()
    private lateinit var adapter: ShortAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShortVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        adapter = ShortAdapter(this, emptyList())
        binding.vpgShortVideo.adapter = adapter

        observeViewModel()

        binding.btnDropBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.videos.observe(this) { videos ->
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
