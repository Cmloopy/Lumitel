package com.cmloopy.lumitel.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.fragment.InfoChannelFragment
import com.cmloopy.lumitel.fragment.ShortVideoChannelFragment
import com.cmloopy.lumitel.fragment.VideoChannelFragment

class ChannelAdapter(fragmentActivity: FragmentActivity, channelId: Int, msisdn: String): FragmentStateAdapter(fragmentActivity) {
    private val list = listOf(
        VideoChannelFragment.newInstance(channelId,msisdn),
        ShortVideoChannelFragment.newInstance(channelId,msisdn),
        InfoChannelFragment.newInstance(channelId, msisdn)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}