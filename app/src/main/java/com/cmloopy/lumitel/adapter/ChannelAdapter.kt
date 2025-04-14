package com.cmloopy.lumitel.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.fragment.InfoChannelFragment
import com.cmloopy.lumitel.fragment.ShortVideoChannelFragment
import com.cmloopy.lumitel.fragment.VideoChannelFragment

class ChannelAdapter(fragmentActivity: FragmentActivity, private val channelId: Int): FragmentStateAdapter(fragmentActivity) {
    private val list = listOf(
        VideoChannelFragment(),
        ShortVideoChannelFragment(),
        InfoChannelFragment()
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return sendIdChannel(channelId,list[position])
    }
    private fun sendIdChannel(channelId: Int, fragment: Fragment): Fragment{
        val setupFragment = fragment
        setupFragment.arguments = Bundle().apply {
            putInt("channelId", channelId)
        }
        return fragment
    }
}