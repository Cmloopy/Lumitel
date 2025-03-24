package com.cmloopy.lumitel.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import com.cmloopy.lumitel.adapter.ShortAdapter
import com.cmloopy.lumitel.databinding.ActivityShortVideoBinding
import com.cmloopy.lumitel.viewmodels.ShortViewModel

class ShortVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShortVideoBinding
    private val viewModel: ShortViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShortVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val adapter = ShortAdapter(this, emptyList())
        binding.vpgShortVideo.adapter = adapter
        viewModel.videos.observe(this, Observer { videos ->
            adapter.updateData(videos)
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        //Gọi release hủy Player
        binding.vpgShortVideo.adapter?.let { adapter ->
            if (adapter is ShortAdapter) {
                adapter.releaseVideo()
            }
        }
    }
    override fun onPause() {
        super.onPause()
        //Dừng video khi trong nền
        binding.vpgShortVideo.adapter?.let { adapter ->
            if(adapter is ShortAdapter){
                adapter.pauseVideo()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        //Tiếp tục chạy khi quay lại video
        binding.vpgShortVideo.adapter?.let { adapter ->
            if(adapter is ShortAdapter){
                adapter.resumeVideo()
            }
        }
    }
}