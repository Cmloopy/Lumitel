package com.cmloopy.lumitel.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.bumptech.glide.Glide
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.ChannelAdapter
import com.cmloopy.lumitel.databinding.ActivityChannelBinding
import com.cmloopy.lumitel.viewmodels.ChannelViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ChannelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChannelBinding
    private val viewModel : ChannelViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var channelID = intent.getIntExtra("idChannel", -1)
        val msisdn = intent.getStringExtra("msisdn")?: "0"
        //Xử lý suscribe//

        viewModel.setChannelId(channelID, msisdn)
        binding.constraintLayout.visibility = View.GONE
        binding.constraintLayout2.visibility = View.GONE
        binding.imgBiaChannel.visibility = View.GONE
        binding.btnSubscribe.visibility = View.GONE
        if(channelID == -1) binding.btnSubscribe.visibility = View.GONE
        else binding.btnSubscribe.visibility = View.VISIBLE

        binding.swipeRefreshLayoutChannel.setOnRefreshListener {
            reloadData()
        }

        viewModel.channel.observe(this){ channel ->
            if(channel != null) {
                channelID = channel.id
                binding.progressBarLoadingChannel.visibility = View.GONE
                binding.constraintLayout.visibility = View.VISIBLE
                binding.constraintLayout2.visibility = View.VISIBLE
                binding.imgBiaChannel.visibility = View.VISIBLE

                binding.txtNameChannelActivity.text = channel.channelName
                binding.txtNameChannelActivity1.text = channel.channelName
                Glide.with(this).load(channel.channelAvatar).into(binding.imgAvatarChannel)
                if (channel.headerBanner != null && channel.headerBanner != "") {
                    Glide.with(this).load(channel.headerBanner).into(binding.imgBiaChannel)
                }
                binding.txtTotalVideoChannel.text = "kakaokeapitest - ${channel.numVideos} Videos"
                val adapter = ChannelAdapter(this, channelId = channelID, msisdn)
                binding.viewpagerChannel.adapter = adapter
                binding.viewpagerChannel.isUserInputEnabled = false
                val listName = listOf("Video", "Short", "Infomation")
                TabLayoutMediator(
                    binding.tabLayoutChannel,
                    binding.viewpagerChannel
                ) { tab, position ->
                    val customView =
                        LayoutInflater.from(this).inflate(R.layout.item_tab_video, null)
                    val textView = customView.findViewById<TextView>(R.id.tabText)
                    textView.text = listName[position]
                    tab.customView = customView
                }.attach()
            }
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnCreateVideo.setOnClickListener {
            val intent = Intent(this, CreateVideoActivity::class.java)
            intent.putExtra("msisdn", msisdn)
            startActivity(intent)
        }
    }
    private fun reloadData() {
        val channelID = intent.getIntExtra("idChannel", -1)
        val msisdn = intent.getStringExtra("msisdn")?:"0"
        viewModel.setChannelId(channelId = channelID, msisdn)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.swipeRefreshLayoutChannel.isRefreshing = false
        }, 500)
    }
}