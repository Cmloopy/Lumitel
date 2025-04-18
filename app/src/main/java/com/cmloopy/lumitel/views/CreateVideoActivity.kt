package com.cmloopy.lumitel.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.databinding.ActivityCreateVideoBinding

class CreateVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityCreateVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackMyChannel.setOnClickListener {
            finish()
        }
    }
}