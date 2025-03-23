package com.cmloopy.lumitel.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.cmloopy.lumitel.adapter.ShortAdapter
import com.cmloopy.lumitel.databinding.ActivityShortVideoBinding
import com.cmloopy.lumitel.viewmodels.HotVideoViewModel

class ShortVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShortVideoBinding
    private val viewModel: HotVideoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShortVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var adapter = ShortAdapter(this, emptyList())
        binding.vpgShortVideo.adapter = adapter
        viewModel.videos.observe(this, Observer { videos ->
            Toast.makeText(this,"${videos.size}", Toast.LENGTH_SHORT).show()
            adapter.updateData(videos)
        })
    }
}