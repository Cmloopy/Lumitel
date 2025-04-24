package com.cmloopy.lumitel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cmloopy.lumitel.databinding.ActivityVideoViewBinding

class VideoViewActivityRemake : AppCompatActivity() {
    private lateinit var binding: ActivityVideoViewBinding
    private val viewModel: VideoViewModelRemake by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}