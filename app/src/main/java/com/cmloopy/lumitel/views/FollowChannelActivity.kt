package com.cmloopy.lumitel.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmloopy.lumitel.adapter.ListFollowAdapter
import com.cmloopy.lumitel.data.models.channel.Channel
import com.cmloopy.lumitel.databinding.ActivityFollowChannelBinding
import com.cmloopy.lumitel.viewmodels.FollowChannelViewModel

class FollowChannelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFollowChannelBinding
    private var msisdn: String? = null
    private val viewModel : FollowChannelViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        msisdn = intent.getStringExtra("msisdn")?:"0"
        viewModel.getListChannelFollow(msisdn.toString())

        binding.recycleViewFollowList.layoutManager = LinearLayoutManager(this)
        val adapter = ListFollowAdapter(emptyList()) {
            getInfoChannel(it)
        }
        binding.recycleViewFollowList.adapter = adapter
        viewModel.channels.observe(this){
            adapter.updateList(it)
        }
        binding.btnBackHome.setOnClickListener {
            finish()
        }
    }

    private fun getInfoChannel(it: Channel) {
        val intent = Intent(this, ChannelActivity::class.java)
        intent.putExtra("msisdn",msisdn)
        intent.putExtra("idChannel",it.id)
        startActivity(intent)
    }
}