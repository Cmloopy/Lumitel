package com.cmloopy.lumitel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cmloopy.lumitel.databinding.ActivityShortVideoBinding

class ShortVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShortVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShortVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}