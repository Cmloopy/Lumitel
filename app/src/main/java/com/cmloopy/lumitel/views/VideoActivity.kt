package com.cmloopy.lumitel.views

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.adapter.VideoAdapter
import com.cmloopy.lumitel.data.models.Video
import com.cmloopy.lumitel.databinding.ActivityVideoBinding
import com.cmloopy.lumitel.viewmodels.VideoViewModel


class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var adapter: VideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        adapter = VideoAdapter(this,binding.recycleViewVideo, emptyList()) {video ->
            rotateVideo(video)
        }
        binding.recycleViewVideo.layoutManager = LinearLayoutManager(this)

        binding.recycleViewVideo.adapter = adapter


        val divider = ShapeDrawable().apply {
            paint.color = Color.WHITE
            intrinsicHeight = 2
        }
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
            setDrawable(divider)
        }
        binding.recycleViewVideo.addItemDecoration(dividerItemDecoration)

        useViewModel()

        binding.recycleViewVideo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
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
            finish()
        }
    }

    private fun rotateVideo(video: Video) {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
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