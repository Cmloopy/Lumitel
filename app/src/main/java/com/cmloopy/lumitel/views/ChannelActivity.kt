package com.cmloopy.lumitel.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.ChannelAdapter
import com.cmloopy.lumitel.databinding.ActivityChannelBinding
import com.google.android.material.tabs.TabLayoutMediator

class ChannelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChannelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val channelID = intent.getIntExtra("idChannel", -1)

        val adapter = ChannelAdapter(this)
        binding.viewpagerChannel.adapter = adapter

        val listName = listOf("Video", "Short")
        TabLayoutMediator(binding.tabLayoutChannel, binding.viewpagerChannel) {tab, position ->
            tab.text = listName[position]
        }
    }
}