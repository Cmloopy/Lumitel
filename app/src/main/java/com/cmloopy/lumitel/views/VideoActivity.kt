package com.cmloopy.lumitel.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        adapter = VideoAdapter(this,binding.recycleViewVideo, emptyList())
        binding.recycleViewVideo.layoutManager = LinearLayoutManager(this)

        binding.recycleViewVideo.adapter = adapter

        useViewModel()

        binding.recycleViewVideo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {  // Chỉ chạy khi cuộn dừng lại
                    val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
                    layoutManager?.let {
                        val centerPosition = it.findFirstCompletelyVisibleItemPosition()
                        if (centerPosition != RecyclerView.NO_POSITION) {
                            adapter.playVideoAtPosition(centerPosition)
                        }
                    }
                }
            }
        })

        binding.btnCloseActivityVideo.setOnClickListener {
            onBackPressed()
            finish()
        }
        setContentView(binding.root)
    }

    private fun useViewModel() {
        viewModel.videol.observe(this){videos ->
            adapter.updateData(videos)
        }
    }
    override fun onPause() {
        super.onPause()
        adapter.pauseVideo()
    }

    override fun onResume() {
        super.onResume()
        adapter.resumeVideo()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.releaseVideo()
    }
}