package com.cmloopy.lumitel.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.fragment.ShortVideoChannelFragment
import com.cmloopy.lumitel.fragment.VideoChannelFragment

class ChannelAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val list = listOf(
        VideoChannelFragment(),
        ShortVideoChannelFragment(),
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}